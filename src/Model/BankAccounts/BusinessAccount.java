package Model.BankAccounts;

public class BusinessAccount extends BankAccount {

	public BusinessAccount(String accountNum, Integer pass) {
		super(accountNum, pass);
		
		setTransferAllowed(true);
	} // BusinessAccount
	
	public BusinessAccount(String accountNum, Integer pass, int initBalance) {
		super(accountNum, pass, initBalance);
		
		setTransferAllowed(true);
	} // BusinessAccount

	@Override
	public String getAccountType() {
		// TODO Auto-generated method stub
		return "BUSINESS ACCOUNT";
	} //getAccountType
} // BusinessAccount
