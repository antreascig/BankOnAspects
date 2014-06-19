package Controllers;

import java.util.Enumeration;
import java.util.Hashtable;

import Model.AccountOperationException;
import Model.BankAccount;
import Model.BasicAccount;

public class AccountController 
{
	private static Hashtable<String, BankAccount> accountList = null;
		
		public static void initAccList()
		{
			accountList = new Hashtable<>();
			
			// Restore from file if any
		} // initAccList
		
	public static Enumeration<String> getAccountNumbersList() 
	{	
		if ( accountList == null )
		{
			initAccList();
		} // if
		return accountList.keys();		
	} // getAccountList

	public static void removeAccount(String accNumber) 
	{
		if ( accountList == null || accountList.isEmpty())
		{
			throw new NullPointerException("The account list is either unitialized or empty");
		} // if
		accountList.remove(accNumber);
	} // removeAccount
	
	public static void addAccount(String accNumber, Integer initAmount) 
	{
		if ( accountList == null )
		{
			initAccList();
		} // if
		BankAccount newAccount;
		
		if ( initAmount == null)
			newAccount	 = new BasicAccount(accNumber, 1234);
		else
			 newAccount = new BasicAccount(accNumber, 1234, initAmount);
		
		accountList.put(accNumber, newAccount);
	} // addAccount

	public static BankAccount getAccount(String accNumber) {
		if ( accountList == null || accountList.isEmpty())
		{
			throw new NullPointerException("The account list is either unitialized or empty");
		} // if		return ;
		
		try
		{
			BankAccount account = accountList.get(accNumber);
			return account;
		} // try		
		catch( NullPointerException exc)
		{
			throw new AccountOperationException("Account not found!");
		} // catch		
	} // getAccount
} // AccountController
