package Controllers;

import java.util.ArrayList;
import java.util.Enumeration;

import Global.AccountType;
import Global.Pair;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;
import Server.Server;
import Services.Logging.Logger;
import Services.Persistence.Persistent;

public class BankDemoController 
{	
	private AccountsController accountController;
	private Server server;
	
	public BankDemoController() {
		accountController = AccountsController.getInstance();
		server = Server.getServerInstance();
	}
	
	public void viewAccount(String accNumber) {
		AccountViewController newAccountViewCtrl = new AccountViewController(accNumber);
		newAccountViewCtrl.viewAccount();
	} // viewAccount

	public void removeAccount(String accNumber) {
		accountController.removeAccount(accNumber);
	} // removeAccount
	
	public Pair<Integer> addAccount() {		
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		Pair<Integer> newAccountInfo = null;
		try {
			String newAccNumber = accountController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, 1234));
			newAccountInfo = new Pair<Integer>(newAccNumber, 1234);
			return newAccountInfo;
			
		} catch ( AccountOperationException exception) {
			newAccountInfo = new Pair<Integer>(exception.getMessage(), null);
			return newAccountInfo;
		} // catch		
	} // addAccount
	
	public ArrayList<String> getAccountList() {
		
		Enumeration<String> accNumbers = accountController.getAccountNumbersList();
		
		ArrayList<String> accList = new ArrayList<>();
		
		while (accNumbers.hasMoreElements()) {
			accList.add(accNumbers.nextElement());
		} // while
		return accList;
	} // getAccountList
	
	public String getAccountType(String accNumber) {
		BankAccount account = accountController.getAccount(accNumber);
		return account.getAccountType().toString();
	} // getAccountType

	public int getClientNumber() {
		return server.getClientCount();
	} // serverRunning
	
	public void stopApplication() {
		Logger.logServerActivity("Server Stopped.");
		server.stopServer();
		Persistent.saveCounters();
		accountController.saveAccounts();
	} // stopApplication

	public void logServerActivity(String message) {
		Logger.logServerActivity(message);
	} // logServerActivity

	public void runServer() {
		server.runServer();
	} // runServer
	
} // BankDemoController
