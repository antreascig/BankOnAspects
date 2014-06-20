package Model.Transactions;

import Controllers.AccountsController;
import Global.Pair;
import Global.TransactionType;
import Global.UserMode;
import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;

public class BalanceTransaction implements Transaction {
	
	private UserMode userMode;
	private String fromAccNumber;
	private int password;
	
	private Pair<?> result;
	
	public BalanceTransaction(UserMode mode ,int pass, String fromAccNum) {
		userMode = mode;
		password = pass;
		fromAccNumber = fromAccNum;
		result = null;
	} // BalanceTransaction

	@Override
	public void executeTransaction() {
		try
		{
			BankAccount account = AccountsController.getAccount(fromAccNumber);
			int balance  = account.getBalance();
			result = new Pair<>("COMPLETED", balance);
		} catch (AccountOperationException exception) {
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
		return TransactionType.BALANCE;
	} // getTransactionType

	@Override
	public Pair<?> getResult() {
		return result;
	} // getResult

	@Override
	public Integer getAmount() {
		return null;
	} // getAmount

}
