package Aspects;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Model.AccountOperationException;
import Model.BankAccounts.BankAccount;

public aspect TransactionSynchronization extends Transactions {
    
//	private static Object synchLock = new Object();
	
	private Lock lock = new ReentrantLock();
   
    before(BankAccount account, int amount): bank_operations(account, amount) 
    {
    	System.out.println();
    	System.out.println("Attempting to get Lock on Account: " + account.getAccNum());
    } // before bank_operations
    
//    Object around(BankAccount account, int amount): bank_operations(account, amount)
//    {
//        synchronized(synchLock) 
//        {
//        	System.out.println("\tLock on Account: " + account.getAccNum());
//            return proceed(account, amount);
//        } // Synchronised
//    } // around bank_operations
    
    
    Object around(BankAccount account, int amount): bank_operations(account, amount)
    {
    	if (lock.tryLock()) {
    	    // Got the lock
    	    try {
    	    	System.out.println("\tLock on Account: " + account.getAccNum());
    	    	return proceed(account, amount);
    	    } // try
    	    finally {
    	        lock.unlock();
    	    } // finally
    	} // if
    	else {
    	    throw new AccountOperationException("Another transaction on the account is in process! Try again later!");
    	} //else
    } // around bank_operations
    
    after(BankAccount account, int amount): bank_operations(account, amount)
    {
    	System.out.println("Released Lock on Account: " + account.getAccNum());
    	System.out.println();
    } // before bank_operations
 
}
