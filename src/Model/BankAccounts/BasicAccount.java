package Model.BankAccounts;

import java.util.Hashtable;

import Global.AccountType;
import Global.TransactionType;

public class BasicAccount extends BankAccount {
	
	public BasicAccount(String accountNum, Integer pass, Hashtable<TransactionType, Boolean> operations)
	{
		super(accountNum, pass, operations);	
	} // BasicAccount
	
//	public BasicAccount(String accountNum, Integer pass, int initBalance)
//	{
//		super(accountNum, pass, initBalance);
//		
//		setTransferAllowed(false);
//	} // BasicAccount

	@Override
	public AccountType getAccountType() {
		return AccountType.BASIC_ACCOUNT;
	} // getAccountType
	
} // BasicAccount
