package Aspects;

import Model.AccountOperationException;

// Aspect that validates the account operations based on constraints
// amount must be >= 0 and the account must contain balance >= amount
public aspect BankConstraints extends OperationHandling{
		
	// Advice to check deposit(..)
	before(Model.BankAccount account, int amount) : deposit(account, amount) 
	{
		if ( amount < 0 )
		{
//			System.out.println("Exception thrown - Error: Deposit amount must not be negative");
			throw new AccountOperationException("Deposit amount must not be negative.");
		} // if
	} // before deposit
	
	// Advice to check withdrawal
	before(Model.BankAccount account, int amount) : withdraw(account, amount) 
	{
		int accountBalance = account.getBalance();
		
		if ( accountBalance < amount )
		{
//			System.out.println("Exception thrown - Error: The account contains insufficient funds");
			throw new AccountOperationException("The account contains insufficient funds.");
		} // if
		
		if ( amount < 0 )
		{
//			System.out.println("Exception thrown - Error: Withdrawal amount must not be negative");
			throw new AccountOperationException("Withdrawal amount must not be negative.");
		} // if
	} // before withdraw
}
