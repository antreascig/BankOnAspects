package Aspects;

public aspect AspectPrecedence 
{
	declare precedence : LoginService, TransactionSynchronization, TransactionLogger, ErrorHandling, BankConstraints;
} // AspectPrecedence
