package Server;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UserThread extends Thread {
	
	private Socket socket;
	
    public UserThread(Socket socket, int clientNumber) {
        this.socket = socket;
        log("New connection with client# " + clientNumber + " at " + socket);
    } // UserThread

    public void run() {
        try {
        	ArrayList<String> testing = new ArrayList<>();
        	
        	testing.add("Andreas");
        	testing.add("Giorgos");
        
          System.out.print("Sending string: '" + testing + "'\n");

          ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());    
          
          socketOut.writeObject(testing);
          socketOut.flush();         
                    
          socket.close();
          Server server = Server.getServerInstance();
          server.removeClient(socket);
        }catch(Exception e) {
            System.out.print("Whoops! It didn't work!\n");
        }
    }
    
    private void log(String message) {
        System.out.println(message);
    }
}
