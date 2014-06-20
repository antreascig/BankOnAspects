package Test_Suite;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Services.Login.LoginHandler;

public class Test_Login_Service {
		
	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testContructor() {
		LoginHandler loginService = new LoginHandler();
		assertNotEquals(loginService, null);
			
	} // loginService
	
	@Test
	public void test_authenticate()
	{
		LoginHandler.login();
		
	} // test_IsAuntenticated

}
