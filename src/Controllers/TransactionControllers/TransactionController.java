package Controllers.TransactionControllers;

import javax.swing.JOptionPane;

import Controllers.AccountController;
import Global.UserMode;
import Model.AccountOperationException;
import Model.BankAccount;

public abstract class TransactionController {

	protected UserMode role;

	public TransactionController(UserMode uRole) 
	{
		role = uRole;
	} // TransactionController

	public UserMode getUserRole() {
		return this.role;
	} // getUserRole

	public void deposit(String accNumber, int amount) {						
			
		try
		{
			BankAccount account = AccountController.getAccount(accNumber);
			account.deposit(amount);
		}
		catch (AccountOperationException exception)
		{
			JOptionPane.showMessageDialog(null,
					exception.getMessage(),
				    "Account Operation Error",
				    JOptionPane.WARNING_MESSAGE);
		}					
		finally {
		} //finally
	} // deposit

	public void withdraw(String accNumber, int amount) {		
		
		try
		{
			BankAccount account = AccountController.getAccount(accNumber);
			account.withdraw(amount);
		}
		catch (AccountOperationException exception)
		{
			JOptionPane.showMessageDialog(null,
					exception.getMessage(),
				    "Account Operation Error",
				    JOptionPane.WARNING_MESSAGE);
		}
		finally {
		} //finally
	} // deposit

	public boolean isInteger(String s) {
	    try 
	    { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) 
	    { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	} // isInteger
	
	public Integer getBalance(String accNumber) {
		try
		{
			BankAccount account = AccountController.getAccount(accNumber);
			return account.getBalance();
		}
		catch (AccountOperationException exception)
		{
			JOptionPane.showMessageDialog(null,
					exception.getMessage(),
				    "Account Operation Error",
				    JOptionPane.WARNING_MESSAGE);
		}
		finally {
		} //finally
		return null;

	} // getAccountBalance

	public String getAccountType(String accNumber)
	{
		try
		{
			BankAccount account = AccountController.getAccount(accNumber);
			return account.getAccountType();
		}
		catch (AccountOperationException exception)
		{
			JOptionPane.showMessageDialog(null,
					exception.getMessage(),
				    "Account Operation Error",
				    JOptionPane.WARNING_MESSAGE);
		}
		finally {
		} //finally
		return null;
	}

}