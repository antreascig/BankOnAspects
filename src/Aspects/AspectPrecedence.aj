package Aspects;

public aspect AspectPrecedence 
{
	declare precedence : TransactionLogger, ErrorHandling, BankConstraints;
} // AspectPrecedence
