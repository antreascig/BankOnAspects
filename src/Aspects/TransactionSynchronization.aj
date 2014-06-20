package Aspects;

import Model.BankAccount;

public aspect TransactionSynchronization extends Transactions {
    
	private static Object lock = new Object();
   
    before(BankAccount account, int amount): bank_operations(account, amount) 
    {
    	System.out.println();
    	System.out.println("Attempting to get Lock on Account: " + account.getAccNum());
    } // before bank_operations
    
    Object around(BankAccount account, int amount): bank_operations(account, amount)
    {
        synchronized(lock) 
        {
        	System.out.println("\tLock on Account: " + account.getAccNum());
            return proceed(account, amount);
        } // Synchronised
    } // around bank_operations
    
    after(BankAccount account, int amount): bank_operations(account, amount)
    {
    	System.out.println("Released Lock on Account: " + account.getAccNum());
    	System.out.println();
    } // before bank_operations
 
}
