package Model.BankAccounts;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.concurrent.locks.ReentrantLock;

import Global.AccountType;
import Global.TransactionType;;

public abstract class BankAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	protected int balance;
	protected String accNum;
	protected Integer credential;
	private final ReentrantLock lock = new ReentrantLock();
	
	private Hashtable<TransactionType, Boolean> operations;

	public BankAccount(String accountNum, Integer pass, Hashtable<TransactionType, Boolean> ops) {
		this.accNum = accountNum;
		credential = pass;
		balance = 0;
		operations = ops;
	} // BankAccount

	public void deposit(int amount) {
		try {
			Thread.sleep(500);
			balance += amount;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
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
	
	public ReentrantLock lock() {
		return this.lock;
	} // lock
	
	public boolean getOperationAvailable(TransactionType operation) {
		return operations.get(operation);
	} // getOperationAvailable
	
	public void changeOperationAvailability(TransactionType operation, Boolean value) {
		operations.put(operation, value);
	} // getOperationAvailable
} // BankAccount