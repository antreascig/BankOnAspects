package Test_Suite;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.AccountFactory;
import Controllers.AccountsController;
import Global.AccountType;
import Global.UserMode;
import Model.Transactions.DepositTransaction;
import Model.Transactions.Transaction;
import Model.Transactions.TransferTransaction;
import Model.Transactions.WithdrawTransaction;
import fr.irisa.triskell.advicetracer.AdviceTracer;
import fr.irisa.triskell.advicetracer.AdviceTracer.TraceElement;
import fr.irisa.triskell.advicetracer.junit.Assert;

public class Test_TransactionLogging {

	private static String basicAccNum1, busAccNum1;
	private static int basicAccPass1, busAccPass1;
	
	@BeforeClass
	public static void setUp() {
		TestSetUp.setUpTestingEnvironment("TransactionLogging");
		
		AccountFactory accFactory = AccountFactory.getAccountFactoryInstance();	
		AccountsController accountController = AccountsController.getInstance();
				
		basicAccPass1 = 1234;	
		busAccPass1 = 5678;
		
		basicAccNum1 = accountController.addAccount(accFactory.createAccount(AccountType.BASIC_ACCOUNT, basicAccPass1));
		busAccNum1 = accountController.addAccount(accFactory.createAccount(AccountType.BUSINESS_ACCOUNT, busAccPass1));
	} // setAccounts
	
	@Test
	public void test_Logging_Transaction_Failed_Operation() {
//		System.out.println("FAILED Operation");
		
		testing_Pointcuts(new DepositTransaction(1, UserMode.CLIENT, busAccPass1, busAccNum1, -100 ), "LogAfterOperationWithError");
		testing_Pointcuts(new WithdrawTransaction(2, UserMode.CLIENT, busAccPass1, busAccNum1, -10 ), "LogAfterOperationWithError");
		testing_Pointcuts(new TransferTransaction(3, UserMode.CLIENT, busAccPass1, busAccNum1, basicAccNum1, -10 ), "LogAfterOperationWithError");
	} // test_Logging_Transaction_No_Error
	
	@Test
	public void test_Logging_Transaction_Completed_Operation() {
//		System.out.println("------------------------");
//		System.out.println("COMPLETED Operation");
		
		testing_Pointcuts(new DepositTransaction(4, UserMode.CLIENT, busAccPass1, busAccNum1, 100 ), "LogAfterOperationNoError");
		testing_Pointcuts(new WithdrawTransaction(5, UserMode.CLIENT, busAccPass1, busAccNum1, 10 ), "LogAfterOperationNoError");
	} // test_Logging_Transaction_No_Error
	
	private void testing_Pointcuts(Transaction tr, String adviceName) {
//		System.out.println();
		AdviceTracer.addTracedAdvice("LogBeforeOperation");
		AdviceTracer.addTracedAdvice(adviceName);
		AdviceTracer.addTracedAdvice("LogTransaction");
			
		AdviceTracer.setAdviceTracerOn();
		
		tr.executeTransaction();				
		
		AdviceTracer.setAdviceTracerOff();	
		
	    Assert.assertAdviceExecutionsEquals(3);
	    
	    List<TraceElement> advList = AdviceTracer.getExecutedAdvices();	 
//	    for (TraceElement element : advList) {
//	    	System.out.println(element.getAdvice());
//	    } // for
	    
	    assertEquals("LogBeforeOperation", advList.get(0).getAdvice());		
	    assertEquals(adviceName, advList.get(1).getAdvice());		
		assertEquals("LogTransaction", advList.get(2).getAdvice());	
	} // testing_Pointcuts
	
} // Test_TransactionLogging
