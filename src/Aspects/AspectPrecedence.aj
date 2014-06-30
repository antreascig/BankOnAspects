package Aspects;

public aspect AspectPrecedence 
{
	declare precedence : TransactionLogger, ErrorHandling, TransactionConstraints, TransactionSecurity, TransactionSynchronization;

} // AspectPrecedence
