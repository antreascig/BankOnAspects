package Model.Transactions;

import java.util.ArrayList;

import Controllers.AccountsController;
import Global.Result;
import Global.TransactionType;
import Global.UserMode;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;

public class WithdrawTransaction implements Transaction {

	private Integer transactionID;
	private UserMode userMode;
	private String fromAccNumber;
	private Integer password;
	private int amount;
	
	private ArrayList<String> affectingAccNumbers;

	
	public WithdrawTransaction(int trID, UserMode mode, Integer pass, String fromAccNum, int am) {
		transactionID = trID;
		userMode = mode;
		password = pass;
		fromAccNumber = fromAccNum;
		amount = am;
		
		affectingAccNumbers = new ArrayList<>();
		affectingAccNumbers.add(fromAccNumber);
		
	} // WithdrawTransaction

	@Override
	public Result executeTransaction() {
		try {
			AccountsController accountController = AccountsController.getInstance();
			BankAccount account = accountController.getAccount(fromAccNumber);
			account.withdraw(amount);
			return new Result("COMPLETED", null);
		} // try
		catch (AccountOperationException exception) {
			return new Result("FAILED", exception.getMessage()); // Transaction Failed Because it broke a BankConstraint
		} // catch	
	} // executeTransaction

	@Override
	public Integer getClientPassword() {
		return this.password;
	} // getClientPassword

	@Override
	public UserMode getUserMode() {
		return this.userMode;
	} // getUserMode

	@Override
	public TransactionType getTransactionType() {
		return TransactionType.WITHDRAW;
	} // getTransactionType

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
	} // getTransactionID
} // WithdrawTransaction
