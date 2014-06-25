package Aspects;

import java.util.ArrayList;

import Controllers.AccountsController;
import Model.BankAccounts.BankAccount;
import Model.Exceptions.AccountOperationException;
import Model.Transactions.Transaction;

public aspect TransactionSynchronization extends Transactions {
    
    Object around(Transaction transaction): critical_transactions(transaction)
    {
    	ArrayList<String> affectingAccounts = transaction.getAffectingAccNumbers();
    	boolean allLocked = true;
    	BankAccount account;
    	int numOfLockedAccounts = 0;
    	String accountNum;
    	
    	for ( int i = 0; i < affectingAccounts.size(); i++ ) {
    		accountNum = affectingAccounts.get(i);
    		account = AccountsController.getAccount(accountNum);
    		
    		System.out.println("Attempting to get Lock on Account: " + accountNum);
    		if  ( !account.lock().tryLock() ) {
    			allLocked = false; 			
    			break;
    		} // if
    		
    		System.out.println("\tLock on Account: " + accountNum);
    		numOfLockedAccounts++;
    	} // for
    	   	
    	if (allLocked) {
    	    // Got the lock
    	    try { 	    	
    	    	return proceed(transaction);
    	    } // try
    	    finally {
    	    	for ( String accountNumber :  affectingAccounts) {
    	    		account = AccountsController.getAccount(accountNumber);
    	    		account.lock().unlock();
    	    		System.out.println("Released Lock on Account: " + accountNumber);
    	    	} // for
    	    	System.out.println();
    	    } // finally
    	} // if
    	else {
    		for ( int i = 0; i < numOfLockedAccounts; i++ )	{
        		accountNum = affectingAccounts.get(i);
        		account = AccountsController.getAccount(accountNum);
        		account.lock().unlock();
        		System.out.println("Released Lock on Account: " + accountNum);
        	} // for
    		System.out.println();
    	    throw new AccountOperationException("Another transaction on account " + affectingAccounts.get(numOfLockedAccounts) + " is in process! Try again later!");
    	} //else
    } // around bank_operations

}
