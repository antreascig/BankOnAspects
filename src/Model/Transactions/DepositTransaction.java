package Model.Transactions;

import java.util.ArrayList;

import Controllers.AccountsController;
import Global.Pair;
import Global.TransactionType;
import Global.UserMode;
import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;

public class DepositTransaction implements Transaction {

	private UserMode userMode;
	private String toAccNumber;
	private Integer password;
	private int amount;
	private Integer transactionID;
	
	private Pair<?> result;
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
		return amount;
	} // getAmount

	@Override
	public ArrayList<String> getAffectingAccNumbers() {
		return affectingAccNumbers;
	} // getAffectingAccNumber

	@Override
	public Integer getTransactionID() {
		return transactionID;
	} // geTransactionNumber
	
} // DepositTransaction
