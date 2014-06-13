package Controllers;

import java.util.ArrayList;
import java.util.Enumeration;

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
	
	public static ArrayList<String> getAccountList() {
		
		Enumeration<String> accNumbers = AccountController.getAccountNumbersList();
		
		ArrayList<String> accList = new ArrayList<>();
		
		while (accNumbers.hasMoreElements())
		{
			accList.add(accNumbers.nextElement());
		}	
		return accList;
	} // getAccountList
	
	public static String getAccountType(String accNum)
	{
		return AccountController.getAccountType(accNum);
	} // getAccountType

	public static void initializeServer() {
		// TODO Auto-generated method stub
		
	}
	
} // BankDemoController
