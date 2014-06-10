package Aspects;

public aspect TransactionSynchronization extends OperationHandling {
    private static final Object lock = new Object();
    
	//////////////////////////////////////////////////////////////////////////////////
	/////// Deposit 
	//////////////////////////////////////////////////////////////////////////////////
    
    before(Model.BankAccount account, int amount): deposit(account, amount) 
    {
    	System.out.println("\tAttempting to get Lock on Account: " + account.getAccNum());
    } // before deposit
    
    Object around(Model.BankAccount account, int amount): deposit(account, amount) 
    {
        synchronized(lock) 
        {
        	System.out.println("\tLock on Account: " + account.getAccNum());
            return proceed(account, amount);
        } // Synchronised
    } // around deposit
    
    after(Model.BankAccount account, int amount): deposit(account, amount)
    {
    	System.out.println("\tReleased Lock on Account: " + account.getAccNum());
    } // before deposit
    
    //////////////////////////////////////////////////////////////////////////////////
    /////// Withdrawal 
    //////////////////////////////////////////////////////////////////////////////////
    
    before(Model.BankAccount account, int amount): withdraw(account, amount) 
    {
    	System.out.println("\tAttempting to get Lock on Account: " + account.getAccNum());
    } // before deposit
    
    Object around(Model.BankAccount account, int amount): withdraw(account, amount) 
    {
        synchronized(lock) 
        {
        	System.out.println("\tLock on Account: " + account.getAccNum());
            return proceed(account, amount);
        } // Synchronised
    } // around deposit
    
    after(Model.BankAccount account, int amount): withdraw(account, amount)
    {
    	System.out.println("\tReleased Lock on Account: " + account.getAccNum());
    } // before deposit
    
}
