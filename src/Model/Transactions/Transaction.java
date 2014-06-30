package Model.Transactions;

import java.util.ArrayList;

import Global.Result;

public interface Transaction {
	void executeTransaction();
	int getClientPassword();
	Global.UserMode getUserMode();
	Global.TransactionType getTransactionType();
	Result<?> getResult();
	Integer getAmount();
	ArrayList<String> getAffectingAccNumbers();
	Integer getTransactionID();
}
