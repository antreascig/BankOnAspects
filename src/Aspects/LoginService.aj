package Aspects;

import Model.Transactions.Transaction;

public aspect LoginService extends Transactions 
{		
	before(Transaction transaction) : basic_transactions(transaction)
	{		

	} // before bank_operations
}
