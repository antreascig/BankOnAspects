package Model.BankAccounts;

import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Global.AccountType;
import Global.TransactionType;;

public abstract class BankAccount{

	protected int balance;
	protected String accNum;
	protected Integer credential;
	private final Lock lock = new ReentrantLock();
	
	private Hashtable<TransactionType, Boolean> operations;

	public BankAccount(String accountNum, Integer pass, Hashtable<TransactionType, Boolean> ops) {
		this.accNum = accountNum;
		credential = pass;
		balance = 0;
		operations = ops;
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
		
//		System.out.println("Account Password: " + credential + "\tClients Password: " + password);
				
		if ( this.credential.equals(password) )
			return true;
		else
			return false;
	} // evaluateCredentials

	public abstract AccountType getAccountType();
	
	public Lock lock() {
		return this.lock;
	} // lock
	
	public boolean getOperationAvailable(TransactionType operation) {
		return operations.get(operation);
	} // getOperationAvailable
	
	public void changeOperationAvailability(TransactionType operation, Boolean value) {
		operations.put(operation, value);
	} // getOperationAvailable
} // BankAccount