package Aspects;

import Login.LoginHandler;
import Model.AccountOperationException;
import Model.BankAccount;

public aspect LoginService extends OperationHandling 
{		
	before(BankAccount account, int amount): bank_operations(account, amount) 
	{		
		try
		{
			LoginHandler.login(account, amount);
		}
		catch(RuntimeException exc)
		{
			throw new AccountOperationException(exc.getMessage());
		} // 
	} // before bank_operations
}
