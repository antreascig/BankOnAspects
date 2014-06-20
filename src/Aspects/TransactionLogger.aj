package Aspects;

import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;
import Services.Logging.Logger;

public aspect TransactionLogger extends Transactions
{
	
	before(BankAccount account, int amount) : bank_operations(account, amount) 
	{
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = getLogMessage(account,operation,"Pending", amount);          
		
		Logger.logTransaction(transactionLog);
	} // before bank_operations
	
	// Advice to log after a deposit transaction has completed
	after(BankAccount account, int amount) returning : bank_operations(account, amount) 
	{
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = getLogMessage(account,operation,"Completed", amount);  
		
		Logger.logTransaction(transactionLog);
	} // before bank_operations
	
	// Advice to log after a deposit transaction has failed
	after(BankAccount account, int amount) throwing (AccountOperationException e) : bank_operations(account, amount) 
	{
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = getLogMessage(account,operation,"Failed ", amount);          
		
		Logger.logTransaction(transactionLog);
	} // before bank_operations
	
	
	private String getLogMessage(BankAccount account, String operation, String status, int amount )
	{
		String log = "Account:" + account.getAccNum() + "\tPrior Balance: " + account.getBalance()
        + " \tOperation: " + operation + "\tStatus: " + status + " \tAmount: " + amount;
		
		return log;
	}
	
	private String getOperation(String operationOrigin)
	{
		if (operationOrigin.contains("deposit"))
			return "Deposit";
		else if ( operationOrigin.contains("withdraw") )
			return "Withrawal";
		else 
			return "Unknown Operation";
	} // getOperation
	
}
