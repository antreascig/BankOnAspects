package Test_Suite;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Controllers.AccountsController;
import Global.UserMode;
import Model.BankAccounts.BasicAccount;
import Model.Transactions.BalanceTransaction;
import Model.Transactions.DepositTransaction;
import Model.Transactions.Transaction;
import Model.Transactions.WithdrawTransaction;
import Services.Security.SecurityHandler;

public class Test_SecurityHandler {
		
	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test_Security_Pointcut()
	{
		AccountsController.addAccount(new BasicAccount("acc1", 1234));
		Transaction tr;
		
		// Test BalanceTransaction Pointcut
		tr = new BalanceTransaction(UserMode.CLIENT, 1234, "acc1" );
		
		assertEquals(false, SecurityHandler.transactionEvaluated());
		
		tr.executeTransaction();
		assertEquals(true, SecurityHandler.transactionEvaluated());
		
		SecurityHandler.resetEvaluation();
		assertEquals(false, SecurityHandler.transactionEvaluated());
		
		// Test DepositTransaction Pointcut
		tr = new DepositTransaction(0, UserMode.CLIENT, 1234, "acc1", 10 );
		
		assertEquals(false, SecurityHandler.transactionEvaluated());
		
		tr.executeTransaction();
		assertEquals(true, SecurityHandler.transactionEvaluated());
		
		SecurityHandler.resetEvaluation();
		assertEquals(false, SecurityHandler.transactionEvaluated());
		
		// Test WithdrawTransaction Pointcut
		tr = new WithdrawTransaction(1, UserMode.CLIENT, 1234, "acc1", 10 );
		
		assertEquals(false, SecurityHandler.transactionEvaluated());
		
		tr.executeTransaction();
		assertEquals(true, SecurityHandler.transactionEvaluated());
		
		SecurityHandler.resetEvaluation();
		assertEquals(false, SecurityHandler.transactionEvaluated());
		
	} // test_IsAuntenticated
	
	@Test
	public void test_Security_Advice()
	{
		AccountsController.addAccount(new BasicAccount("acc1", 1234));
		AccountsController.addAccount(new BasicAccount("acc2", 4567));
		
//		Transaction tr = new 	
		
	}
	
	

} // Test_SecurityHandler
