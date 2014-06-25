package Model.Transactions;

import java.util.ArrayList;

import Global.Pair;

public interface Transaction {
	void executeTransaction();
	int getClientPassword();
	Global.UserMode getUserMode();
	Global.TransactionType getTransactionType();
	Pair<?> getResult();
	Integer getAmount();
	ArrayList<String> getAffectingAccNumbers();
	Integer getTransactionID();
}
