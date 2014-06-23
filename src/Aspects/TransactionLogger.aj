package Aspects;

import Global.Pair;
import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;
import Model.Transactions.Transaction;
import Services.Logging.Logger;

public aspect TransactionLogger extends Transactions
{
	after (Transaction transaction) returning : logged_transactions(transaction)
	{		
		Pair<?> result = transaction.getResult();
					
		String log = "" + transaction.getTransactionID()   	  + "\t " 	// Transaction ID
						+ transaction.getAffectingAccNumber() + "\t"	// Account Number	
						+ transaction.getTransactionType() 	  + "\t " 	// Transaction Type
						+ transaction.getAmount() 		  	  + "\t "	// Amount
						+ result.getKey();								// Status
		
		Logger.logTransaction(log);
	} // before bank_operations
	
	after (Transaction transaction) throwing (AccountOperationException e)  : logged_transactions(transaction)
	{				
		String log = "" + transaction.getTransactionID()   	  + "\t " 	// Transaction ID
						+ transaction.getAffectingAccNumber() + "\t " 	// 	Account Number	
						+ transaction.getTransactionType() 	  + "\t " 	// Transaction Type
						+ transaction.getAmount() 		  	  + "\t "	// Amount
						+ "FAILED" 							  + "\t "	// Status
						+ e.getMessage();								// Failure Reason
		
		Logger.logTransaction(log);
	} // before bank_operations
	
	
	
	before(BankAccount account, int amount) : bank_operations(account, amount) 
	{
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = getLogMessage(account,operation,"Pending", amount);          
		
		Logger.logAccountTransaction(account.getAccNum(), transactionLog);
	} // before bank_operations
	
	// Advice to log after a deposit transaction has completed
	after(BankAccount account, int amount) returning : bank_operations(account, amount) 
	{
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = getLogMessage(account,operation,"Completed", amount);  
		
		Logger.logAccountTransaction(account.getAccNum(), transactionLog);
		Logger.logAccountTransaction(account.getAccNum(), "");	// Prints empty line to group logs on same transaction together
	} // before bank_operations
	
	// Advice to log after a deposit transaction has failed
	after(BankAccount account, int amount) throwing (AccountOperationException e) : bank_operations(account, amount) 
	{
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = getLogMessage(account,operation,"Failed ", amount);          
		
		Logger.logAccountTransaction(account.getAccNum(), transactionLog);
		Logger.logAccountTransaction(account.getAccNum(), "");	// Prints empty line to group logs on same transaction together
	} // before bank_operations
	
	
	private String getLogMessage(BankAccount account, String operation, String status, int amount ) {
		
		String log = "Account:" + account.getAccNum() + "\tPrior Balance: " + account.getBalance()
        + " \tOperation: " + operation + "\tStatus: " + status + " \tAmount: " + amount;
		
		return log;
	} // getLogMessage
	
	private String getOperation(String operationOrigin)
	{
		if (operationOrigin.contains("deposit"))
			return "Deposit ";
		else if ( operationOrigin.contains("withdraw") )
			return "Withdrawal";
		else 
			return "Unknown Operation";
	} // getOperation
	
}
