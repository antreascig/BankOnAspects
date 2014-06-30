package Global;

public enum AccountType {
	BASIC_ACCOUNT ("Basic Account"),
	BUSINESS_ACCOUNT ("Business Account");
	
	private final String text;

    private AccountType(final String text) {
        this.text = text;
    } // TransactionType
    
    @Override
    public String toString() {
        return text;
    } // toString
} // AccountType
