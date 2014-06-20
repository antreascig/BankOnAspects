package Aspects;

import Model.AccountOperationException;
import Services.Logging.Logger;

//import BankOnAspect.AccountOperationException;

//import BankOnAspect.AccountOperationException;

public aspect ErrorHandling extends Transactions {
	
	after(Model.BankAccount account, int amount) throwing (AccountOperationException exception): bank_operations(account, amount)
	{
		String message = "Exception: \n\tReason:" + exception.getMessage() + "\n\tAccount: " + account.getAccNum() + "\n\tAmount: " + amount;
		Logger.writeLog(message);		  
	} // after bank_operations
}
