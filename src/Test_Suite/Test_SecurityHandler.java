package Test_Suite;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Controllers.AccountFactory;
import Controllers.AccountsController;
import Global.AccountType;
import Global.Result;
import Global.TransactionNumberGenerator;
import Global.UserMode;
import Model.Transactions.*;
import Services.Security.SecurityHandler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test_SecurityHandler {
	
	private static String basicAccNum1, busAccNum1, basicAccNum2, busAccNum2;
	private static int basicAccPass1, busAccPass1, basicAccPass2, busAccPass2;
	private static TransactionNumberGenerator trGen;
	
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
	public void test1_Security_Pointcut() {
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
			
			tr.executeTransaction();  // The transaction fails in ClientMode but we do not care. We only test the pointcut
			assertEquals(true, SecurityHandler.transactionEvaluated());
			
			SecurityHandler.resetEvaluation();
			assertEquals(false, SecurityHandler.transactionEvaluated());
		} // for
	} // test_IsAuntenticated
	
	@Test
	public void test2_Authorization_ClientMode_Basic_Account_Exceptions() {
		Transaction tr;
		UserMode mode = UserMode.CLIENT;
		Result result = null;
		
		// Authorisation for getting Balance - It should Complete
		tr = new BalanceTransaction(mode, basicAccPass1, basicAccNum1);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		
		// Authorisation for Deposit Transaction - It should Complete
		tr = new DepositTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		// Authorisation for Withdrawal Transaction - It should Complete
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 10);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		// Authorisation for Transfer between BASIC_ACCOUNT -> BASIC_ACCOUNT - It should fail 		
		tr = new TransferTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, basicAccNum2, 10 );
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals("Not authorized for transaction", result.getInfo());
		
		// Authorisation for Transfer between BASIC_ACCOUNT -> BUSINESS_ACCOUNT - It should fail 		
		tr = new TransferTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, busAccNum1, 10 );
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals("Not authorized for transaction", result.getInfo());				
	} // test_Security_Advice_Authorization_Basic_Account_Exceptions
		
	@Test
	public void test3_Authorization_ClientMode_Business_Account() {
		UserMode mode = UserMode.CLIENT;
		Transaction tr;
		Result result = null;
		// Authorisation for getting Balance - It should Complete
		tr = new BalanceTransaction(mode, busAccPass1, busAccNum1);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		// Authorisation for Deposit Transaction - It should Complete
		tr = new DepositTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		// Authorisation for Withdrawal Transaction - It should Complete
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, 10);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		// Authorisation for Transfer between BUSINESS_ACCOUNT -> BASIC_ACCOUNT - It should Complete
		tr = new TransferTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, basicAccNum1, 10 );
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());

		// Authorisation for Transfer between BUSINESS_ACCOUNT -> BUSINESS_ACCOUNT - It should Complete		
		tr = new TransferTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, busAccNum2, 10 );
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());							
	} // test_Security_Advice_Authorization_Basic_Account_Exceptions
	
	@Test
	public void test4_Authentication_ClientMode_Balance() {
		UserMode mode = UserMode.CLIENT;
		Result result = null;
		String expectedErrorMessage = "Not authenticated for transaction";
		Transaction tr;
		// Test Authentication for Balance
		// It Should Complete
		tr = new BalanceTransaction(mode, basicAccPass1, basicAccNum1);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		// It should Fail - Null Password
		tr = new BalanceTransaction(mode, null, basicAccNum1);
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals(expectedErrorMessage, result.getInfo());	
		// It should Fail - Wrong password
		tr = new BalanceTransaction(mode, -1111, basicAccNum1);
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals(expectedErrorMessage, result.getInfo());	
	} // test103_Authentication_ClientMode_Balance
	
	@Test
	public void test5_Authentication_ClientMode__Deposit_() {
		UserMode mode = UserMode.CLIENT;
		Result result = null;
		String expectedErrorMessage = "Not authenticated for transaction";
		Transaction tr;
		// Test Authentication for Balance
		// It Should Complete
		tr = new DepositTransaction(trGen.getAndIncr(),mode, basicAccPass1, basicAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		// It should Fail - Null Password
		tr = new DepositTransaction(trGen.getAndIncr(),mode, null, basicAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals(expectedErrorMessage, result.getInfo());	
		// It should Fail - Wrong Password
		tr = new DepositTransaction(trGen.getAndIncr(),mode, -1111, basicAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals(expectedErrorMessage, result.getInfo());	
	} // test_Authentication_Basic_Account_Client_Deposit_Exception
	
	@Test
	public void test6_Authentication_ClientMode_Withdrawal() {
		UserMode mode = UserMode.CLIENT;
		Result result = null;
		String expectedErrorMessage = "Not authenticated for transaction";
		Transaction tr;
		// Test Authentication for Balance
		// It Should Complete
		tr = new WithdrawTransaction(trGen.getAndIncr(),mode, basicAccPass1, basicAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		// It should Fail - Null Password
		tr = new WithdrawTransaction(trGen.getAndIncr(),mode, null, basicAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals(expectedErrorMessage, result.getInfo());	
		// It should Fail - Wrong Password
		tr = new WithdrawTransaction(trGen.getAndIncr(),mode, -1111, basicAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals(expectedErrorMessage, result.getInfo());	
	} // test_Authentication_Basic_Account_Client_Withdrawal_Exception
	
	@Test
	public void test7_Authentication_ClientMode_Transfer(){
		UserMode mode = UserMode.CLIENT;
		Result result = null;
		String expectedErrorMessage = "Not authenticated for transaction";
		Transaction tr;
		// Test Authentication for Balance
		// It Should Complete
		tr = new TransferTransaction(trGen.getAndIncr(),mode, busAccPass1, busAccNum1, basicAccNum2, 0);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		// It should Fail - Null Password
		tr = new TransferTransaction(trGen.getAndIncr(),mode, null, busAccNum1, basicAccNum2, 0);
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals(expectedErrorMessage, result.getInfo());	
		// It should Fail - Wrong Password
		tr = new TransferTransaction(trGen.getAndIncr(),mode, -1111, busAccNum1, basicAccNum2, 0);
		result = tr.executeTransaction();
		assertEquals("FAILED", result.getStatus());
		assertEquals(expectedErrorMessage, result.getInfo());	

	} // test_Authentication_Basic_Account_Client_Transfer_Exceptions
	
	
	@Test
	public void test8_AdminMode_No_Exceptions_On_Any_Transaction() {
		Transaction tr;
		int randomPass = 12321;
		Result result = null;
		try
		{
		UserMode mode = UserMode.ADMIN;
		// Authorisation for getting Balance
		tr = new BalanceTransaction(mode, randomPass, basicAccNum1);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		tr = new BalanceTransaction(mode, null, basicAccNum1);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());

		// Authorisation for Deposit Transaction
		tr = new DepositTransaction(trGen.getAndIncr(), mode, randomPass, basicAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		tr = new DepositTransaction(trGen.getAndIncr(), mode, null, basicAccNum1, 100);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		// Authorisation for Withdrawal Transaction
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, randomPass, basicAccNum1, 10);		
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, null, basicAccNum1, 10);
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());

		// Authorisation for Transfer between BASIC_ACCOUNT -> BASIC_ACCOUNT 		
		tr = new TransferTransaction(32, mode, randomPass, basicAccNum1, basicAccNum2, 10 );
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		tr = new TransferTransaction(32, mode, null, basicAccNum1, basicAccNum2, 10 );
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());

		// Authorisation for Transfer between BASIC_ACCOUNT -> BUSINESS_ACCOUNT 		
		tr = new TransferTransaction(33, mode, randomPass, basicAccNum1, busAccNum1, 10 );
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		
		tr = new TransferTransaction(33, mode, null, basicAccNum1, busAccNum1, 10 );
		result = tr.executeTransaction();
		assertEquals("COMPLETED", result.getStatus());
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
	} // test_Security_Advice_Authorization_Basic_Account_Exceptions
	
	@After
	public void resetEvaluation() {
		SecurityHandler.resetEvaluation();
		assertEquals(false, SecurityHandler.transactionEvaluated());
	} // resetEvaluation
} // Test_SecurityHandler
