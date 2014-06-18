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

	public static void removeAccount(String accNum) 
	{
		if ( accountList == null || accountList.isEmpty())
		{
			throw new NullPointerException("The account list is either unitialized or empty");
		} // if
		accountList.remove(accNum);
	} // removeAccount
	
	public static void addAccount(String accNum, Integer initAmount) 
	{
		if ( accountList == null )
		{
			initAccList();
		} // if
		BankAccount newAccount;
		
		if ( initAmount == null)
			newAccount	 = new BasicAccount(accNum, "password");
		else
			 newAccount = new BasicAccount(accNum, "password", initAmount);
		
		accountList.put(accNum, newAccount);
	} // addAccount

	public static BankAccount getAccount(String accNum) {
		if ( accountList == null || accountList.isEmpty())
		{
			throw new NullPointerException("The account list is either unitialized or empty");
		} // if		return ;
		
		try
		{
			BankAccount account = accountList.get(accNum);
			return account;
		} // try		
		catch( NullPointerException exc)
		{
			throw new AccountOperationException("Account not found!");
		} // catch		
	} // getAccount
} // AccountController
