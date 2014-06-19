package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import Global.Pair;

public class Server extends Observable implements Runnable
{ 
	private boolean running;
	
	private ArrayList<UserThread> clients;
	
	private int clientNumber;
	
	private Server()
	{
		running = false;
		clients = new ArrayList<>();
	} // Server
	
	@Override
	public void run() {
        ServerSocket listener = null;
        clientNumber = 1;
		try {
			listener = new ServerSocket(9898);
			
			running = true;
			setChanged();
	    	notifyObservers(new Pair<>("STATUS", running));
	    	
            while (true) 
            {
                addUser(listener.accept());
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
            	notifyObservers(new Pair<>("STATUS", running));
            }
        } // finally
	} // run

	private void addUser(Socket newClientSocket) {
		UserThread newClient = new UserThread(newClientSocket, clientNumber );		
		clients.add(newClient);
		setChanged();
		notifyObservers("");
		notifyObservers(new Pair<>("USER_ADDED", clientNumber));
		
		newClient.start();
			
		clientNumber++;
	}
    
    public void removeClient(UserThread client)
    {
    	clients.remove(client);
    	setChanged();
    	notifyObservers(new Pair<>("USER_REMOVED", client.getClientNumber() ));
    } // removeClient
    
    public int getClientCount()
    {
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
    
} // Server

