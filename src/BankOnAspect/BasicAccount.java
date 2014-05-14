package BankOnAspect;

public class BasicAccount implements BankAccount {
	private int balance;
	private int accNum;
	
	public BasicAccount(int accountNum)
	{
		this.accNum = accountNum;
		balance = 0;
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
	public int getAccNum() {
		return this.accNum;
	} // getAccNum
} // BasicAccount
