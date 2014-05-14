package Aspects;

import BankOnAspect.AccountOperationException;

// Aspect that validates the account operations based on contraints
// amount must be >= 0 and the account must contain balance >= amount
public aspect BankConstraints extends OperationHandling{
		
	// Advice to check deposit(..)
	before(BankOnAspect.BankAccount account, int amount) : deposit(account, amount) 
	{
		if ( amount < 0 )
			throw new AccountOperationException("Deposit amount must not be negative");
	}	
	
	// Advice to check withdrawal
	before(BankOnAspect.BankAccount account, int amount) : withdraw(account, amount) 
	{
		int accountBalance = account.getBalance();
		if ( accountBalance < amount )
			throw new AccountOperationException("The account contains insufficient funds");
		
		if ( amount < 0 )
			throw new AccountOperationException("Withdrawal amount must not be negative");	
	}
}
