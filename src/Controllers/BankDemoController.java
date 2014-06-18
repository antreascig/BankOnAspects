package Controllers;

import java.util.ArrayList;
import java.util.Enumeration;

import Controllers.TransactionControllers.RootTransactionController;
import Controllers.TransactionControllers.TransactionController;
import Global.UserMode;
import Interface.AccountView;

public class BankDemoController 
{
	private TransactionController transactionCtrl;
	
	public BankDemoController()
	{
		transactionCtrl = new RootTransactionController(UserMode.ADMIN);
	}
	
	public void viewAccount(String accNumber) {
		Integer balance = transactionCtrl.getBalance(accNumber);
		
		AccountView newAccountView = new AccountView(accNumber, balance, transactionCtrl );
		
		newAccountView.setVisible(true);
	} // viewAccount

	public void removeAccount(String accNumber) 
	{
		AccountController.removeAccount(accNumber);
	} // removeAccount

	public void addAccount(String accNum, Integer initAmount) 
	{
		AccountController.addAccount(accNum, initAmount);
	} // addAccount
	
	public ArrayList<String> getAccountList() {
		
		Enumeration<String> accNumbers = AccountController.getAccountNumbersList();
		
		ArrayList<String> accList = new ArrayList<>();
		
		while (accNumbers.hasMoreElements())
		{
			accList.add(accNumbers.nextElement());
		}	
		return accList;
	} // getAccountList
	
	public String getAccountType(String accNumber)
	{
		return transactionCtrl.getAccountType(accNumber);
	} // getAccountType

	public void initializeServer() {
		// TODO Auto-generated method stub	
	}
	
} // BankDemoController
