package Test_Suite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Controllers.AccountFactory;
import Global.AccountType;
import Model.BankAccounts.BankAccount;

public class Test_Account_Operations {
	
	@Test
	public void test_Account_creation_default_operations() {
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		BankAccount account = accFactory.createAccount(AccountType.BASIC_ACCOUNT, 1234);
		
		assertNotEquals(account, null);
	} // test_Account_creation
	
	@Test
	public void test_Account_creation_custom_operations() {
			
		
	} // test_Account_creation
	
	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test_account_get_balance() {
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		BankAccount account = accFactory.createAccount(AccountType.BASIC_ACCOUNT, 1234);
		
		assertEquals(0, account.getBalance());
		
	} // test_account_get_balance
	
	@Test
	public void test_account_deposit() {
		int amount = 100;
		
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		BankAccount account = accFactory.createAccount(AccountType.BASIC_ACCOUNT, 1234);
		
		account.deposit(amount);
		
		assertEquals(100, account.getBalance());
	} // test_account_deposit
	
} // Test_Account_Operations
