package Controllers;

import java.util.Enumeration;
import java.util.Hashtable;

import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;

public class AccountsController 
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
	
	public static void addAccount(BankAccount newAccount) 
	{
		if ( accountList == null )
		{
			initAccList();
		} // if
		
		accountList.put(newAccount.getAccNum(), newAccount);
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
