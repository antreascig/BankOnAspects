package Test_Suite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import BankOnAspect.AccountOperationException;
import BankOnAspect.BankAccount;
import BankOnAspect.BasicAccount;

public class Test_Account_Operations {
	
	@Test
	public void test_Account_creation_Only_AccNum() 
	{
		String accountNum = "1111";
		BankAccount account = new BasicAccount(accountNum);
		
		assertNotEquals(account, null);
		
		assertEquals(accountNum, account.getAccNum());
	} // test_Account_creation
	
	@Test
	public void test_Account_creation_both_Param() 
	{
		// Check with "correct" parameters
		String accountNum = "1111";
		BankAccount account = new BasicAccount(accountNum, 10);
		
		assertNotEquals(account, null);	
		assertEquals(accountNum, account.getAccNum());
		
		
		// Check with initial balance = 0
		account = new BasicAccount(accountNum, 10);
		assertNotEquals(account, null);	
		assertEquals(accountNum, account.getAccNum());
		
		// Check that exception is thrown for initial balance <0
		exception.expect(AccountOperationException.class);
		account = new BasicAccount("1111", -10);
		
	} // test_Account_creation
	
	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test_account_get_balance()
	{
		BankAccount account = new BasicAccount("1111", 10);
		
		assertEquals(10, account.getBalance());
		
		account = new BasicAccount("1111", 0);
		
		assertEquals(0, account.getBalance());
		
	} // test_account_get_balance
	
	@Test
	public void test_account_deposit()
	{
		int amount = 100;
		
		BankAccount account = new BasicAccount("1111");
		
		account.deposit(amount);
		
		
		
		
	}
	
}
