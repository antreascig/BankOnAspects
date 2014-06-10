package Controllers;

import java.util.Enumeration;
import javax.swing.DefaultListModel;
import Interface.AccountView;

public class BankDemoController 
{
	public static void viewAccount(String accNum) {
		int balance = AccountController.getAccountBalance(accNum);
		
		AccountView newAccountView = new AccountView(accNum, balance);
		
		newAccountView.setVisible(true);
	} // viewAccount

	public static void removeAccount(String accNum) 
	{
		AccountController.removeAccount(accNum);
	} // removeAccount

	public static void addAccount(String accNum, Integer initAmount) 
	{
		AccountController.addAccount(accNum, initAmount);
	} // addAccount
	
	public static DefaultListModel<String> getAccountList() {
		
		Enumeration<String> accNumbers = AccountController.getAccountNumbersList();
		
		DefaultListModel<String> accList = new DefaultListModel<>();
				
		while ( accNumbers.hasMoreElements() )
			accList.addElement(accNumbers.nextElement());
		
		return accList;
	} // getAccountList
} // BankDemoController
