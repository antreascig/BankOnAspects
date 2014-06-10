package Aspects;

import Model.AccountOperationException;

public aspect TransactionLogger extends OperationHandling
{
	// Advice to log before a deposit transaction
	before(Model.BankAccount account, int amount) : deposit(account, amount) 
	{
		String transactionLog = "Account:" + account.getAccNum() + "\tPrior Balance: " + account.getBalance()
				             + " \tOperation: Deposit \tStatus: Pending \tAmount: " + amount;
		
		System.out.println(transactionLog);		          
		
		// Logger.write(transactionLog);
	} // before deposit
	
	// Advice to log after a deposit transaction has completed
	after(Model.BankAccount account, int amount) returning : deposit(account, amount) 
	{
		String transactionLog = "Account:" + account.getAccNum() + "\tNew Balance: " + account.getBalance()
				             + " \tOperation: Deposit \tStatus: Completed \tAmount: " + amount;
		
		System.out.println(transactionLog);		          
		
		// Logger.write(transactionLog);
	} // before deposit
	
	// Advice to log after a deposit transaction has failed
		after(Model.BankAccount account, int amount) throwing (AccountOperationException e) : deposit(account, amount) 
		{
			String transactionLog = "Account:" + account.getAccNum() + "\tNew Balance: " + account.getBalance()
					             + " \tOperation: Deposit \tStatus: Failed \tAmount: " + amount;
			
			System.out.println(transactionLog);		          
			
			// Logger.write(transactionLog);
		} // before deposit
	
	// Advice to log before a withdrawal transaction
	before(Model.BankAccount account, int amount) : withdraw(account, amount) 
	{
		String transactionLog = "Account:" + account.getAccNum() + "\tPrior Balance: " + account.getBalance()
				             + " \tOperation: Withdrawal \tStatus: Pending \tAmount: " + amount;
		
		System.out.println(transactionLog);		          
		
		// Logger.write(transactionLog);
	} // before deposit

	// Advice to log after a withdrawal transaction has completed
	after(Model.BankAccount account, int amount) returning : withdraw(account, amount) 
	{
		String transactionLog = "Account:" + account.getAccNum() + "\tNew Balance: " + account.getBalance()
				             + " \tOperation: Withdrawal \tStatus: Completed \tAmount: " + amount;
		
		System.out.println(transactionLog);		          
		
		// Logger.write(transactionLog);
	} // before deposit
	
	// Advice to log after a withdrawal transaction has failed
			after(Model.BankAccount account, int amount) throwing (AccountOperationException e) : withdraw(account, amount) 
			{
				String transactionLog = "Account:" + account.getAccNum() + "\tNew Balance: " + account.getBalance()
						             + " \tOperation: Withdrawal \tStatus: Failed \tAmount: " + amount;
				
				System.out.println(transactionLog);		          
				
				// Logger.write(transactionLog);
			} // before deposit
	
}
