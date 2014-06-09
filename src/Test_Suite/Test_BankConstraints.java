package Test_Suite;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Model.AccountOperationException;
import Model.BankAccount;
import Model.BasicAccount;

public class Test_BankConstraints 
{

	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testWithdrawException() 
	{
		BankAccount account = new BasicAccount("1111");
		
		assertEquals(account.getBalance(), 0);
		
		exception.expect(AccountOperationException.class);
		account.withdraw(10);				
	} // testWithdrawException
	
	@Test
	public void testDepositException()
	{
		BankAccount account = new BasicAccount("1111");
				
		exception.expect(AccountOperationException.class);
		account.deposit(-10);		
	} // testDepositException

	
}
