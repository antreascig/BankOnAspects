package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server
{ 
    public static void runServer() 
    { 
    	System.out.println("The Server is running.");
        int clientNumber = 0;
        ServerSocket listener = null;
		try {
			listener = new ServerSocket(9898);
			
            while (true) 
            {
                new UserThread(listener.accept(), clientNumber++).start();
            }
        } catch (IOException e) {
			System.out.println(e.getMessage());

		} finally {
            try {
				listener.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} // catch
        } // finally
    } // runServer
} // Server

