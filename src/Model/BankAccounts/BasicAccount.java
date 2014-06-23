package Model.BankAccounts;

public class BasicAccount extends BankAccount {
	
	public BasicAccount(String accountNum, Integer pass)
	{
		super(accountNum, pass);
		setTransferAllowed(false);
		
		setTransferAllowed(false);
	} // BasicAccount
	
	public BasicAccount(String accountNum, Integer pass, int initBalance)
	{
		super(accountNum, pass, initBalance);
		
		setTransferAllowed(false);
	} // BasicAccount

	@Override
	public String getAccountType() {
		return "BASIC ACCOUNT";
	} // getAccountType
	
} // BasicAccount
