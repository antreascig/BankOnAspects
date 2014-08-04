package Test_Suite;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.AccountFactory;
import Controllers.AccountsController;
import Global.AccountType;
import Global.Result;
import Global.TransactionType;
import Server.UserThread;

public class Test_TransactionSynchronization {
	
	private static String basicAccNum1, busAccNum1;
	private static int basicAccPass1, busAccPass1;
	
	@BeforeClass
	public static void setUp() {
		
		TestSetUp.setUpTestingEnvironment("TransactionSynchronization");
		
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		AccountsController accountController = AccountsController.getInstance();
				
		basicAccPass1 = 1234;	
		busAccPass1 = 5678;
		
		basicAccNum1 = accountController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, basicAccPass1));
		busAccNum1 = accountController.addAccount(accFactory.createAccount(AccountType.BUSINESS_ACCOUNT, busAccPass1));		
	} // setAccounts
	
	@AfterClass
	public static void tearDown() {
		TestSetUp.tearDownTestingEnvironment();
	} // tearDown
	
	@Test
	public void testSynchronisationCriterio1() {
		UserThread newClient1 = new UserThread(null, 1 , basicAccNum1);
		
		UserThread newClient2 = new UserThread(null, 2, basicAccNum1 );
		try {	
			
		newClient1.selectOperation(TransactionType.DEPOSIT, null);
		newClient2.selectOperation(TransactionType.WITHDRAW, null);
		
		newClient1.start();
		newClient2.start();
	
		Result resultForUser1 = newClient1.getResult();
		Result resultForUser2 = newClient2.getResult();
		
		System.out.println();
		System.out.println("Deposit on account: " + basicAccNum1 + " \t Status: " + resultForUser1.getStatus() + " \t Info: " + resultForUser1.getInfo());
		System.out.println("Deposit on account: " + basicAccNum1 + " \t Status: " + resultForUser2.getStatus() + " \t Info: " + resultForUser2.getInfo());
		
		assertNotEquals(resultForUser2.getStatus(), resultForUser1.getStatus());
		} catch (Exception exc) {
			exc.printStackTrace();
		} //catch
	} //test
	
	
	@Test
	public void testSynchronisationCriterio2() {
		System.out.println("----------------------------------------------------------------------------------------");
		
		UserThread newClient1 = new UserThread(null, 1 , basicAccNum1);
		
		UserThread newClient2 = new UserThread(null, 2, busAccNum1 );
		try {	
			
		newClient1.selectOperation(TransactionType.DEPOSIT, null);
		newClient2.selectOperation(TransactionType.TRANSFER, basicAccNum1);
		
		newClient1.start();
		newClient2.start();
	
		Result resultForUser1 = newClient1.getResult();
		Result resultForUser2 = newClient2.getResult();
		
		System.out.println();
		System.out.println("Deposit on account: " + basicAccNum1 + " \t Status: " + resultForUser1.getStatus() + " \t Info: " + resultForUser1.getInfo());
		System.out.println("Transfer from: " + busAccNum1 + " to " + basicAccNum1 + " \t Status: " + resultForUser2.getStatus() + " \t Info: " + resultForUser2.getInfo());
		
		assertNotEquals(resultForUser2.getStatus(), resultForUser1.getStatus());
		} catch (Exception exc) {
			exc.printStackTrace();
		} //catch
	} //test

}
