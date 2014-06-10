package Aspects;

public aspect AspectPrecedence 
{
	declare precedence : TransactionSynchronization, TransactionLogger, ErrorHandling, BankConstraints;
} // AspectPrecedence
