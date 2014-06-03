package Aspects;

import BankOnAspect.AccountOperationException;

public aspect ErrorHandling {
	
	before (AccountOperationException exception): handler(Exception+) && args(exception) 
	{
		String errorMessage = exception.getMessage();
		// Do something with message
		System.out.println(errorMessage);
    }
}
