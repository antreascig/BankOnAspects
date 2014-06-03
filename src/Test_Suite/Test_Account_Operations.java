package Test_Suite;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import BankOnAspect.AccountOperationException;
import BankOnAspect.BankAccount;
import BankOnAspect.BasicAccount;

public class Test_Account_Operations {

	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testWithdrawException() 
	{
		BankAccount account = new BasicAccount(1111);
		
		assertEquals(account.getBalance(), 0);
		
		exception.expect(AccountOperationException.class);
		account.withdraw(10);				
	} // testWithdrawException
	
	@Test
	public void testDepositException()
	{
		BankAccount account = new BasicAccount(1111);
				
		exception.expect(AccountOperationException.class);
		account.deposit(-10);		
	} // testDepositException

	
}
