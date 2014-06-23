package Test_Suite;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Global.UserMode;
import Model.Transactions.BalanceTransaction;
import Model.Transactions.Transaction;
import Services.Security.SecurityHandler;

public class Test_SecurityHandler {
		
	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test_Security_Pointcut()
	{
		Transaction tr = new BalanceTransaction(UserMode.CLIENT, 1234, "acc1" );
		
		assertEquals(false, SecurityHandler.transactionEvaluated());
		
		SecurityHandler.evaluateTransaction(tr);
		assertEquals(true, SecurityHandler.transactionEvaluated());
//		
//		SecurityHandler.resetEvaluation();
//		assertEquals(false, SecurityHandler.transactionEvaluated());
		
	} // test_IsAuntenticated

} // Test_SecurityHandler
