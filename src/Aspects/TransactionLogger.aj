package Aspects;

import Global.Result;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;
import Model.Exceptions.BankSystemException;
import Model.Transactions.Transaction;
import Services.Logging.Logger;

public aspect TransactionLogger extends Transactions
{
	// Logs Transactions in Transaction Log file
	// Logs completed Transaction
	after (Transaction transaction) returning(Result result) : critical_transactions(transaction) {		
		
		String log = String.format("%d \t %-8s  \t %-15s \t %s \t %d \t %s", transaction.getTransactionID(), transaction.getUserMode().name() , 
																			transaction.getAffectingAccNumbers(), transaction.getTransactionType(), 
																			transaction.getAmount(), result.getStatus() );
		
		if (result.getStatus().equals("FAILED"))
			log += "\t " + result.getInfo();
		
//		String log = "" + transaction.getTransactionID()   	   + "\t " 	// Transaction ID
//						+ transaction.getUserMode().name()	   + "\t "  // UserMode	
//						+ transaction.getAffectingAccNumbers() + "\t "	// Account Number	
//						+ transaction.getTransactionType() 	   + "\t " 	// Transaction Type
//						+ transaction.getAmount() 		  	   + "\t "	// Amount
//						+ result.getKey();								// Status
		
		Logger.logTransaction(log);
	} // before bank_operations
	
	// Logs failed Transaction
	after (Transaction transaction) throwing (BankSystemException e)  : critical_transactions(transaction) {
		String log = String.format("%d \t %-8s  \t %-15s \t %s \t %d \t FAILED \t %s", transaction.getTransactionID(), transaction.getUserMode().name() , 
																					transaction.getAffectingAccNumbers(), transaction.getTransactionType(), 
																					transaction.getAmount(), e.getMessage() );
//		System.out.println("Log: " + log);
//		String log = "" + transaction.getTransactionID()   	   + "\t " 	// Transaction ID
//						+ transaction.getUserMode().name()	   + "\t "  // UserMode	
//						+ transaction.getAffectingAccNumbers() + "\t " 	// 	Account Number	
//						+ transaction.getTransactionType() 	   + "\t " 	// Transaction Type
//						+ transaction.getAmount() 		  	   + "\t "	// Amount
//						+ "FAILED" 							   + "\t "	// Status
//						+ e.getMessage();								// Failure Reason
		
		Logger.logTransaction(log);
	} // before bank_operations
	
	// Logs Transactions in Transaction Log file
	
	before(BankAccount account, int amount) : bank_operations(account, amount) {
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = "Account:" + account.getAccNum() + "\tPrior Balance: " + account.getBalance()
		        + " \tOperation: " + operation + "\tStatus: Pending" + " \tAmount: " + amount;
		
		Logger.logAccountTransaction(account.getAccNum(), account.getAccountType().toString() , transactionLog);
	} // before bank_operations
	
	// Advice to log after a deposit transaction has completed
	after(BankAccount account, int amount) returning : bank_operations(account, amount) {
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = "Account:" + account.getAccNum() + "\tNew Balance:   " + account.getBalance()
		        				+ " \tOperation: " + operation + "\tStatus: Completed" + " \tAmount: " + amount + "\r\n";
		
		Logger.logAccountTransaction(account.getAccNum(), account.getAccountType().toString(), transactionLog);
	} // before bank_operations
	
	// Advice to log after a deposit transaction has failed
	after(BankAccount account, int amount) throwing (AccountOperationException e) : bank_operations(account, amount) {
		
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
//		System.out.println(operation);

		
		String transactionLog = "Account:" + account.getAccNum() + "\tNew Balance:   " + account.getBalance()
		        + " \tOperation: " + operation + "\tStatus: Failed" + " \tAmount: " + amount + "\r\n";     
		
		Logger.logAccountTransaction(account.getAccNum(), account.getAccountType().toString(), transactionLog);
	} // before bank_operations
	
	private String getOperation(String operationOrigin) {
		if (operationOrigin.contains("deposit"))
			return "Deposit ";
		else if ( operationOrigin.contains("withdraw") )
			return "Withdrawal";
		else 
			return "Unknown Operation";
	} // getOperation
	
}
