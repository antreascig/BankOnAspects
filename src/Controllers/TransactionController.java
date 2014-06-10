package Controllers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TransactionController{
	
	public static void deposit(JFrame parent, String acc)
	{	
		String amountString = (String) JOptionPane.showInputDialog(
				parent,
                "Enter amount to deposit:",
                "Deposit",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                0);		
		
		while ( !isInteger(amountString) )
		{
			if (amountString == null)
				return;
			
			amountString = (String) JOptionPane.showInputDialog(
					parent,
	                "Amount Must be An integer Number!"
	                + "\nEnter amount to deposit:",
	                "Deposit",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                0);
		} // while	
		
		int amount = Integer.parseInt(amountString);
		
		// Call deposit on account
		AccountController.depositInAccount(acc, amount);
		
	} // deposit
	
	public static void withdraw(JFrame parent, String acc)
	{	
		String amountString = (String) JOptionPane.showInputDialog(
				parent,
                "Enter amount to withdraw:",
                "Withdraw",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                0);		
		
		while ( !isInteger(amountString) )
		{
			if (amountString == null)
				return;
			
			amountString = (String) JOptionPane.showInputDialog(
					parent,
	                "Amount Must be An integer Number!"
	                + "\nEnter amount to withdraw:",
	                "Withdrawal",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                0);
		} // while	
		
		int amount = Integer.parseInt(amountString);
		
		// Call withdrawal on account
		AccountController.withdrawFromAccount(acc, amount);
		
	} // deposit
	
	
	private static boolean isInteger(String s) {
	    try 
	    { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) 
	    { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	} // isInteger

	public static int getBalance(String accNumber) 
	{
		return AccountController.getAccountBalance(accNumber);
	}
}
