package Controllers;

import Global.AccountType;
import Model.BankAccounts.BankAccount;
import Model.BankAccounts.BasicAccount;
import Model.BankAccounts.BusinessAccount;

public class AccountFactory {
	
	private AccountFactory() {
	}
	
	private static AccountFactory instance = null;
	
	public static AccountFactory getAccountFactoryInstance()
	{
		if ( instance == null ) {
			instance = new AccountFactory();
		} //if
		
		return instance;
	} // getAccountFactoryInstance
	
	public BankAccount createAccount(AccountType accountType, String accountNumber, int pass, int initBalance )
	{
		BankAccount account = null;
		switch (accountType) {
			case BASIC_ACCOUNT: 
								account = new BasicAccount(accountNumber, pass, initBalance);
								break;
			case BUSINESS_ACCOUNT:
								account = new BusinessAccount(accountNumber, pass, initBalance);
								break;
		default:
			return null;
		} // switch
		
		return account;
	}

}
