package Model.Transactions;

import java.util.ArrayList;

import Controllers.AccountsController;
import Global.Result;
import Global.TransactionType;
import Global.UserMode;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;

public class BalanceTransaction implements Transaction {
	
	private UserMode userMode;
	private String fromAccNumber;
	private Integer password;
	
	private ArrayList<String> affectingAccNumbers;
	
	public BalanceTransaction(UserMode mode ,Integer pass, String fromAccNum) {
		userMode = mode;
		password = pass;
		fromAccNumber = fromAccNum;		
		affectingAccNumbers = new ArrayList<>();
		affectingAccNumbers.add(fromAccNum);
	} // BalanceTransaction

	@Override
	public Result executeTransaction() {
		try
		{
			AccountsController accountController = AccountsController.getInstance();
			BankAccount account = accountController.getAccount(fromAccNumber);
			Integer balance  = account.getBalance();
			return new Result("COMPLETED", balance.toString());
		} catch (AccountOperationException exception) {
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
		return TransactionType.BALANCE;
	} // getTransactionType

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
