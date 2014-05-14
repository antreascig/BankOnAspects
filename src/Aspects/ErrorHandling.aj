package Aspects;

import BankOnAspect.AccountOperationException;

public aspect ErrorHandling {
	pointcut handleException(AccountOperationException exception) : 
		handler(AccountOperationException) && args(exception);
	
	before(AccountOperationException exception): handleException(exception)
	{
		String errorMessage = exception.getMessage();
		// Do something with message
	}
}
