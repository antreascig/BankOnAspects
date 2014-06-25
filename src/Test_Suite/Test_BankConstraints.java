package Test_Suite;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Controllers.AccountFactory;
import Global.AccountType;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;

public class Test_BankConstraints 
{

	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testWithdrawException() 
	{
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	

		BankAccount account = accFactory.createAccount(AccountType.BASIC_ACCOUNT, "acc1", 1234);
		
		assertEquals(account.getBalance(), 0);
		
		exception.expect(AccountOperationException.class);
		account.withdraw(10);				
	} // testWithdrawException
	
	@Test
	public void testDepositException()
	{
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	

		BankAccount account = accFactory.createAccount(AccountType.BASIC_ACCOUNT, "acc1", 1234);
				
		exception.expect(AccountOperationException.class);
		account.deposit(-10);		
	} // testDepositException

	
}
