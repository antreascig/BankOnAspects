package Global;

import Services.Persistence.Persistent;

public class AccountNumberGenerator {
private Integer counter;
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

	public int getAndIncreaseNumber() {
		synchronized (counter) {
			return counter++;
		} // synchronised
	} // getAndIncreaseNumber
	
	public void resetCounter() {
		synchronized(counter) {
			this.counter = 0;
		} // synchronised
	} // resetCounter	
} // AccountNumber
