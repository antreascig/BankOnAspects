package Test_Suite;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import Controllers.AccountFactory;
import Controllers.AccountsController;
import Global.AccountType;
import Global.TransactionNumberGenerator;
import Global.UserMode;
import Model.Exceptions.AccountAuthenticationException;
import Model.Exceptions.AccountAuthorisationException;
import Model.Transactions.*;
import Services.Security.SecurityHandler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test_SecurityHandler {
	
	private static String basicAccNum1, busAccNum1, basicAccNum2, busAccNum2;
	private static int basicAccPass1, busAccPass1, basicAccPass2, busAccPass2;
	private static TransactionNumberGenerator trGen;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void setUp() {
		
		TestSetUp.setUpTestingEnvironment("SecurityHandler");
		
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		AccountsController accountController = AccountsController.getInstance();
		
		trGen = TransactionNumberGenerator.getTrNumInstance();
		
		basicAccPass1 = 1234;	
		basicAccPass2 = 9123;
		busAccPass1 = 5678;
		busAccPass2 = 4567;
		
		basicAccNum1 = accountController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, basicAccPass1));
//		System.out.println("Basic Account1 Num: " + basicAccNum1);
		basicAccNum2 = accountController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, basicAccPass2));
//		System.out.println("Basic Account1 Num: " + basicAccNum2);
		busAccNum1 = accountController.addAccount(accFactory.createAccount(AccountType.BUSINESS_ACCOUNT, busAccPass1));
//		System.out.println("Basic Account1 Num: " + busAccPass1);		
		busAccNum2 = accountController.addAccount(accFactory.createAccount(AccountType.BUSINESS_ACCOUNT, busAccPass2));
//		System.out.println("Basic Account1 Num: " + busAccPass2);
	} // setAccounts
	
	@AfterClass
	public static void tearDown() {
		TestSetUp.tearDownTestingEnvironment();
	} // tearDown
	
	@Test
	public void test100_Security_Pointcut() {
		Transaction tr;
		for ( UserMode mode : UserMode.values()) {
			// Test BalanceTransaction Pointcut
			tr = new BalanceTransaction(mode, basicAccPass1,  basicAccNum1);
			assertEquals(false, SecurityHandler.transactionEvaluated());
			
			tr.executeTransaction();
			assertEquals(true, SecurityHandler.transactionEvaluated());
			
			SecurityHandler.resetEvaluation();
			assertEquals(false, SecurityHandler.transactionEvaluated());
			
			// Test DepositTransaction Pointcut
			tr = new DepositTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 100 );	
			assertEquals(false, SecurityHandler.transactionEvaluated());	
			
			tr.executeTransaction();
			assertEquals(true, SecurityHandler.transactionEvaluated());	
			
			SecurityHandler.resetEvaluation();
			assertEquals(false, SecurityHandler.transactionEvaluated());
			
			// Test WithdrawTransaction Pointcut
			tr = new WithdrawTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 10 );	
			assertEquals(false, SecurityHandler.transactionEvaluated());
			
			tr.executeTransaction();
			assertEquals(true, SecurityHandler.transactionEvaluated());
			
			SecurityHandler.resetEvaluation();
			assertEquals(false, SecurityHandler.transactionEvaluated());
				
			// Test TransferTransaction Pointcut
			tr = new TransferTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, busAccNum1, 10 );
			assertEquals(false, SecurityHandler.transactionEvaluated());
			try {
				tr.executeTransaction();  // The transaction fails in ClientMode but we do not care. We only test the pointcut
			} catch(Exception exc){		
			}
			finally{
				assertEquals(true, SecurityHandler.transactionEvaluated());
				SecurityHandler.resetEvaluation();
				assertEquals(false, SecurityHandler.transactionEvaluated());
			} // finally
		} // for
	} // test_IsAuntenticated
	
	@Test
	public void test101_Authorization_ClientMode_Basic_Account_Exceptions() {
		Transaction tr;
		UserMode mode = UserMode.CLIENT;
		
		// Authorisation for getting Balance - No exception expected
		tr = new BalanceTransaction(mode, basicAccPass1, basicAccNum1);
		tr.executeTransaction();
		
		// Authorisation for Deposit Transaction - No exception expected
		tr = new DepositTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 100);
		tr.executeTransaction();
		
		// Authorisation for Withdrawal Transaction - No exception expected
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 10);
		tr.executeTransaction();
		
		try {
			// Authorisation for Transfer between BASIC_ACCOUNT -> BASIC_ACCOUNT - It should fail and throw exception		
			tr = new TransferTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, basicAccNum2, 10 );
			tr.executeTransaction();
		}  catch (AccountAuthorisationException exc) {
			assertEquals("Not authenticated for transaction", exc.getMessage());
		} finally {
			// Authorisation for Transfer between BASIC_ACCOUNT -> BUSINESS_ACCOUNT - It should fail and throw exception		
			tr = new TransferTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, busAccNum1, 10 );
			exception.expect(AccountAuthorisationException.class);
			tr.executeTransaction();					
		} // finally				
	} // test_Security_Advice_Authorization_Basic_Account_Exceptions
		
	@Test
	public void test102_Authorization_ClientMode_Business_Account_Exceptions() {
		UserMode mode = UserMode.CLIENT;
		Transaction tr;		
		// Authorisation for getting Balance - No exception expected
		tr = new BalanceTransaction(mode, busAccPass1, busAccNum1);
		tr.executeTransaction();
		
		// Authorisation for Deposit Transaction - No exception expected
		tr = new DepositTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, 100);
		tr.executeTransaction();
		
		// Authorisation for Withdrawal Transaction - No exception expected
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, 10);
		tr.executeTransaction();
		
		// Authorisation for Transfer between BUSINESS_ACCOUNT -> BASIC_ACCOUNT - No exception expected
		tr = new TransferTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, basicAccNum1, 10 );
		tr.executeTransaction();

		// Authorisation for Transfer between BUSINESS_ACCOUNT -> BUSINESS_ACCOUNT - No exception expected		
		tr = new TransferTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, busAccNum2, 10 );
		tr.executeTransaction();								
	} // test_Security_Advice_Authorization_Basic_Account_Exceptions
	
	@Test
	public void test103_Authentication_ClientMode_Basic_Account_Balance_Exception() {
		UserMode mode = UserMode.CLIENT;
		String expectedErrorMessage = "Not authenticated for transaction";
		Transaction tr = new BalanceTransaction(mode, busAccPass1, basicAccNum1);
		
		exception.expect(AccountAuthenticationException.class);
		exception.expectMessage(expectedErrorMessage);
		tr.executeTransaction();	
	} // test_Authentication_Basic_Account_Client_Balance_Exception
	
	@Test
	public void test104_Authentication_ClientMode_Basic_Account_Deposit_Exception() {
		UserMode mode = UserMode.CLIENT;
		String expectedErrorMessage = "Not authenticated for transaction";		
		Transaction tr = new DepositTransaction(trGen.getAndIncr(), mode, busAccPass1, basicAccNum1, 100);	
		exception.expect(AccountAuthenticationException.class);
		exception.expectMessage(expectedErrorMessage);
		tr.executeTransaction();
	} // test_Authentication_Basic_Account_Client_Deposit_Exception
	
	@Test
	public void test105_Authentication_ClientMode_Basic_Account_Withdrawal_Exception() {
		UserMode mode = UserMode.CLIENT;
		String expectedErrorMessage = "Not authenticated for transaction";
		
		Transaction tr = new WithdrawTransaction(trGen.getAndIncr(), mode, busAccPass1, basicAccNum1, 10);
		exception.expect(AccountAuthenticationException.class);
		exception.expectMessage(expectedErrorMessage);
		tr.executeTransaction();
	} // test_Authentication_Basic_Account_Client_Withdrawal_Exception
	
	@Test
	public void test106_Authentication_ClientMode_Basic_Account_Transfer_Exceptions (){
		UserMode mode = UserMode.CLIENT;
		String expectedErrorMessage = "Not authenticated for transaction";
		
		Transaction tr = new TransferTransaction(trGen.getAndIncr(), mode, busAccPass1, basicAccNum1, busAccNum1, 10);
		exception.expect(AccountAuthenticationException.class);
		exception.expectMessage(expectedErrorMessage);
		tr.executeTransaction();

	} // test_Authentication_Basic_Account_Client_Transfer_Exceptions
	
	@Test
	public void test107_Authentication_ClientMode_Business_Account_Balance_Exception() {
		UserMode mode = UserMode.CLIENT;
		String expectedErrorMessage = "Not authenticated for transaction";
		Transaction tr = new BalanceTransaction(mode, basicAccPass1, busAccNum1);
		
		exception.expect(AccountAuthenticationException.class);
		exception.expectMessage(expectedErrorMessage);
		tr.executeTransaction();	
	} // test_Authentication_Basic_Account_Client_Balance_Exception
	
	public void test108_Authentication_ClientMode_Business_Account_Deposit_Exception() {
		UserMode mode = UserMode.CLIENT;
		String expectedErrorMessage = "Not authenticated for transaction";
		
		Transaction tr = new DepositTransaction(-1, mode, basicAccPass1, busAccNum1, 100);	
		exception.expect(AccountAuthenticationException.class);
		exception.expectMessage(expectedErrorMessage);
		tr.executeTransaction();
	} // test_Authentication_Basic_Account_Client_Deposit_Exception
	
	@Test
	public void test109_Authentication_ClientMode_Business_Account_Withdrawal_Exception() {
		UserMode mode = UserMode.CLIENT;
		String expectedErrorMessage = "Not authenticated for transaction";
		
		Transaction tr = new WithdrawTransaction(trGen.getAndIncr(), mode, basicAccPass1, busAccNum1, 10);
		exception.expect(AccountAuthenticationException.class);
		exception.expectMessage(expectedErrorMessage);
		tr.executeTransaction();
	} // test_Authentication_Basic_Account_Client_Withdrawal_Exception
	
	@Test
	public void test110_Authentication_ClientMode_Business_Account_ClientMode_Transfer_Exceptions (){
		UserMode mode = UserMode.CLIENT;
		String expectedErrorMessage = "Not authenticated for transaction";
		
		Transaction tr = new TransferTransaction(trGen.getAndIncr(), mode, basicAccPass1, busAccNum1, basicAccNum1, 10);
		exception.expect(AccountAuthenticationException.class);
		exception.expectMessage(expectedErrorMessage);
		tr.executeTransaction();
	}
	
	@Test
	public void test111_AdminMode_No_Exceptions_On_Any_Transaction() {
		Transaction tr;
		int randomPass = 12321;
		
		UserMode mode = UserMode.ADMIN;
		// Authorisation for getting Balance - No exception expected
		tr = new BalanceTransaction(mode, randomPass, basicAccNum1);
		tr.executeTransaction();
		
		tr = new BalanceTransaction(mode, null, basicAccNum1);
		tr.executeTransaction();

		// Authorisation for Deposit Transaction - No exception expected
		tr = new DepositTransaction(trGen.getAndIncr(), mode, randomPass, basicAccNum1, 100);
		tr.executeTransaction();
		tr = new DepositTransaction(trGen.getAndIncr(), mode, null, basicAccNum1, 100);
		tr.executeTransaction();
		
		// Authorisation for Withdrawal Transaction - No exception expected
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, randomPass, basicAccNum1, 10);
		tr.executeTransaction();
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, null, basicAccNum1, 10);
		tr.executeTransaction();	

		// Authorisation for Transfer between BASIC_ACCOUNT -> BASIC_ACCOUNT - It should fail and throw exception		
		tr = new TransferTransaction(32, mode, randomPass, basicAccNum1, basicAccNum2, 10 );
		tr.executeTransaction();
		tr = new TransferTransaction(32, mode, null, basicAccNum1, basicAccNum2, 10 );
		tr.executeTransaction();

		// Authorisation for Transfer between BASIC_ACCOUNT -> BUSINESS_ACCOUNT - It should fail and throw exception		
		tr = new TransferTransaction(33, mode, randomPass, basicAccNum1, busAccNum1, 10 );
		tr.executeTransaction();	
		tr = new TransferTransaction(33, mode, null, basicAccNum1, busAccNum1, 10 );
		tr.executeTransaction();	
	} // test_Security_Advice_Authorization_Basic_Account_Exceptions
	
	@After
	public void resetEvaluation() {
		SecurityHandler.resetEvaluation();
		assertEquals(false, SecurityHandler.transactionEvaluated());
	} // resetEvaluation
} // Test_SecurityHandler
