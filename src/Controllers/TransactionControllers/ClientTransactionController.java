package Controllers.TransactionControllers;

import Global.UserMode;
import Login.LoginHandler;


public class ClientTransactionController extends TransactionController 
{
	private LoginHandler loginService;
	
	public ClientTransactionController(UserMode uRole, Integer pin) 
	{
		super(uRole);
		
		loginService = new LoginHandler(pin);
	}
	
	public LoginHandler getLoginService()
	{
		return this.loginService;
	}
	
}
