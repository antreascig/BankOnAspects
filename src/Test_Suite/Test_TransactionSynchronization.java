package Test_Suite;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.AccountFactory;
import Controllers.AccountsController;
import Global.AccountType;
import Global.Result;
import Server.UserThread;

public class Test_TransactionSynchronization {
	
	private static String basicAccNum1, busAccNum1, basicAccNum2, busAccNum2;
	private static int basicAccPass1, busAccPass1, basicAccPass2, busAccPass2;
	
	@BeforeClass
	public static void setUp() {
		
		TestSetUp.setUpTestingEnvironment("TransactionSynchronization");
		
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		AccountsController accountController = AccountsController.getInstance();
				
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
	public void test() {

		UserThread newClient1 = new UserThread(null, 1 , basicAccNum1);
		
		UserThread newClient2 = new UserThread(null, 2, basicAccNum1 );
		newClient1.start();
		newClient2.start();

		try
		{
		Result<?> resultForUser1 = newClient1.getResult();
		Result<?> resultForUser2 = newClient2.getResult();
		
		System.out.println("Result 1: Status: " + resultForUser1.getStatus() + "\t Info: " + resultForUser1.getInfo());
		System.out.println("Result 2: Status: " + resultForUser2.getStatus() + "\t Info: " + resultForUser2.getInfo());
		
		assertNotEquals(resultForUser2.getStatus(), resultForUser1.getStatus());

		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		
		
	}

}
