package Controllers;

import java.util.Enumeration;
import java.util.Hashtable;

import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;
import Services.Persistence.Persistent;

public class AccountsController {
	private Hashtable<String, BankAccount> accountList = null;
	
	private static AccountsController instance = null;
	
	private AccountsController() {
		accountList = Persistent.loadAccounts();
	} // AccountsController
	
	public static AccountsController getInstance() {
		if ( instance == null )
			instance = new AccountsController();
		return instance;
	} // getInstance
		
	public Enumeration<String> getAccountNumbersList() {	
		return accountList.keys();		
	} // getAccountList

	public synchronized void removeAccount(String accNumber) {
		accountList.remove(accNumber);
	} // removeAccount
	
	public String addAccount(BankAccount newAccount) {
		if (accountList.containsKey(newAccount.getAccNum()))
			throw new AccountOperationException("The account key: " + newAccount.getAccNum() + " already exists.");
		accountList.put(newAccount.getAccNum(), newAccount);
		
		return newAccount.getAccNum();
	} // addAccount

	public BankAccount getAccount(String accNumber) {	
		try {
			BankAccount account = accountList.get(accNumber);
			return account;
		} // try		
		catch( NullPointerException exc) {
			throw new AccountOperationException("Account not found!");
		} // catch		
	} // getAccount
	
	public void saveAccounts() {
		Persistent.saveAccounts(accountList);
	} // saveAccounts
	
	public void clearAccounts() {
		accountList.clear();
	} // clearAccounts
	
} // AccountController
