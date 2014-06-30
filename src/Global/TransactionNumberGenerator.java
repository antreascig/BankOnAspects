package Global;

import Services.Persistence.Persistent;

public class TransactionNumberGenerator {
	private Integer counter;
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

	public int getAndIncr() {
		synchronized (counter) {
			return counter++;
		} // synchronised
	} // getAndIncreaseNumber
	
	public void resetCounter() {
		synchronized(counter) {
			this.counter = 0;
		} // synchronised
	} // resetCounter
} // TransactionNumber
