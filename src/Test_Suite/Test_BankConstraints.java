package Test_Suite;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Controllers.AccountFactory;
import Global.AccountType;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;

public class Test_BankConstraints 
{
	@BeforeClass
	public static void setUp() {
		TestSetUp.setUpTestingEnvironment("BankConstraints");
	} // setUp
	
	@AfterClass
	public static void tearDown() {
		TestSetUp.tearDownTestingEnvironment();
	} // tearDown
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testWithdrawException() {
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		BankAccount account = accFactory.createAccount(AccountType.BASIC_ACCOUNT, 1234);	
		
		exception.expect(AccountOperationException.class);
		exception.expectMessage("The account contains insufficient funds.");
		account.withdraw(10);				
	} // testWithdrawException
	
	@Test
	public void testDepositException() {
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		BankAccount account = accFactory.createAccount(AccountType.BASIC_ACCOUNT, 1234);
				
		exception.expect(AccountOperationException.class);
		exception.expectMessage("Deposit amount must not be negative");
		account.deposit(-10);
	} // testDepositException

	
}
