package Model.BankAccounts;

import Model.AccountOperationException;

public class BasicAccount implements BankAccount {
	private int balance;
	private String accNum;
	private Integer credential;
	
	public BasicAccount(String accountNum, Integer pass)
	{
		this.accNum = accountNum;
		credential = pass;
		this.balance = 0;
	} // BasicAccount
	
	public BasicAccount(String accountNum, Integer pass, int initBalance)
	{
		this.accNum = accountNum;
		this.credential = pass;
		
		if ( initBalance >= 0 )
			this.balance = initBalance;
		else 
			throw new AccountOperationException("Initial Balance should be equal or greater than 0");
	} // BasicAccount
	
	@Override
	public void deposit(int amount) {
		balance += amount;
	} // deposit

	@Override
	public void withdraw(int amount) {
		balance -= amount;
	} // withdraw

	@Override
	public int getBalance() {
		return this.balance;
	} // getBalance

	@Override
	public String getAccNum() {
		return this.accNum;
	} // getAccNum

	@Override
	public boolean evaluateCredentials(Integer password) 
	{
		if ( this.credential == password )
			return true;
		else
			return false;
	}

	@Override
	public String getAccountType() {
		return "Basic Account";
	}
} // BasicAccount
