package Controllers.TransactionControllers;

import Global.UserMode;

public class ClientTransactionController extends TransactionController 
{
	private int password;
	public ClientTransactionController(UserMode uRole, Integer pin) 
	{
		super(uRole);
		
		password = pin;
	}
	
	public int getPassword()
	{
		return this.password;
	} // getPassword
	
}
