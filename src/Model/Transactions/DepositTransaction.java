package Model.Transactions;

import java.util.ArrayList;

import Controllers.AccountsController;
import Global.Result;
import Global.TransactionType;
import Global.UserMode;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;

public class DepositTransaction implements Transaction {

	private UserMode userMode;
	private String toAccNumber;
	private Integer password;
	private int amount;
	private Integer transactionID;
	
	private Result<?> result;
	private ArrayList<String> affectingAccNumbers;
	
	public DepositTransaction(int trID, UserMode mode, Integer pass, String toAccNum, int am) {
		transactionID = trID;
		userMode = mode;
		password = pass;
		toAccNumber = toAccNum;
		result = null;
		amount = am;
		
		affectingAccNumbers = new ArrayList<>();
		affectingAccNumbers.add(toAccNum);
		
	} // DepositTransaction

	@Override
	public void executeTransaction() {
		try {
			AccountsController accountController = AccountsController.getInstance();
			BankAccount account = accountController.getAccount(toAccNumber);
			account.deposit(amount);
			result = new Result<>("COMPLETED", null);
		}
		catch (AccountOperationException exception) {
			result = new Result<>("FAILED", exception.getMessage()); // Transaction Failed Because it broke a BankConstraint
		} // catch	
	}

	@Override
	public int getClientPassword() {
		return this.password;
	} // getClientPassword

	@Override
	public UserMode getUserMode() {
		return this.userMode;
	} // getUserMode

	@Override
	public TransactionType getTransactionType() {
		return TransactionType.DEPOSIT;
	} // getTransactionType

	@Override
	public Result<?> getResult() {
		return this.result;
	} // getResult

	@Override
	public Integer getAmount() {
		return this.amount;
	} // getAmount

	@Override
	public ArrayList<String> getAffectingAccNumbers() {
		return this.affectingAccNumbers;
	} // getAffectingAccNumber

	@Override
	public Integer getTransactionID() {
		return this.transactionID;
	} // geTransactionNumber
	
} // DepositTransaction
