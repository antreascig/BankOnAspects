package Services.Logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger 
{
	private static final String transactionFile = "TransactionLog.txt";
	private static final String serverActivityFile = "ServerActivityLog.txt";
	
	public static void logTransaction(String message) {
		writeToFile(transactionFile, message);
	} // logTransaction
	
	public static void logAccountTransaction(String accountNumber, String message) {
		String accountTransactionFile = "//Accounts//" + accountNumber + ".txt";
		writeToFile(accountTransactionFile, message);
	} // logAccountTransaction
	
	public static void logServerActivity(String message) {
		writeToFile(serverActivityFile, message);
	} // writeLog
		
	private static void writeToFile(String fileName, String message) {
		PrintWriter out = null;
		File file = null;
		
		try {
			file = new File("Logs", fileName);
			
			File parent_directory = file.getParentFile();
			
			if ( parent_directory != null ) {
			    parent_directory.mkdirs();
			} // if
			
			if ( !file.exists() )
				file.createNewFile();
			
			out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));			
			
			// An empty message is probably used as a blank line so only add date when message is not empty
			if (!message.equals("")) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				message = dateFormat.format(date) + "\t " + message;
			} // if
		
		    out.println(message);
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if ( out != null )
				out.close();
		} //finally
	} // writeToFile
} // Logger
