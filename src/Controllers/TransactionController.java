package Controllers;

import Global.Result;
import Global.TransactionNumberGenerator;
import Global.UserMode;
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
	
	public Result deposit(String accNumber, int amount) {
		Result result;		
		int trID = getID();		
		tr = new DepositTransaction(trID, userMode, password, accNumber, amount);	
		result = tr.executeTransaction();
		
		if (result == null)
			return new Result("FAILED", "Transaction not completed");
		else
			return result;
	} // deposit

	public Result withdraw(String accNumber, int amount) {		
		Result result = null;		
		int trID = getID();	
		tr = new WithdrawTransaction(trID , userMode, password, accNumber, amount);
		result = tr.executeTransaction();
		
		if (result == null)
			return new Result("FAILED", "Transaction not completed");
		else
			return result;	
	} // withdraw
	
	public Result getBalance(String accNumber) {
		Result result = null;
		tr = new BalanceTransaction(userMode, password, accNumber);
		result = tr.executeTransaction();
		
		if (result == null)
			return new Result("FAILED", "Transaction not completed");
		else
			return result;	
	} // getAccountBalance
	
	public Result transfer(String fromAccNumber, String toAccNumber, int amount) {
		Result result = null;
		
		int trID = getID();
		
		tr = new TransferTransaction(trID , userMode, password, fromAccNumber, toAccNumber, amount);
		result = tr.executeTransaction();
		
		if (result == null)
			return new Result("FAILED", "Transaction not completed");
		else
			return result;	
	} // transfer
} // TransactionController