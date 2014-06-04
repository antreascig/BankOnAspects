package Test_Suite;

import static org.junit.Assert.*;

import org.junit.Test;

import BankOnAspect.BankAccount;
import BankOnAspect.BasicAccount;

public class Test_Account_Operations {

	@Test
	public void test_Account_creation() 
	{
		int accountNum = 1111;
		BankAccount account = new BasicAccount(accountNum);
		
		assertNotEquals(account, null);
		
		assertEquals(accountNum, account.getAccNum());
		
	} // test_Account_creation

}
