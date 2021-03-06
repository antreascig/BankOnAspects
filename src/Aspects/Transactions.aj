package Aspects;

import Model.BankAccounts.BankAccount;
import Model.Transactions.Transaction;
import Model.Transactions.BalanceTransaction;
import Global.Result;


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
	
	pointcut transactions(Transaction transaction) : 
			execution(Result Transaction.executeTransaction()) && target(transaction);
	
	pointcut critical_transactions(Transaction transaction) : 
		( execution(Result Transaction.executeTransaction()) && target(transaction) ) && !within(BalanceTransaction);	
}  // Transactions
