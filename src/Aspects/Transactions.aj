package Aspects;

import Model.BankAccounts.BankAccount;
import Model.Transactions.Transaction;


public abstract aspect Transactions {
	
	// Matches execution of deposit method in BankAccount
	pointcut deposit(BankAccount account, int amount) : 
	   execution(void BankAccount.deposit(int)) && target(account) && args(amount) ;
	
	// Matches execution of withdraw method in BankAccount
	pointcut withdraw(BankAccount account, int amount) : 
		   execution(void BankAccount.withdraw(int)) && target(account) && args(amount);
	
	// Matches the previous two pointcuts - Combination of all operations
	pointcut bank_operations( BankAccount account, int amount) : 
		       deposit( BankAccount, int ) && target(account) && args(amount)
		    || withdraw(BankAccount, int ) && target(account) && args(amount);
		    
		    
	pointcut basic_transactions(Transaction transaction) : 
			execution(void Transaction.executeTransaction()) && target(transaction);
	} 
