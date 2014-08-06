package Server;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Controllers.TransactionController;
import Global.Result;
import Global.TransactionType;
import Global.UserMode;

public class UserThread extends Thread {
	
	private Socket socket;
	private int clientNumber;
	private TransactionController controller;
	
	private String accNumber;
	private Result result;
	
	private TransactionType selectedTransaction;
	private String toAccountNumber = null;
	
    public UserThread(Socket socket, int clientNum, String accNum) {
        this.socket = socket;
        this.clientNumber = clientNum;
        log("New connection with client# " + clientNumber);
        
        accNumber = accNum;
        
        controller = new TransactionController(UserMode.ADMIN);
        
        selectedTransaction = TransactionType.DEPOSIT;
        
    } // UserThread

    boolean resultReceived;
    public void run() {	
    	
    	resultReceived = false;
    	
    	switch ( selectedTransaction ) {
    		case DEPOSIT : 
    						result = deposit(10);
    						break;
    		case WITHDRAW : 
    						result = withdraw(10);
    						break;
    		case TRANSFER : 
			    			result = transfer(toAccountNumber, 10);
							break;    						
    		default:
    						break;
    	} // switch
    	
    	resultReceived = true;
    	/* TESTING COMMUNICATION
        try {
        	if (socket == null)
        		return;
        	ArrayList<String> testing = new ArrayList<>();
        	
        	testing.add("Testing");
        	testing.add("Client");
        	testing.add("Communication");
        
          System.out.print("Sending string: '" + testing + "'\n");

          ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());    
          
          socketOut.writeObject(testing);
          socketOut.flush();         
          
          Thread.sleep(10000);
          
          socket.close();
          Server server = Server.getServerInstance();
          server.removeClient(this);
        }catch(Exception e) {
            System.out.print("Whoops! It didn't work!\n");
        }  	
        */
    } // run
    
    public void selectOperation(TransactionType transaction, String toAccount) {
    	selectedTransaction = transaction;
    	toAccountNumber = toAccount;
    } // selectOperation
    
    public int getClientNumber(){
    	return this.clientNumber;
    } // getClientNumber
    
    private void log(String message) {
        System.out.println(message);
    } // log
    
    public Result getResult() throws InterruptedException {
		while (!resultReceived){
			Thread.sleep(500);
		} // while
		return result;
	} // getResult
    
	private Result deposit( int amount ) {
		return controller.deposit(accNumber, amount);
	} // deposit
	
	private Result withdraw(int amount ) {
		return controller.deposit(accNumber, amount);
	} // withdraw
	
	private Result transfer(String toAccNum, int amount) {
		return controller.transfer(accNumber, toAccNum, amount);
	} // transfer
} // UserThread
