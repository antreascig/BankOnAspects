package Model.Transactions;

import java.util.ArrayList;

import Controllers.AccountsController;
import Global.Pair;
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
	
	private Pair<?> result;
	private ArrayList<String> affectingAccNumbers;

	
	public WithdrawTransaction(int trID, UserMode mode, Integer pass, String fromAccNum, int am) {
		transactionID = trID;
		userMode = mode;
		password = pass;
		fromAccNumber = fromAccNum;
		result = null;
		amount = am;
		
		affectingAccNumbers = new ArrayList<>();
		affectingAccNumbers.add(fromAccNumber);
		
	} // WithdrawTransaction

	@Override
	public void executeTransaction() {
		try {
			AccountsController accountController = AccountsController.getInstance();
			BankAccount account = accountController.getAccount(fromAccNumber);
			account.withdraw(amount);
			result = new Pair<>("COMPLETED", null);
		}
		catch (AccountOperationException exception) {
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
		return TransactionType.DEPOSIT;
	} // getTransactionType

	@Override
	public Pair<?> getResult() {
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
	} // getTransactionID
} // WithdrawTransaction
