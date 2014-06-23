package Controllers;

import Global.Pair;
import Global.UserMode;
import Model.AccountOperationException;
import Model.Transactions.*;

public class TransactionController {

	private UserMode userMode;
	private Integer password;
	private Transaction tr;

	public TransactionController(UserMode mode) {
		userMode = mode;
		password = null;
	} // TransactionController
	
	public TransactionController(UserMode uRole, int pin) {
		userMode = uRole;
		password = pin;
	} // TransactionController
	
	public Pair<?> deposit(String accNumber, int amount) {
		Pair<?> result;
		
		tr = new DepositTransaction(userMode, password, accNumber, amount);
		try {
			tr.executeTransaction();
		
			result = tr.getResult();
		
			if ( result == null )
				throw new AccountOperationException("Something went wrong. No result received.");
			
			return result;
		} catch ( AccountOperationException exception ) // Catches Authentication/Authorisation/Or result==null exception
		{
			return new Pair<>("FAILED", exception.getMessage()); 
		} //catch
	} // deposit

	public Pair<?> withdraw(String accNumber, int amount) {		
		Pair<?> result = null;
		
		tr = new BalanceTransaction(userMode, password, accNumber);
		try {
			tr.executeTransaction();
		
			result = tr.getResult();
		
			if ( result == null )
				throw new AccountOperationException("Something went wrong. No result received.");
			
			return result;
		} catch ( AccountOperationException exception ) // Catches Authentication/Authorisation/Or result==null exception
		{
			return new Pair<>("FAILED", exception.getMessage());
		} //catch		
	} // withdraw
	
	
	public Pair<?> getBalance(String accNumber) {
		Pair<?> result;
		
		tr = new BalanceTransaction(userMode, password, accNumber);
		try {
			tr.executeTransaction();
		
			result = tr.getResult();
		
			if ( result == null )
				throw new AccountOperationException("Something went wrong. No result received.");
			
			return result;
		} catch ( AccountOperationException exception ) // Catches Authentication/Authorisation/Or result==null exception
		{
			return new Pair<>("FAILED", exception.getMessage());
		} //catch
	} // getAccountBalance
}