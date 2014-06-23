package Aspects;

public aspect AspectPrecedence 
{
	declare precedence : TransactionSecurity, TransactionSynchronization, TransactionLogger, ErrorHandling, BankConstraints;
} // AspectPrecedence
