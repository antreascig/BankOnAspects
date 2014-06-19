package Model;

public interface BankAccount {
	void deposit(int amount);
	void withdraw(int amount);
	int getBalance();
	String getAccNum();
	boolean evaluateCredentials(Integer password);
	String getAccountType();
} // BankAccount