package Global;

public class TransactionNumber {
	private int counter;
	
	private static TransactionNumber instance = null;
	
	private TransactionNumber() {
		counter = 1;
	}
	public static TransactionNumber getTrNumInstance() {
		if (instance == null)
			instance = new TransactionNumber();
		
		return instance;
	} // getTrNumInstance
	
	public int getTrNumber() {
		return this.counter;
	} // getTrNumber

	public synchronized int getAndIncreaseNumber()
	{
		return counter++;
	} // getAndIncreaseNumber
} // TransactionNumber
