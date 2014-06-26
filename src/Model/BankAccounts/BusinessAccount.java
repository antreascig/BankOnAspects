package Model.BankAccounts;

import java.util.Hashtable;

import Global.AccountType;
import Global.TransactionType;

public class BusinessAccount extends BankAccount {
	
	private static final long serialVersionUID = 1L;

	public BusinessAccount(String accountNum, Integer pass, Hashtable<TransactionType, Boolean> operations) {
		super(accountNum, pass, operations);
	} // BusinessAccount

	@Override
	public AccountType getAccountType() {
		// TODO Auto-generated method stub
		return AccountType.BUSINESS_ACCOUNT;
	} //getAccountType
} // BusinessAccount
