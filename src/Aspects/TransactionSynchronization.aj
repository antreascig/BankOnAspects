package Aspects;

import java.util.ArrayList;

import org.aspectj.lang.annotation.AdviceName;

import Global.Result;
import Model.Transactions.Transaction;
import Services.Synchronization.AccountLocker;

public aspect TransactionSynchronization extends Transactions {
    
	@AdviceName("TransactionSynchronisation")
    Result around(Transaction transaction): critical_transactions(transaction) { 
    	Result result = null;
	    ArrayList<String> affectingAccounts = transaction.getAffectingAccNumbers();
	    AccountLocker locker = new AccountLocker(affectingAccounts);
	    
	    if ( locker.lockAccounts() )    
	    	result  = proceed(transaction);
	    else
	    	result = new Result("FAILED", locker.getError());
	    
	    locker.unlockAccounts();
	    
	    return result;
    } // around bank_operations
    
    
} // TransactionSynchronization
