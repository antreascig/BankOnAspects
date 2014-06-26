package Global;

import Services.Persistence.Persistent;

public class AccountNumberGenerator {
private int counter;
	
	private static AccountNumberGenerator instance = null;
	
	private AccountNumberGenerator() {
		counter = Persistent.loadAccountCounter();
	} // AccountNumber
	
	public static AccountNumberGenerator getInstance() {
		if (instance == null)
			instance = new AccountNumberGenerator();
		
		return instance;
	} // getTrNumInstance
	
	public int getAccNumber() {
		return this.counter;
	} // getTrNumber

	public synchronized int getAndIncreaseNumber() {
		return counter++;
	} // getAndIncreaseNumber

} // AccountNumber
