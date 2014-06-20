package Aspects;

import Controllers.TransactionControllers.TransactionController;

public aspect LoginService extends Transactions 
{		
	before(TransactionController controller, String accNumber,  int amount) : basic_transactions(controller, accNumber, amount)
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
