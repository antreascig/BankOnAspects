package Aspects;

import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;
import Services.Logging.Logger;


public aspect ErrorHandling extends Transactions {
	
	after(BankAccount account, int amount) throwing (AccountOperationException exception): bank_operations(account, amount)
	{
		String message = "\tFailure Reason:" + exception.getMessage();
		Logger.logAccountTransaction(account.getAccNum(), message);	  
	} // after bank_operations
} // ErrorHandling
