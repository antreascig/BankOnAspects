package Controllers;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;

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
		accountList.remove(accNum);
	} // removeAccount
	
	public static void addAccount(String accNum, Integer initAmount) 
	{
		BankAccount newAccount;
		
		if ( initAmount == null)
			newAccount	 = new BasicAccount(accNum);
		else
			 newAccount = new BasicAccount(accNum, initAmount);
		
		accountList.put(accNum, newAccount);
	} // addAccount

	public static int getAccountBalance(String accNum) {
		BankAccount account = accountList.get(accNum);

		return account.getBalance();
	} // getAccountBalance

	public static void depositInAccount(String accNum, int amount) 
	{
		BankAccount account = accountList.get(accNum);
		try
		{
			account.deposit(amount);
		}
		catch (AccountOperationException exception)
		{
			JOptionPane.showMessageDialog(null,
					exception.getMessage(),
				    "Account Operation Error",
				    JOptionPane.WARNING_MESSAGE);
		}
	} // depositInAccount

	public static void withdrawFromAccount(String accNum, int amount) 
	{
		BankAccount account = accountList.get(accNum);
		
		try
		{
			account.withdraw(amount);
		}
		catch (AccountOperationException exception)
		{
			JOptionPane.showMessageDialog(null,
					exception.getMessage(),
				    "Account Operation Error",
				    JOptionPane.WARNING_MESSAGE);
		}
	} // withdrawFromAccount

}
