package Services.Security;

import Model.AccountOperationException;
import Model.Transactions.Transaction;

public class SecurityHandler 
{	
	private static boolean evaluated = false;

	public static void evaluateTransaction(Transaction tr) throws AccountOperationException {
		evaluated = true;
		if ( !authenticated() )
		 {
//			 throw new AccountOperationException("Not authenticated for transaction");
		 }
		
		 if ( !authorised() )
		 {
//			 throw new AccountOperationException("Not authorized for transaction");
		 }
		 
	} // login
	
	private static boolean authenticated() {
//		isAuntenticated = account.evaluateCredentials(password);	
		
		return false;
	} // authenticated

	private static boolean authorised () {
		
		return false;
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
