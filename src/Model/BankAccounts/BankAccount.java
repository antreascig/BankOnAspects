package Model.BankAccounts;

public abstract class BankAccount {

	protected int balance;
	protected String accNum;
	protected Integer credential;
	private boolean transferAllowed;

	public BankAccount(String accountNum, Integer pass) {
		this.accNum = accountNum;
		credential = pass;
		deposit(0);
	} // BankAccount
	
	public BankAccount(String accountNum, Integer pass, int initBalance)
	{
		this.accNum = accountNum;
		this.credential = pass;
		deposit(initBalance);
	} // BankAccount

	public void deposit(int amount) {
		balance += amount;
	} // deposit

	public void withdraw(int amount) {
		balance -= amount;
	} // withdraw

	public int getBalance() {
		return this.balance;
	} // getBalance

	public String getAccNum() {
		return this.accNum;
	} // getAccNum

	public boolean evaluateCredentials(Integer password) {
		if ( this.credential == password )
			return true;
		else
			return false;
	} // evaluateCredentials

	public abstract String getAccountType();

	public boolean transferTransactionAllowed() {
		return transferAllowed;
	} // transferTransactionAllowed

	public void setTransferAllowed(boolean value) {
		this.transferAllowed = value;		
	} // setTransferAllowed

}