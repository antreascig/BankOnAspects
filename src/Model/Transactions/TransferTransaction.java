package Model.Transactions;

import Global.Pair;
import Global.TransactionType;
import Global.UserMode;

public class TransferTransaction implements Transaction {
	private Integer transactionID;
	
	
	public TransferTransaction()
	{
		
	}

	@Override
	public void executeTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getClientPassword() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserMode getUserMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionType getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<?> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAffectingAccNumber() {
		// TODO Auto-generated method stub
		return null;
	} // getAffectingAccNumber

	@Override
	public Integer getTransactionID() {
		return transactionID;
	} // getTransactionNumber
} // TransferTransaction