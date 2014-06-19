package Login;

import Controllers.AccountController;
import Model.BankAccount;

public class LoginHandler 
{
	private Integer password;
	private boolean isAuntenticated;
	
	public LoginHandler(Integer pin)
	{
		password = pin;
	}

	public void login(String accNumber, int amount) {
		BankAccount account = AccountController.getAccount(accNumber);
		
		isAuntenticated = account.evaluateCredentials(password);
		
		
	}

	public Integer getPassword() {
		return password;
	}
	
	public boolean getIsAuntenticated()
	{
		return this.isAuntenticated;
	} // getIsAuntenticated
	
	public void resetAuthentication()
	{
		isAuntenticated = false;
	}
	

}
