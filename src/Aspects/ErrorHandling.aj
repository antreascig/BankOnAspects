package Aspects;

import Model.AccountOperationException;

//import BankOnAspect.AccountOperationException;

//import BankOnAspect.AccountOperationException;

public aspect ErrorHandling extends OperationHandling {
	
	after(Model.BankAccount account, int amount) throwing (AccountOperationException exception): deposit(account, amount)
	{
		  System.out.println("Exception: " + exception.getMessage() + "\n\tAccount: " + account.getAccNum() + "\n\tAmount: " + amount);		  
		  // Log Exception
	}
	
}
