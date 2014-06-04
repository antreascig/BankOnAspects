package Aspects;

import BankOnAspect.AccountOperationException;

//import BankOnAspect.AccountOperationException;

//import BankOnAspect.AccountOperationException;

public aspect ErrorHandling extends OperationHandling {
	
	after(BankOnAspect.BankAccount account, int amount) throwing (AccountOperationException exception): deposit(account, amount)
	{
		  System.out.println("Exception: " + exception.getMessage() + "\n\tAccount: " + account.getAccNum() + "\n\tAmount: " + amount);		  
		  // Log Exception
	}
	
}
