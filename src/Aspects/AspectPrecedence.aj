package Aspects;

public aspect AspectPrecedence 
{
	declare precedence : TransactionLogger, TransactionConstraints, TransactionSecurity, TransactionSynchronization;

} // AspectPrecedence
