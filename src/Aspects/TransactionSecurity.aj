package Aspects;

import org.aspectj.lang.annotation.AdviceName;

import Model.Transactions.Transaction;
import Services.Security.SecurityHandler;
import Global.Result;
import Global.UserMode;

public aspect TransactionSecurity extends Transactions 
{		
	@AdviceName("TransactionSecurity")
	Result around(Transaction transaction) : transactions(transaction)
	{		
		SecurityHandler.setEvaluated();
		
		if ( transaction.getUserMode() == UserMode.ADMIN )
			return proceed(transaction);
		
		if (!SecurityHandler.authenticated(transaction)) 
			return new Result("FAILED", "Not authenticated for transaction");
		if (!SecurityHandler.authorised(transaction))
			return new Result("FAILED", "Not authorized for transaction");
		
		return proceed(transaction);
	} // before basic_transactions
} // TransactionSecurity
