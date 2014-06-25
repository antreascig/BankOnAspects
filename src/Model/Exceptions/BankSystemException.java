package Model.Exceptions;

@SuppressWarnings("serial")
public abstract class BankSystemException extends RuntimeException {
	public BankSystemException(String message) {
		super(message);
	} // BankSystemException
} // BankSystemException