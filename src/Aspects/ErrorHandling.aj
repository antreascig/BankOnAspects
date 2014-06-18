package Aspects;

import Model.AccountOperationException;

//import BankOnAspect.AccountOperationException;

//import BankOnAspect.AccountOperationException;

public aspect ErrorHandling extends Transactions {
	
	after(Model.BankAccount account, int amount) throwing (AccountOperationException exception): bank_operations(account, amount)
	{
		  System.out.println("Exception: \n\tReason:" + exception.getMessage() + "\n\tAccount: " + account.getAccNum() + "\n\tAmount: " + amount);		  
		  // Log Exception
	} // after bank_operations
}
