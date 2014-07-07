package Server;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Controllers.TransactionController;
import Global.Result;
import Global.UserMode;

public class UserThread extends Thread {
	
	private Socket socket;
	private int clientNumber;
	private TransactionController controller;
	private Result result;
	
	private String accNumber;
	
    public UserThread(Socket socket, int clientNum, String accNum) {
        this.socket = socket;
        this.clientNumber = clientNum;
        log("New connection with client# " + clientNumber);
        
        accNumber = accNum;
        
        controller = new TransactionController(UserMode.ADMIN);
    } // UserThread

    boolean resultReceived;
    public void run() {
    	
    	resultReceived = false;
    	result = deposit(accNumber, 100);
    	resultReceived = true;
    	
        try {
        	if (socket == null)
        		return;
        	ArrayList<String> testing = new ArrayList<>();
        	
        	testing.add("Andreas");
        	testing.add("Giorgos");
        
          System.out.print("Sending string: '" + testing + "'\n");

          ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());    
          
          socketOut.writeObject(testing);
          socketOut.flush();         
                    
          socket.close();
          Server server = Server.getServerInstance();
          server.removeClient(this);
        }catch(Exception e) {
            System.out.print("Whoops! It didn't work!\n");
        }  	
    } // run
    
    public int getClientNumber(){
    	return this.clientNumber;
    }
    
    private void log(String message) {
        System.out.println(message);
    }


	private Result deposit(String toAccNum, int amount ) {
		return controller.deposit(toAccNum, amount);
	} // deposit

	public Result getResult() throws InterruptedException {
		while (!resultReceived){
			Thread.sleep(1000);
		}
		return result;
	}
}
