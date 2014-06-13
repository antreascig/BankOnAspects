package Model;

public interface BankAccount {
	void deposit(int amount);
	void withdraw(int amount);
	int getBalance();
	String getAccNum();
	boolean evaluateCredentials(String pass);
	String getAccountType();
} // BankAccount