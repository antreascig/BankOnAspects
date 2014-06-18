package Aspects;

import Model.BankAccount;

public aspect LoginService extends Transactions 
{		
	before(BankAccount account, int amount): bank_operations(account, amount) 
	{		
//		try
//		{
//			
//		}
//		catch(RuntimeException exc)
//		{
//			throw new AccountOperationException(exc.getMessage());
//		} // 
	} // before bank_operations
}
