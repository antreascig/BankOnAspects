package Global;

public enum TransactionType { 
	DEPOSIT ("Deposit") , 
	WITHDRAW ("Withdrawal"), 
	BALANCE ("Balance"),
	TRANSFER ("Transfer");
							  
							  
	private final String text;

    private TransactionType(final String text) {
        this.text = text;
    } // TransactionType
    
    @Override
    public String toString() {
        return text;
    } // toString
} // TransactionType
