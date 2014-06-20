package Controllers;

import java.util.ArrayList;
import java.util.Enumeration;

import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;
import Server.Server;
import Services.Logging.Logger;

public class BankDemoController 
{	
	public BankDemoController()
	{
	}
	
	public void viewAccount(String accNumber) {
		try
		{
			AccountViewController newAccountViewCtrl = new AccountViewController(accNumber);
			newAccountViewCtrl.viewAccount();
		} catch (AccountOperationException exception)
		{
			
		}
	} // viewAccount

	public void removeAccount(String accNumber) 
	{
		AccountsController.removeAccount(accNumber);
	} // removeAccount

	public void addAccount(String accNum, Integer initAmount) 
	{
		AccountsController.addAccount(accNum, initAmount);
	} // addAccount
	
	public ArrayList<String> getAccountList() {
		
		Enumeration<String> accNumbers = AccountsController.getAccountNumbersList();
		
		ArrayList<String> accList = new ArrayList<>();
		
		while (accNumbers.hasMoreElements())
		{
			accList.add(accNumbers.nextElement());
		}	
		return accList;
	} // getAccountList
	
	public String getAccountType(String accNumber)
	{
		BankAccount account = AccountsController.getAccount(accNumber);
		return account.getAccountType();
	} // getAccountType

	public int getClientNumber() {

		Server server = Server.getServerInstance();
		return server.getClientCount();
	} // serverRunning
	
	public void stopApplication()
	{
		Server server = Server.getServerInstance();
		server.stopServer();
	} // stopApplication

	public void logServerActivity(String message) {
		Logger.logServerActivity(message);
		
	}
	
} // BankDemoController
