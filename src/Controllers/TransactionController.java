package Controllers;

import Global.Result;
import Global.TransactionNumberGenerator;
import Global.UserMode;
import Model.Exceptions.AccountOperationException;
import Model.Exceptions.BankSystemException;
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
	
	private int getID() {
		TransactionNumberGenerator counter = TransactionNumberGenerator.getTrNumInstance();
		
		return counter.getAndIncr();
	} // getID
	
	public Result<?> deposit(String accNumber, int amount) {
		Result<?> result;
		
		int trID = getID();
		
		tr = new DepositTransaction(trID, userMode, password, accNumber, amount);
		try {
			tr.executeTransaction();
		
			result = tr.getResult();
		
			if ( result == null )
				throw new AccountOperationException("Something went wrong. No result received.");
			
			return result;
		} catch ( BankSystemException exception ) { // Catches Authentication/Authorisation/Or result==null exception
			return new Result<>("FAILED", exception.getMessage()); 
		} //catch
	} // deposit

	public Result<?> withdraw(String accNumber, int amount) {		
		Result<?> result = null;
		
		int trID = getID();
		
		tr = new WithdrawTransaction(trID , userMode, password, accNumber, amount);
		try {
			tr.executeTransaction();
		
			result = tr.getResult();
		
			if ( result == null )
				throw new AccountOperationException("Something went wrong. No result received.");
			
			return result;
		} catch ( BankSystemException exception )  { // Catches Authentication/Authorisation/Or result==null exception
			return new Result<>("FAILED", exception.getMessage());
		} //catch		
	} // withdraw
	
	public Result<?> getBalance(String accNumber) {
		Result<?> result = null;
		
		tr = new BalanceTransaction(userMode, password, accNumber);
		try {
			
			tr.executeTransaction();
		
			result = tr.getResult();
		
			if ( result == null )
				throw new AccountOperationException("Something went wrong. No result received.");
			
			return result;
		} catch ( BankSystemException exception ) { // Catches Authentication/Authorisation/Or result==null exception
			return new Result<>("FAILED", exception.getMessage());
		} //catch
	} // getAccountBalance
}