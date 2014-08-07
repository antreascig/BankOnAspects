package Test_Suite;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import fr.irisa.triskell.advicetracer.AdviceTracer;
import fr.irisa.triskell.advicetracer.AdviceTracer.TraceElement;
import fr.irisa.triskell.advicetracer.junit.Assert;
import Controllers.AccountFactory;
import Controllers.AccountsController;
import Global.AccountType;
import Global.Result;
import Global.TransactionNumberGenerator;
import Global.UserMode;
import Model.Transactions.*;
import Services.Security.SecurityHandler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test_TransactionSecurity {
	
	private static String basicAccNum1, busAccNum1, basicAccNum2, busAccNum2;
	private static int basicAccPass1, busAccPass1, basicAccPass2, busAccPass2;
	private static TransactionNumberGenerator trGen;
	
	@BeforeClass
	public static void setUp() {
		
		TestSetUp.setUpTestingEnvironment("Test_TransactionSecurity");
		
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		AccountsController accountController = AccountsController.getInstance();
		
		trGen = TransactionNumberGenerator.getTrNumInstance();
		
		basicAccPass1 = 1234;	
		basicAccPass2 = 9123;
		busAccPass1 = 5678;
		busAccPass2 = 4567;
		
		basicAccNum1 = accountController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, basicAccPass1));
		basicAccNum2 = accountController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, basicAccPass2));
		busAccNum1 = accountController.addAccount(accFactory.createAccount(AccountType.BUSINESS_ACCOUNT, busAccPass1));
		busAccNum2 = accountController.addAccount(accFactory.createAccount(AccountType.BUSINESS_ACCOUNT, busAccPass2));
	} // setAccounts
	
	@AfterClass
	public static void tearDown() {
		TestSetUp.tearDownTestingEnvironment();
	} // tearDown
	
	@Test
	public void test_Security_Pointcut_With_Trace() {
		for ( UserMode mode : UserMode.values()) {
			test_Transaction_Pointcut(new BalanceTransaction(mode, basicAccPass1,  basicAccNum1));
			test_Transaction_Pointcut(new DepositTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 100 ));
			test_Transaction_Pointcut(new WithdrawTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 10 ));
			test_Transaction_Pointcut(new TransferTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, busAccNum1, 10 ));
		} // for

	} // test_Security_Pointcut_With_Trace
	
	private void test_Transaction_Pointcut(Transaction tr) {
		AdviceTracer.addTracedAdvice("TransactionSecurity");
		AdviceTracer.setAdviceTracerOn();
		
		tr.executeTransaction();				
		
		AdviceTracer.setAdviceTracerOff();	
	    Assert.assertAdviceExecutionsEquals(1);
	    
	    List<TraceElement> advList = AdviceTracer.getExecutedAdvices();	
		assertEquals("TransactionSecurity", advList.get(0).getAdvice());
//		System.out.println(advList.get(0).getAdvice());
	} // test_Transaction_Pointcut
		
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
	
	////// Testing Delegated Code
	
	@Test
	public void test_Authorization_ClientMode() {
		Transaction tr;
		UserMode mode = UserMode.CLIENT;
		boolean authorized;
		
		//// BASIC ACCOUNT
		// Authorisation for Balance - TRUE
		tr = new BalanceTransaction(mode, basicAccPass1, basicAccNum1);
		authorized = SecurityHandler.authorised(tr);
		assertEquals(true, authorized);
		
		// Authorisation for Deposit Transaction - TRUE
		tr = new DepositTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 100);
		authorized = SecurityHandler.authorised(tr);
		assertEquals(true, authorized);
				
		// Authorisation for Withdrawal Transaction - TRUE
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, 10);
		authorized = SecurityHandler.authorised(tr);
		assertEquals(true, authorized);
		
		// Authorisation for Transfer between BASIC_ACCOUNT -> BASIC_ACCOUNT - FALSE		
		tr = new TransferTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, basicAccNum2, 10 );
		authorized = SecurityHandler.authorised(tr);
		assertEquals(false, authorized);
		
		// Authorisation for Transfer between BASIC_ACCOUNT -> BUSINESS_ACCOUNT - FALSE		
		tr = new TransferTransaction(trGen.getAndIncr(), mode, basicAccPass1, basicAccNum1, busAccNum1, 10 );
		authorized = SecurityHandler.authorised(tr);
		assertEquals(false, authorized);
		
		//// BUSINESS ACCOUNT
		
		// Authorisation for getting Balance - It should Complete
		tr = new BalanceTransaction(mode, busAccPass1, busAccNum1);
		authorized = SecurityHandler.authorised(tr);
		assertEquals(true, authorized);
		
		// Authorisation for Deposit Transaction - It should Complete
		tr = new DepositTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, 100);
		authorized = SecurityHandler.authorised(tr);
		assertEquals(true, authorized);
		
		// Authorisation for Withdrawal Transaction - It should Complete
		tr = new WithdrawTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, 10);
		authorized = SecurityHandler.authorised(tr);
		assertEquals(true, authorized);
		
		// Authorisation for Transfer between BUSINESS_ACCOUNT -> BASIC_ACCOUNT - It should Complete
		tr = new TransferTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, basicAccNum1, 10 );
		authorized = SecurityHandler.authorised(tr);
		assertEquals(true, authorized);

		// Authorisation for Transfer between BUSINESS_ACCOUNT -> BUSINESS_ACCOUNT - It should Complete		
		tr = new TransferTransaction(trGen.getAndIncr(), mode, busAccPass1, busAccNum1, busAccNum2, 10 );
		authorized = SecurityHandler.authorised(tr);
		assertEquals(true, authorized);	
	} // test_Authorization
	
	@Test
	public void test_Authentication_ClientMode_Balance() {
		UserMode mode = UserMode.CLIENT;
		boolean authenticated = false;
		Transaction tr;
		
		// Test Authentication for Balance
		// TRUE
		tr = new BalanceTransaction(mode, basicAccPass1, basicAccNum1);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(true, authenticated);
		// FALSE - Null Password
		tr = new BalanceTransaction(mode, null, basicAccNum1);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(false, authenticated);
		// FALSE - Wrong password
		tr = new BalanceTransaction(mode, -1111, basicAccNum1);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(false, authenticated);	
	} // test103_Authentication_ClientMode_Balance
	
	@Test
	public void test_Authentication_ClientMode__Deposit() {
		UserMode mode = UserMode.CLIENT;
		boolean authenticated;
		Transaction tr;
		
		// Test Authentication for Balance
		// True
		tr = new DepositTransaction(trGen.getAndIncr(),mode, basicAccPass1, basicAccNum1, 100);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(true, authenticated);	
		// False - Null Password
		tr = new DepositTransaction(trGen.getAndIncr(),mode, null, basicAccNum1, 100);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(false, authenticated);	
		// False - Wrong Password
		tr = new DepositTransaction(trGen.getAndIncr(),mode, -1111, basicAccNum1, 100);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(false, authenticated);		
	} // test_Authentication_Basic_Account_Client_Deposit_Exception
	
	@Test
	public void test_Authentication_ClientMode_Withdrawal() {
		UserMode mode = UserMode.CLIENT;
		boolean authenticated;
		Transaction tr;
		
		// Test Authentication for Balance
		// True
		tr = new WithdrawTransaction(trGen.getAndIncr(),mode, basicAccPass1, basicAccNum1, 100);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(true, authenticated);
		// False - Null Password
		tr = new WithdrawTransaction(trGen.getAndIncr(),mode, null, basicAccNum1, 100);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(false, authenticated);
		// False - Wrong Password
		tr = new WithdrawTransaction(trGen.getAndIncr(),mode, -1111, basicAccNum1, 100);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(false, authenticated);
	} // test_Authentication_Basic_Account_Client_Withdrawal_Exception
	
	@Test
	public void test_Authentication_ClientMode_Transfer(){
		UserMode mode = UserMode.CLIENT;
		boolean authenticated;
		Transaction tr;
		
		// Test Authentication for Balance
		// It Should Complete
		tr = new TransferTransaction(trGen.getAndIncr(),mode, busAccPass1, busAccNum1, basicAccNum2, 0);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(true, authenticated);
		// It should Fail - Null Password
		tr = new TransferTransaction(trGen.getAndIncr(),mode, null, busAccNum1, basicAccNum2, 0);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(false, authenticated);
		// It should Fail - Wrong Password
		tr = new TransferTransaction(trGen.getAndIncr(),mode, -1111, busAccNum1, basicAccNum2, 0);
		authenticated = SecurityHandler.authenticated(tr);
		assertEquals(false, authenticated);	

	} // test_Authentication_Basic_Account_Client_Transfer_Exceptions
	
	@Test
	public void test_AdminMode() {
		Transaction tr;
		int randomPass = 12321;
		Result result = null;
		try {
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
	
	////////////////////////////
	@After
	public void resetEvaluation() {
		SecurityHandler.resetEvaluation();
		assertEquals(false, SecurityHandler.transactionEvaluated());
	} // resetEvaluation
} // Test_SecurityHandler
