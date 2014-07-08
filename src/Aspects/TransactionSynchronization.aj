package Aspects;

import java.util.ArrayList;
import org.aspectj.lang.annotation.AdviceName;
import Controllers.AccountsController;
import Global.Result;
import Model.BankAccounts.BankAccount;
import Model.Transactions.Transaction;

public aspect TransactionSynchronization extends Transactions {
    
	private static boolean outputOn = false; 
	
	@AdviceName("TransactionSynchronisation")
    Result around(Transaction transaction): critical_transactions(transaction) {
    	try {   	
	    	ArrayList<String> affectingAccounts = transaction.getAffectingAccNumbers();
	    	boolean allLocked = true;
	    	BankAccount account;
	    	int numOfLockedAccounts = 0;
	    	String accountNum;
	    	AccountsController accountController = AccountsController.getInstance();
	    	for ( int i = 0; i < affectingAccounts.size(); i++ ) {
	    		accountNum = affectingAccounts.get(i);
	    		
	    		account = accountController.getAccount(accountNum);
	    		
	    		output("Attempting to get Lock on Account: " + accountNum);
	    		if  ( !account.lock().tryLock() ) {
	    			allLocked = false; 			
	    			break;
	    		} // if
	    		
	    		output("\tLock on Account: " + accountNum);
	    		numOfLockedAccounts++;
	    	} // for
	    	   	
	    	if (allLocked) {
	    	    // Got the lock
	    	    try { 	    	
	    	    	return proceed(transaction);
	    	    } // try
	    	    finally {
	    	    	for ( String accountNumber :  affectingAccounts) {
	    	    		account = accountController.getAccount(accountNumber);
	    	    		account.lock().unlock();
	    	    		output("Released Lock on Account: " + accountNumber);
	    	    	} // for
	    	    	output("\n");
	    	    } // finally
	    	} // if
	    	else {
	    		for ( int i = 0; i < numOfLockedAccounts; i++ )	{
	        		accountNum = affectingAccounts.get(i);
	        		account = accountController.getAccount(accountNum);
	        		account.lock().unlock();
	        		output("Released Lock on Account: " + accountNum);
	        	} // for
	    		output("\n");
	    		String info = "Another transaction on account " + affectingAccounts.get(numOfLockedAccounts) 
	    						+ " is in process! Try again later!";
	    	    return new Result("FAILED", info);
	    	} //else
    	} catch (NullPointerException exc) {
    		return new Result("FAILED", "Account not found!");
    	} 
    } // around bank_operations
    
    private void output (String msg) {
    	if ( outputOn )
    		System.out.println(msg);
    }
} // TransactionSynchronization
