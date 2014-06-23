package Aspects;

import Model.Transactions.Transaction;
import Services.Security.SecurityHandler;

public aspect TransactionSecurity extends Transactions 
{		
	before(Transaction transaction) : transactions(transaction)
	{		
		SecurityHandler.evaluateTransaction(transaction);
	} // before basic_transactions
} // TransactionSecurity
