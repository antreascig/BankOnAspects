package BankOnAspect;

public interface BankAccount {
	void deposit(int amount);
	void withdraw(int amount);
	int getBalance();
	int getAccNum();
} // BankAccount