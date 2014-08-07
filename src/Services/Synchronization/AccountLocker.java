package Services.Synchronization;

import java.util.ArrayList;

import Controllers.AccountsController;
import Model.BankAccounts.BankAccount;

public class AccountLocker implements Runnable{
	private int numOfLockedAccounts;
	private ArrayList<String> accounts;
	private AccountsController accountController;
	private String error;
	private boolean lockCalled;
	
	private boolean outputOn = false; 
	
	public AccountLocker (ArrayList<String> accs) {
		numOfLockedAccounts = 0;
		accounts = accs;
		accountController = AccountsController.getInstance();
		error = "";
		lockCalled = false;
	} // AccountLocker
	
	public boolean lockAccounts()  {
		lockCalled = false;
		
    	BankAccount account;
    	numOfLockedAccounts = 0;
    	String accountNum;
    	for ( int i = 0; i < accounts.size(); i++ ) {
    		accountNum = accounts.get(i);
    		
    		account = accountController.getAccount(accountNum);
    		
    		output("Attempting to get Lock on Account: " + accountNum);
    		if  ( !account.lock().tryLock() ) {
    			numOfLockedAccounts = i;
    			error = "Another transaction on account " + accountNum
    					+ " is in process! Try again later!";
    			output(error);
    			return false;
    		} // if
    		
    		output("\tLock on Account: " + accountNum);
    		numOfLockedAccounts++;
    	} // for
       	error = "No Errors";
       	lockCalled = true;
		return true;
	} // lockAccounts
	
	public void unlockAccounts() {
		String accountNum;
		BankAccount account;
    		for ( int i = 0; i < numOfLockedAccounts; i++ )	{
        		accountNum = accounts.get(i);
        		account = accountController.getAccount(accountNum);
        		account.lock().unlock();
        		output("Released Lock on Account: " + accountNum);
        	} // for
	} // unLockAccounts	
	
	public String getError()  {
		try {
			while (!lockCalled){		
				Thread.sleep(100);
				return error;
			} // while
		} catch (InterruptedException e) {
			return e.getMessage();
		}
		return error;		
	} // getError
	
	private void output(String msg) {
    	if ( outputOn )
    		System.out.println(msg);
    } // output

	@Override
	public void run() {
		lockAccounts();
	} // run
} // AccountLocker
