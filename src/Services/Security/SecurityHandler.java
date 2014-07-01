package Services.Security;

import Controllers.AccountsController;
import Model.BankAccounts.BankAccount;
import Model.Transactions.Transaction;

public class SecurityHandler 
{	
	private static boolean evaluated = false;
	
	public static boolean authenticated(Transaction tr) {	
		String accNumToAuthenticate = tr.getAffectingAccNumbers().get(0);
		AccountsController accountController = AccountsController.getInstance();
		BankAccount accountToAuthenticate = accountController.getAccount(accNumToAuthenticate);
		
		Integer clientPass = tr.getClientPassword();	
		if ( clientPass == null )
			return false;
		
		return accountToAuthenticate.evaluateCredentials(clientPass);
	} // authenticated

	public static boolean authorised (Transaction tr) {	
		String accNumToAuthorise = tr.getAffectingAccNumbers().get(0);
		AccountsController accountController = AccountsController.getInstance();
		BankAccount accountToAuthorise = accountController.getAccount(accNumToAuthorise);
		
		return accountToAuthorise.getOperationAvailable(tr.getTransactionType());	
	} // Authorised
	
	
	/*
	 *	The following 3 methods are for testing purposes only.
	 *	They should not be used for any other purpose.
	 *	They are meant for a single transaction evaluation
	 *	They do not take into consideration synchronisation/concurrency. 
	 *
	 *	evaluated is used as a flag that indicates whether the corresponding aspect was called and the transaction was evaluated 
	 *	transactionEvaluated() returns the flag, setEvaluated() raises the flag and resetEvaluation() resets the value
	 */
	
	public static void setEvaluated() {
		evaluated = true;
	} // setEvaluated
	
	public static boolean transactionEvaluated() {
		return evaluated;
	} // transactionEvaluated
	
	public static void resetEvaluation() {
		evaluated = false;
	} // resetEvaluation

}
