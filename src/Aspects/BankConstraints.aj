package Aspects;

import BankOnAspect.AccountOperationException;

// Aspect that validates the account operations based on contraints
// amount must be >= 0 and the account must contain balance >= amount
public aspect BankConstraints extends OperationHandling{
		
	// Advice to check deposit(..)
	before(BankOnAspect.BankAccount account, int amount) : deposit(account, amount) 
	{
		if ( amount < 0 )
		{
			System.out.println("Exception thrown");
			throw new AccountOperationException("Deposit amount must not be negative");
		} // if
	}	
	
	// Advice to check withdrawal
	before(BankOnAspect.BankAccount account, int amount) : withdraw(account, amount) 
	{
		int accountBalance = account.getBalance();
		
		if ( accountBalance < amount )
		{
			System.out.println("Exception thrown");
			throw new AccountOperationException("The account contains insufficient funds");
		} // if
		
		if ( amount < 0 )
		{
			System.out.println("Exception thrown");
			throw new AccountOperationException("Withdrawal amount must not be negative");
		} // if
	}
}
