package Global;

import Services.Persistence.Persistent;

public class TransactionNumberGenerator {
	private int counter;
	
	private static TransactionNumberGenerator instance = null;
	
	private TransactionNumberGenerator() {
		counter = Persistent.loadTransactionCounter();
	} // TransactionNumber
	
	public static TransactionNumberGenerator getTrNumInstance() {
		if (instance == null)
			instance = new TransactionNumberGenerator();
		return instance;
	} // getTrNumInstance
	
	public int getTrNumber() {
		return this.counter;
	} // getTrNumber

	public synchronized int getAndIncreaseNumber() {
		return counter++;
	} // getAndIncreaseNumber
	
} // TransactionNumber
