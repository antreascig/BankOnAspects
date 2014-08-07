package Test_Suite;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.AccountFactory;
import Controllers.AccountsController;
import Global.AccountType;
import Global.Result;
import Global.TransactionType;
import Model.BankAccounts.BankAccount;
import Server.UserThread;
import Services.Synchronization.AccountLocker;

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
	public void testLocker() {
		System.out.println("----------------------------------------------------------------------------------------");
		ArrayList<String> accounts = new ArrayList<>();
		accounts.add(basicAccNum1);
		AccountLocker locker = new AccountLocker(accounts);
		AccountLocker locker2 = new AccountLocker(accounts);
		
		// Account not Locked
		locker.lockAccounts();
		locker.unlockAccounts();		

		new Thread(locker).start();
		new Thread(locker2).start();
		System.out.println("Locker 1: " + locker.getError());
		System.out.println("Locker 2: " + locker2.getError());
		assertNotEquals(locker.getError(), locker2.getError());
	} // testLocker

	@Test
	public void testUnlocker() {
		System.out.println("----------------------------------------------------------------------------------------");
		ArrayList<String> accounts = new ArrayList<>();
		accounts.add(basicAccNum1);
		accounts.add(busAccNum1);
		AccountLocker locker = new AccountLocker(accounts);
		
		locker.lockAccounts();
		locker.unlockAccounts();
		
		AccountsController controller = AccountsController.getInstance();
		
		BankAccount accountTest1 = controller.getAccount(basicAccNum1);
		BankAccount accountTest2 = controller.getAccount(busAccNum1);
		
		assertEquals(false, accountTest1.lock().isLocked());
		assertEquals(false, accountTest2.lock().isLocked());
	} // testUnlocker
	
	@Test
	public void testSynchronisationCriterion1() {
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
	public void testSynchronisationCriterion2() {
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

} // Test_TransactionSynchronization
