package BankOnAspect;

public class AccountOperationException extends RuntimeException {
	
	public AccountOperationException(String message)
	{
		super(message);
	} // AccountOperationException
	
	public String getMessage()
	{
		return this.getMessage();
	} // getMessage
} // AccountOperationException
