package Controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.DefaultComboBoxModel;

import Global.AccountType;
import Global.Pair;
import Global.Result;
import Global.UserMode;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;
import Server.Server;
import Services.Logging.Logger;
import Services.Persistence.Persistent;

public class BankDemoController 
{	
	private AccountsController accountController;
	private Server server;
	private TransactionController trController;
	private AccountViewController accountViewCtrl;
	
	public BankDemoController() {
		accountController = AccountsController.getInstance();
		server = Server.getServerInstance();
		trController = new TransactionController(UserMode.ADMIN);
	} // BankDemoController
	
	public void viewAccount(String accNumber) {
		accountViewCtrl = new AccountViewController(accNumber);
		accountViewCtrl.viewAccount();
	} // viewAccount

	public void removeAccount(String accNumber) {
		accountController.removeAccount(accNumber);
	} // removeAccount
	
	public Pair<Integer> addAccount(int option) {
		
		AccountType type;
		switch(option){
			case 0: type = AccountType.BASIC_ACCOUNT;
					break;
			case 1: type = AccountType.BUSINESS_ACCOUNT;
					break;
			default:
					return null;
		} // switch
		
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		Pair<Integer> newAccountInfo = null;
		try {
			String newAccNumber = accountController.addAccount(accFactory.createAccount(type, 1234));
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
		Collections.sort(accList);
		return accList;
	} // getAccountList
	
	public DefaultComboBoxModel<String> getAccountComboList() {
		ArrayList<String> accArrayList = getAccountList();
		
		DefaultComboBoxModel<String> accList = new DefaultComboBoxModel<>();
		for (String accNum :accArrayList) {
			accList.addElement(accNum);
		} // whileW
		
		return accList;
	} // getAccountComboList
	
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

	public Integer getBalance(String accNum) {
		Result result = trController.getBalance(accNum);
		if (result.getStatus().equals("COMPLETED") )
			return Integer.parseInt(result.getInfo());
		else
			return null;
		
	} // getBalance

	public Boolean transfer(String fromAccNumber, String toAccNumber, int amount) {
		Result result = trController.transfer(fromAccNumber, toAccNumber, amount);
		if (result.getStatus().equals("COMPLETED") )
			return true;
		else
			return false;
	}
	
} // BankDemoController
