package Controllers;

import Global.Result;
import Global.UserMode;
import Interface.AccountView;

public class AccountViewController {
	
	private String accNumber;
	private AccountView accountView;
	private TransactionController trController;
	
	public AccountViewController(String accNum) {
		accNumber = accNum;
		trController = new TransactionController(UserMode.ADMIN);
	} // AccountViewController
	
	public void viewAccount() {
		accountView = new AccountView(accNumber, this );
		accountView.setVisible(true);
	} // viewAccount

	public void withdraw(int amount) {
		Result result = trController.withdraw(accNumber, amount);
		checkResult(result);
	} // withdraw

	public void deposit(int amount) {
		Result result = trController.deposit(accNumber, amount);
		checkResult(result);
	} // deposit

	public Integer getBalance() {
		Result result = trController.getBalance(accNumber);
		Integer balance = null;
		
		boolean trCompleted = checkResult(result);
		
		if ( trCompleted )
			balance = Integer.parseInt(result.getInfo());
		
		return balance;
	} // getBalance

	private boolean checkResult(Result result) {
		String status = result.getStatus();
		if ( status.equals("COMPLETED")) 
			return true;
		else if (status.equals("FAILED")) {
			String failureReason =  (String) result.getInfo();
			accountView.showErrorMessage(failureReason);
			return false;
		} // else if
		else {
			accountView.showErrorMessage("Invalid Response from Server. Try again later.");
			return false;
		} // else
	} // checkResult

	public boolean isInteger(String amountString) {
		try {
			Integer.parseInt(amountString);
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}	
	}
} // AccountViewController
