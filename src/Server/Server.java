package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

public class Server extends Observable implements Runnable
{ 
	private boolean running;
	
	private ArrayList<Socket> clients;
	
	private Server()
	{
		running = false;
		clients = new ArrayList<>();
	}
    
    public void removeClient(Socket client)
    {
    	clients.remove(client);
    	setChanged();
    	notifyObservers(clients.size());
    } // removeClient
    
    public int getClientCount()
    {
    	// Checks for disconnected clients
    	for (Socket client : clients)
    	{
    		if (client.isClosed())
    			clients.remove(client);
    	} // for
    	return clients.size();
    } // getClientCount
    
    public boolean isRunning()
    {
    	return running;
    } // isRunning
    
    private static Server instance = null;
    
    public static Server getServerInstance()
    {
    	if ( instance == null )
    		instance = new Server();

    	return instance;
    } // getServerInstance

	@Override
	public void run() {
		int clientNumber = 0;
        ServerSocket listener = null;
		try {
			listener = new ServerSocket(9898);
			
			running = true;
			System.out.println("The Server is actually running.");
            while (true) 
            {
            	Socket newClient = listener.accept();
                new UserThread(newClient, clientNumber++).start();
                
                clients.add(newClient);
                setChanged();
                notifyObservers(clients.size());
            } // while
        } catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
            try {
				listener.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} // catch
            finally
            {
            	running = false;
            }
        } // finally
	} // run
    
} // Server

