package Model.Transactions;

import java.util.ArrayList;

import Global.Result;

public interface Transaction {
	Result executeTransaction();
	Integer getClientPassword();
	Global.UserMode getUserMode();
	Global.TransactionType getTransactionType();
	Integer getAmount();
	ArrayList<String> getAffectingAccNumbers();
	Integer getTransactionID();
}
