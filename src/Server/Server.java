package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import Controllers.BankDemoController;
import Global.Update;
import Interface.BankDemoInterface;

public class Server extends Observable
{ 
	private Boolean isRunning;
	private ArrayList<UserThread> clients;
	private int clientNumber;
	private ServerSocket listener;
	
	
	private Server() {
		isRunning = false;
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
			
			isRunning = true;
			setChanged();
	    	notifyObservers(new Update<>("STATUS", isRunning));
	    	
            while (isRunning) {           	
                addClient(listener.accept());
            } // while
        } catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
            try {
            	if ( listener != null )
            		listener.close();
			} catch (IOException e) {
				System.out.println("Exception: " + e.getMessage());
			} // catch
            finally {
            	isRunning = false;
            	notifyObservers(new Update<>("STATUS", isRunning));
            } // finally
        } // finally
	} // run
	
	public void stopServer() {	
		try {
			listener.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			isRunning = false;
			notifyObservers(new Update<>("STATUS", isRunning));
		} // finally
	} // stopServer;

	private void addClient(Socket newClientSocket) {
		UserThread newClient = new UserThread(newClientSocket, clientNumber, "" );		
		clients.add(newClient);
		setChanged();
		notifyObservers(new Update<>("USER_ADDED", clientNumber));
		
		newClient.start();
			
		clientNumber++;
	} // addClient
    
    public void removeClient(UserThread client) {
    	clients.remove(client);
    	setChanged();
    	notifyObservers(new Update<>("USER_REMOVED", client.getClientNumber() ));
    } // removeClient
    
    public int getClientCount() {
    	return clients.size();
    } // getClientCount
    
    public boolean isRunning() {
    	return isRunning;
    } // isRunning
    
    private static Server instance = null;
    
    public static Server getServerInstance() {
    	if ( instance == null )
    		instance = new Server();
    	return instance;
    } // getServerInstance	s
    
} // Server

