package Model.Transactions;

import java.util.ArrayList;

import Controllers.AccountsController;
import Global.Pair;
import Global.TransactionType;
import Global.UserMode;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;

public class BalanceTransaction implements Transaction {
	
	private UserMode userMode;
	private String fromAccNumber;
	private Integer password;
	
	private Pair<?> result;
	private ArrayList<String> affectingAccNumbers;
	
	public BalanceTransaction(UserMode mode ,Integer pass, String fromAccNum) {
		userMode = mode;
		password = pass;
		fromAccNumber = fromAccNum;
		result = null;
		
		affectingAccNumbers = new ArrayList<>();
		affectingAccNumbers.add(fromAccNum);
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
		return this.password;
	} // getClientPassword

	@Override
	public UserMode getUserMode() {
		return this.userMode;
	} // getUserMode

	@Override
	public TransactionType getTransactionType() {
		return TransactionType.BALANCE;
	} // getTransactionType

	@Override
	public Pair<?> getResult() {
		return this.result;
	} // getResult

	@Override
	public Integer getAmount() {
		return null;
	} // getAmount

	@Override
	public ArrayList<String> getAffectingAccNumbers() {		
		return this.affectingAccNumbers;
	} // getAffectingAccNumber

	@Override
	public Integer getTransactionID() {
		return null;
	} // getTransactionNumber
} // BalanceTransaction
