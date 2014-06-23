package Controllers;

import java.util.ArrayList;
import java.util.Enumeration;

import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;
import Model.BankAccounts.BasicAccount;
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

	private int counter = 1;
	
	public void addAccount() 
	{
		String accNum = "acc" + counter;
		counter++;
		
		BankAccount newAccount = new BasicAccount(accNum, 1234, 0);		
		
		AccountsController.addAccount(newAccount);
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
		Logger.logServerActivity("Server Stopped.");
		Server server = Server.getServerInstance();
		server.stopServer();
	} // stopApplication

	public void logServerActivity(String message) {
		Logger.logServerActivity(message);
		
	}
	
} // BankDemoController
