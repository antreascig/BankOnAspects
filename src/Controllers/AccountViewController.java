package Controllers;


import Interface.AccountView;

public class AccountViewController {
	
	private String accNumber;
	private AccountView accountView;
	
	public AccountViewController(String accNum)
	{
		accNumber = accNum;
	}
	
	public void viewAccount()
	{
		int balance = getBalance();
		accountView = new AccountView(accNumber, balance, this );
		accountView.setVisible(true);
	}
	
	public boolean isInteger(String s) {
	    
	    // only got here if we didn't return false
	    return true;
	} // isInteger

	public void withdraw(int amount) {
		
	}

	public void deposit(int amount) {
		
	}

	public Integer getBalance() {
		Integer balance = 1;
		
		return balance;
	}
}
