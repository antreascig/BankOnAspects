package Services.Logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger 
{
	public static void writeLog(String message)
	{
		PrintWriter out = null;
		try
		{
			out = new PrintWriter(new BufferedWriter(new FileWriter("TransactionLog.txt", true)));
		    out.println(message);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}finally {
			out.close();
		} //finally
	}

}
