package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import Controllers.BankDemoController;
import Global.Pair;
import Interface.BankDemoInterface;

public class Server extends Observable
{ 
	private Boolean running;
	private ArrayList<UserThread> clients;
	private int clientNumber;
	private ServerSocket listener;
	
	
	private Server() {
		running = false;
		clients = new ArrayList<>();
	} // Server
	
	public void runServer() {
		// Create and run Graphical Interface of Application 
		BankDemoInterface newDemo = new BankDemoInterface(new BankDemoController());
		newDemo.setVisible(true);
		
		// Add interface as an observer
		addObserver(newDemo);	
		
		// Create listener socket
		listener = null;
        clientNumber = 1;
		try {
			listener = new ServerSocket(9898);
			
			running = true;
			setChanged();
	    	notifyObservers(new Pair<>("STATUS", running));
	    	
            while (running) 
            {           	
                addClient(listener.accept());
            } // while
        } catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
            try {
				listener.close();
			} catch (IOException e) {
				System.out.println("Exception: " + e.getMessage());
			} // catch
            finally {
            	running = false;
            	notifyObservers(new Pair<>("STATUS", running));
            } // finally
        } // finally
	} // run
	
	public void stopServer() {	
		try {
			listener.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			running = false;
			notifyObservers(new Pair<>("STATUS", running));
		} // finally
	} // stopServer;

	private void addClient(Socket newClientSocket) {
		UserThread newClient = new UserThread(newClientSocket, clientNumber );		
		clients.add(newClient);
		setChanged();
		notifyObservers("");
		notifyObservers(new Pair<>("USER_ADDED", clientNumber));
		
		newClient.start();
			
		clientNumber++;
	} // addClient
    
    public void removeClient(UserThread client) {
    	clients.remove(client);
    	setChanged();
    	notifyObservers(new Pair<>("USER_REMOVED", client.getClientNumber() ));
    } // removeClient
    
    public int getClientCount() {
    	return clients.size();
    } // getClientCount
    
    public boolean isRunning() {
    	return running;
    } // isRunning
    
    private static Server instance = null;
    
    public static Server getServerInstance() {
    	if ( instance == null )
    		instance = new Server();
    	return instance;
    } // getServerInstance	
    
} // Server

