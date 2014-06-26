package Services.Persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import Global.AccountNumberGenerator;
import Global.TransactionNumberGenerator;
import Model.BankAccounts.BankAccount;

public class Persistent 
{
	private static final String accountsFile = "Accounts.acc";
	
	private static final String transactionNumFile =  "TransactionNumber.set";
	private static final String accountNumFile =  "AccountNumber.set";
	
	public static void saveAccounts(Hashtable<String, BankAccount> accountList ) {
		saveObject(accountsFile, accountList);
	} //saveAccounts
	
	public static void saveCounters() {
		TransactionNumberGenerator trNumber = TransactionNumberGenerator.getTrNumInstance();
		Integer trCounter = trNumber.getTrNumber();
		saveObject(transactionNumFile, trCounter);
		
		AccountNumberGenerator accNumber = AccountNumberGenerator.getInstance();
		Integer accCounter = accNumber.getAccNumber();
		saveObject(accountNumFile, accCounter);	
	} // saveTransactionCounter
	
	private static void saveObject(String fileName, Object obj) {
		ObjectOutputStream saver;
		File file = null;
		File parent_directory = null;
		try {
			file = new File("Data", fileName);
			
			parent_directory = file.getParentFile();
			
			if ( parent_directory != null ) {
			    parent_directory.mkdirs();
			} // if
			
			if ( !file.exists() )
				file.createNewFile();
			
			saver = new ObjectOutputStream(new FileOutputStream(file, false));
			// Write object out to file
			saver.writeObject ( obj );
		} catch (IOException e) {
			e.printStackTrace();
		} //catch	
	} // saveObject
	
	private static Object loadObject(String fileName) {
		ObjectInputStream loader = null;
		Object obj = null;
		File file = null;
		File parent_directory = null;
		try {
			file = new File("Data", fileName);
			
//			System.out.println(file.getPath());		
			
			if ( parent_directory != null || !file.exists() ) {
				return obj;
			} // if
			
			parent_directory = file.getParentFile();
			
			loader = new ObjectInputStream(new FileInputStream(file));
			// Load Object From File
			obj = loader.readObject();
			
			return obj;
		} catch (FileNotFoundException exc) {
			System.out.println(fileName + " was not found. Creating new.");	
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();	
		} //catch
		finally{
			if ( loader != null )
				try {
					loader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} // catch
		} // finally
		return obj;
	} // loadObject
	
	@SuppressWarnings("unchecked")
	public static Hashtable<String, BankAccount> loadAccounts() {
		Object obj = loadObject(accountsFile);
		
		if ( obj == null)
			return new Hashtable<>();
		else
			return (Hashtable<String, BankAccount>) obj;
		
	} //saveAccounts	
	
	public static int loadTransactionCounter() {
		Object obj = loadObject(transactionNumFile);
		
		if ( obj == null)
			return 1;
		else
			return (int) obj;			
	} // saveTransactionCounter
	
	public static int loadAccountCounter() {
		Object obj = loadObject(accountNumFile);
		
		if ( obj == null)
			return 1;
		else
			return (int) obj;			
	} // loadAccountCounter
} // saveAccounts
