package Aspects;

import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;
import Services.Logging.Logger;


public aspect ErrorHandling extends Transactions {
	
	after(BankAccount account, int amount) throwing (AccountOperationException exception): bank_operations(account, amount)
	{
		String message = "Exception: \n\tReason:" + exception.getMessage() + "\n\tAccount: " + account.getAccNum() + "\n\tAmount: " + amount;
		Logger.logTransaction(message);		  
	} // after bank_operations
}
