package Model.Transactions;

import Controllers.AccountsController;
import Global.Pair;
import Global.TransactionType;
import Global.UserMode;
import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;

public class WithdrawTransaction implements Transaction {

	private Integer transactionID;
	private UserMode userMode;
	private String toAccNumber;
	private Integer password;
	private int amount;
	
	private Pair<?> result;
	
	public WithdrawTransaction(int trID, UserMode mode, Integer pass, String toAccNum, int am) {
		transactionID = trID;
		userMode = mode;
		password = pass;
		toAccNumber = toAccNum;
		result = null;
		amount = am;
	} // WithdrawTransaction

	@Override
	public void executeTransaction() {
		try
		{
			BankAccount account = AccountsController.getAccount(toAccNumber);
			account.withdraw(amount);
			result = new Pair<>("COMPLETED", null);
		}
		catch (AccountOperationException exception)
		{
			result = new Pair<>("FAILED", exception.getMessage()); // Transaction Failed Because it broke a BankConstraint
		} // catch	
	} // executeTransaction

	@Override
	public int getClientPassword() {
		return password;
	} // getClientPassword

	@Override
	public UserMode getUserMode() {
		return userMode;
	} // getUserMode

	@Override
	public TransactionType getTransactionType() {
		return TransactionType.DEPOSIT;
	} // getTransactionType

	@Override
	public Pair<?> getResult() {
		return result;
	} // getResult

	@Override
	public Integer getAmount() {
		return amount;
	} // getAmount

	@Override
	public String getAffectingAccNumber() {
		return this.toAccNumber;
	} // getAffectingAccNumber

	@Override
	public Integer getTransactionID() {
		return transactionID;
	} // getTransactionID
} // WithdrawTransaction
