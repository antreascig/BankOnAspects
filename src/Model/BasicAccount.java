package Model;

public class BasicAccount implements BankAccount {
	private int balance;
	private String accNum;
	private String password;
	
	public BasicAccount(String accountNum, String pass)
	{
		this.accNum = accountNum;
		this.balance = 0;
	} // BasicAccount
	
	public BasicAccount(String accountNum, String pass, int initBalance)
	{
		this.accNum = accountNum;
		this.password = pass;
		
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
	public boolean evaluateCredentials(String pass) 
	{
		if ( this.password == pass )
			return true;
		else
			return false;
	}

	@Override
	public String getAccountType() {
		return "Basic Account";
	}
} // BasicAccount
