package Aspects;

import Model.AccountOperationException;
import Model.BankAccount;

public aspect TransactionLogger extends Transactions
{
	
	before(Model.BankAccount account, int amount) : bank_operations(account, amount) 
	{
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = getLogMessage(account,operation,"Pending", amount);
		
		System.out.println(transactionLog);		          
		
		// Logger.write(transactionLog);
	} // before bank_operations
	
	// Advice to log after a deposit transaction has completed
	after(Model.BankAccount account, int amount) returning : bank_operations(account, amount) 
	{
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = getLogMessage(account,operation,"Completed", amount);
		
		System.out.println(transactionLog);    
		
		// Logger.write(transactionLog);
	} // before bank_operations
	
	// Advice to log after a deposit transaction has failed
	after(Model.BankAccount account, int amount) throwing (AccountOperationException e) : bank_operations(account, amount) 
	{
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = getLogMessage(account,operation,"Failed", amount);
		
		System.out.println(transactionLog);		          
		
		// Logger.write(transactionLog);
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
