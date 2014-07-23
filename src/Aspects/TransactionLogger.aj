package Aspects;

import Global.Result;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;
import Model.Transactions.Transaction;
import Services.Logging.Logger;
import org.aspectj.lang.annotation.AdviceName;

public aspect TransactionLogger extends Transactions
{
	// Logs Transactions in Transaction Log file
	// Logs completed Transaction
	@AdviceName("LogTransactionNoError")
	after (Transaction transaction) returning(Result result) : critical_transactions(transaction) {		
		
		String log = String.format("%d \t %-8s  \t %-15s \t %s \t %d \t %s", transaction.getTransactionID(), transaction.getUserMode().name() , 
																			transaction.getAffectingAccNumbers(), transaction.getTransactionType(), 
																			transaction.getAmount(), result.getStatus() );
		if (result.getStatus().equals("FAILED"))
			log += "\t " + result.getInfo();
		
		Logger.logTransaction(log);
	} // before bank_operations
	
	// Logs failed Transaction
	@AdviceName("LogTransactionWithError")
	after (Transaction transaction) throwing (AccountOperationException e)  : critical_transactions(transaction) {
		String log = String.format("%d \t %-8s  \t %-15s \t %s \t %d \t FAILED  \t %s", transaction.getTransactionID(), transaction.getUserMode().name() , 
																					transaction.getAffectingAccNumbers(), transaction.getTransactionType(), 
																					transaction.getAmount(), e.getMessage() );
		Logger.logTransaction(log);
	} // before bank_operations
	
	// Logs Account Operation in Account Log file
	@AdviceName("LogBeforeOperation")
	before(BankAccount account, int amount) : bank_operations(account, amount) {
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = "Account:" + account.getAccNum() + "\tPrior Balance: " + account.getBalance()
		        + " \tOperation: " + operation + "\tStatus: Pending" + " \tAmount: " + amount;
		
		Logger.logAccountTransaction(account.getAccNum(), account.getAccountType().toString() , transactionLog);
	} // before bank_operations
	
	
	// Logs Account Operation in Account Log file that completed correctly
	@AdviceName("LogAfterOperationNoError")
	after(BankAccount account, int amount) returning : bank_operations(account, amount) {
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());
		
		String transactionLog = "Account:" + account.getAccNum() + "\tNew Balance:   " + account.getBalance()
		        				+ " \tOperation: " + operation + "\tStatus: Completed" + " \tAmount: " + amount + "\r\n";
		
		Logger.logAccountTransaction(account.getAccNum(), account.getAccountType().toString(), transactionLog);
	} // before bank_operations
	
	// Logs Account Operation in Account Log file that did not complete correctly
	@AdviceName("LogAfterOperationWithError")
	after(BankAccount account, int amount) throwing (AccountOperationException exception) : bank_operations(account, amount) {
		
		String operation = getOperation(thisJoinPoint.getSignature().toShortString());

		String transactionLog = "Account:" + account.getAccNum() + "\tNew Balance:   " + account.getBalance()
		        + " \tOperation: " + operation + "\tStatus: Failed " + " \tAmount: " + amount + "\t " +  exception.getMessage() + "\r\n";     
		
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
