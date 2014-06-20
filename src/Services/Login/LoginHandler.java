package Services.Login;

import Model.AccountOperationException;

public class LoginHandler 
{	
	private static boolean isAuntenticated = false;

	public static void login() throws AccountOperationException {
		isAuntenticated = false;		
		
		if ( !authenticated() )
		 {
			 throw new AccountOperationException("Not authenticated for transaction");
		 }
		
		 if ( !authorized() )
			 throw new AccountOperationException("Not authorized for transaction");	
		 
	} // login
	
	private static boolean authenticated() {
//		isAuntenticated = account.evaluateCredentials(password);	
		
		return false;
	}

	private static boolean authorized () {
		
		return false;
	}
	
	public static boolean isAuntenticated()
	{
		return isAuntenticated;
	} // getIsAuntenticated
	
	public static void resetAuthentication()
	{
		isAuntenticated = false;
	}
	

}
