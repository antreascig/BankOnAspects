package Services.Logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger 
{
	public static void logTransaction(String message)
	{
		String transactionFile = "TransactionLog.txt";
		writeToFile(transactionFile, message);
	} // writeLog
	
	public static void logAccountTransaction(String accountNumber, String message)
	{
		String transactionFile = accountNumber + ".txt";
		writeToFile(transactionFile, message);
	}
	
	public static void logServerActivity(String message)
	{
		String transactionFile = "ServerActivityLog.txt";
		writeToFile(transactionFile, message);
	} // writeLog
	
	
	private static void writeToFile(String file, String message) {
		PrintWriter out = null;
		try
		{
			out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));			
			
			// An empty message is probably used as a blank line so only add date when message is not empty
			if (!message.equals("")) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				message = dateFormat.format(date) + "\t " + message;
			} // if
		
		    out.println(message);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}finally {
			out.close();
		} //finally
	}

}
