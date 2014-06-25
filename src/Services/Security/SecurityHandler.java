package Services.Security;

import Controllers.AccountsController;
import Global.UserMode;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountAuthenticationException;
import Model.Exceptions.AccountAuthorisationException;
import Model.Exceptions.AccountOperationException;
import Model.Transactions.Transaction;

public class SecurityHandler 
{	
	private static boolean evaluated = false;

	public static void evaluateTransaction(Transaction tr) throws AccountOperationException {
		evaluated = true;
		
		if ( tr.getUserMode() == UserMode.ADMIN )
			return;
		
		if ( !authenticated(tr) )
		 {
			 throw new AccountAuthenticationException("Not authenticated for transaction");
		 }
		
		 if ( !authorised(tr) )
		 {
			 throw new AccountAuthorisationException("Not authorized for transaction");
		 }
		 
	} // login
	
	private static boolean authenticated(Transaction tr) {
		String accNumToAuthenticate = tr.getAffectingAccNumbers().get(0);
		BankAccount accountToAuthenticate = AccountsController.getAccount(accNumToAuthenticate);
		
		Integer clientPass = tr.getClientPassword();
		
		if ( clientPass == null )
		{
			throw new AccountOperationException("Client Password not provided or not found.");
		}
		
		return accountToAuthenticate.evaluateCredentials(clientPass);
	} // authenticated

	private static boolean authorised (Transaction tr) {
		String accNumToAuthorise = tr.getAffectingAccNumbers().get(0);
		BankAccount accountToAuthorise = AccountsController.getAccount(accNumToAuthorise);
		
		return accountToAuthorise.getOperationAvailable(tr.getTransactionType());	
	} // Authorised
	
	
	/*
	 *	The following 2 methods are for testing purposes only.
	 *	They should not be used for any other purpose.
	 *	They are meant for a single transaction evaluation
	 *	They do not take into consideration synchronisation/concurrency. 
	 *
	 *	evaluated is used as a flag that indicates whether the corresponding aspect was called and the transaction was evaluated 
	 *	transactionEvaluated() returns the flag and resetEvaluation() resets the value
	 */
	
	public static boolean transactionEvaluated() {
		return evaluated;
	} // transactionEvaluated
	
	public static void resetEvaluation() {
		evaluated = false;
	} // resetEvaluation

}
