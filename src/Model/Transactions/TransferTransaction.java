package Model.Transactions;

import java.util.ArrayList;

import Controllers.AccountsController;
import Global.Result;
import Global.TransactionType;
import Global.UserMode;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;

public class TransferTransaction implements Transaction {
	private UserMode userMode;
	private String fromAccNumber, toAccNumber;
	private Integer transactionID, password;
	private int amount;
	
	private ArrayList<String> affectingAccNumbers;
	
	public TransferTransaction(int trID, UserMode mode, Integer pass, String fromAccNum, String toAccNum, int am) {
		transactionID = trID;
		userMode = mode;
		password = pass;
		fromAccNumber = fromAccNum;
		toAccNumber = toAccNum;
		amount = am;
		
		affectingAccNumbers = new ArrayList<>();
		affectingAccNumbers.add(fromAccNum);
		affectingAccNumbers.add(toAccNum);
	} // TransferTransaction

	@Override
	public Result executeTransaction() {
		try {
			AccountsController accountController = AccountsController.getInstance();
			BankAccount fromAccount = accountController.getAccount(fromAccNumber);
			BankAccount toAccount = accountController.getAccount(toAccNumber);
			
			fromAccount.withdraw(amount);
			toAccount.deposit(amount);
			return new Result("COMPLETED", "");
		}
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
		return TransactionType.TRANSFER;
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
	} // getTransactionNumber
} // TransferTransaction
