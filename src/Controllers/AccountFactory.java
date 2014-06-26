package Controllers;

import java.util.Hashtable;

import Global.AccountNumberGenerator;
import Global.AccountType;
import Global.TransactionType;
import Model.BankAccounts.BankAccount;
import Model.BankAccounts.BasicAccount;
import Model.BankAccounts.BusinessAccount;

public class AccountFactory {
	
	private AccountFactory() {
	}
	
	private static AccountFactory instance = null;
	
	public static AccountFactory getAccountFactoryInstance() {
		if ( instance == null ) {
			instance = new AccountFactory();
		} //if
		return instance;
	} // getAccountFactoryInstance
	
	// Create Bank Account with default operations to its type
	public BankAccount createAccount(AccountType accountType, int pass) {
		return createAccount(accountType, pass, getDefaultOperations(accountType));
	} // createAccount
	
	public BankAccount createAccount(AccountType accountType, int pass, Hashtable<TransactionType, Boolean> operations ) {
		BankAccount account = null;
		String accountNumber = getAccountNumber();

		switch (accountType) {
			case BASIC_ACCOUNT: 
								account = new BasicAccount(accountNumber, pass, operations);
								break;
			case BUSINESS_ACCOUNT:
								account = new BusinessAccount(accountNumber, pass, operations);
								break;
		default:
			return null;
		} // switch		
		return account;
	} // createAccount
	
	private String getAccountNumber() {
		AccountNumberGenerator accCounter = AccountNumberGenerator.getInstance();
		int counter = accCounter.getAndIncreaseNumber();
		
		return "acc" + counter;
	} // getAccountNumber

	private Hashtable<TransactionType, Boolean> getDefaultOperations( AccountType accountType) {
		Hashtable<TransactionType, Boolean> operations = new Hashtable<>();
		
		switch (accountType) {
			case BASIC_ACCOUNT: 
								operations.put(TransactionType.BALANCE, true);
								operations.put(TransactionType.DEPOSIT, true);
								operations.put(TransactionType.WITHDRAW, true);
								operations.put(TransactionType.TRANSFER, false);
								break;
			case BUSINESS_ACCOUNT:
								operations.put(TransactionType.BALANCE, true);
								operations.put(TransactionType.DEPOSIT, true);
								operations.put(TransactionType.WITHDRAW, true);
								operations.put(TransactionType.TRANSFER, true);
								break;
			default:
					return null;
		} // switch
		
		return operations;
	} // getDefaultOperations

}
