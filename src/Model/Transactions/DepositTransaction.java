package Model.Transactions;

import Controllers.AccountsController;
import Global.Pair;
import Global.TransactionType;
import Global.UserMode;
import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;

public class DepositTransaction implements Transaction {

	private UserMode userMode;
	private String toAccNumber;
	private int password;
	private int amount;
	
	private Pair<?> result;
	
	public DepositTransaction(UserMode mode, Integer pass, String toAccNum, int am) {
		userMode = mode;
		password = pass;
		toAccNumber = toAccNum;
		result = null;
		amount = am;
	}

	@Override
	public void executeTransaction() {
		try
		{
			BankAccount account = AccountsController.getAccount(toAccNumber);
			account.deposit(amount);
			result = new Pair<>("COMPLETED", null);
		}
		catch (AccountOperationException exception)
		{
			result = new Pair<>("FAILED", exception.getMessage()); // Transaction Failed Because it broke a BankConstraint
		} // catch	
	}

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
		// TODO Auto-generated method stub
		return null;
	} // getAmount
}
