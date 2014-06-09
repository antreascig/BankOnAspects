package Aspects;

public abstract aspect OperationHandling {
	
	// Matches execution of deposit method in BankAccount
	pointcut deposit(Model.BankAccount account, int amount) : 
	   execution(void BankOnAspect.BankAccount.deposit(int)) && target(account) && args(amount) ;
	
	// Matches execution of withdraw method in BankAccount
	pointcut withdraw(Model.BankAccount account, int amount) : 
		   execution(void BankOnAspect.BankAccount.withdraw(int)) && target(account) && args(amount);
} 
