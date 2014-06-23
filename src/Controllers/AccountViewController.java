package Controllers;


import Global.Pair;
import Global.UserMode;
import Interface.AccountView;

public class AccountViewController {
	
	private String accNumber;
	private AccountView accountView;
	private TransactionController controller;
	
	public AccountViewController(String accNum) {
		accNumber = accNum;
		controller = new TransactionController(UserMode.ADMIN);
	} // AccountViewController
	
	public void viewAccount()
	{
		accountView = new AccountView(accNumber, this );
		accountView.setVisible(true);
	} // viewAccount

	public void withdraw(int amount) {
		Pair<?> result = controller.withdraw(accNumber, amount);
		checkResult(result);
	} // withdraw

	public void deposit(int amount) {
		Pair<?> result = controller.deposit(accNumber, amount);
		checkResult(result);
	} // deposit

	public Integer getBalance() {
		Pair<?> result = controller.getBalance(accNumber);
		Integer balance = null;
		
		boolean trCompleted = checkResult(result);
		
		if ( trCompleted )
			balance = (Integer) result.getValue();
		
		return balance;
	} // getBalance

	private boolean checkResult(Pair<?> result) {
		String status = result.getKey();
		if ( status.equals("COMPLETED")) 
			return true;
		else if (status.equals("FAILED")) {
			String failureReason =  (String) result.getValue();
			accountView.showErrorMessage(failureReason);
			return false;
		} // else if
		else {
			accountView.showErrorMessage("Invalid Response from Server. Try again later.");
			return false;
		} // else
	} // checkResult

	public boolean isInteger(String amountString) 
	{
		try {
			Integer.parseInt(amountString);
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}	
	}
}
