package Test_Suite;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Controllers.AccountFactory;
import Controllers.AccountsController;
import Global.AccountType;
import Global.UserMode;
import Model.Transactions.*;
import Services.Security.SecurityHandler;

public class Test_SecurityHandler {
		
	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test_Security_Pointcut()
	{
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	

		AccountsController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, "acc1", 1234));
		
		AccountsController.addAccount(accFactory.createAccount(AccountType.BUSINESS_ACCOUNT, "acc2", 5678));
		
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
		
		
		
		// Test TransferTransaction Pointcut
		tr = new TransferTransaction(1, UserMode.ADMIN, null, "acc1", "acc2", 10 );
		
		assertEquals(false, SecurityHandler.transactionEvaluated());
		
		tr.executeTransaction();  // The transaction fails but we do not care. We only test the pointcut 
		assertEquals(true, SecurityHandler.transactionEvaluated()); 
		
		SecurityHandler.resetEvaluation();
		assertEquals(false, SecurityHandler.transactionEvaluated());
		
	} // test_IsAuntenticated
	
	@Test
	public void test_Security_Advice()
	{
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	

		AccountsController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, "acc1", 1234));
		
		AccountsController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, "acc2", 4567));
		
		
		
	}
	
	

} // Test_SecurityHandler
