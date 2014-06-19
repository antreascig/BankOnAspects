import Controllers.BankDemoController;
import Interface.BankDemoInterface;
import Server.Server;

public class BankOnAspectsMain {

	public static void main(String[] args) 
	{	
		BankDemoInterface newDemo = new BankDemoInterface(new BankDemoController());
		newDemo.setVisible(true);
		Server server = Server.getServerInstance();
		
		server.addObserver(newDemo);
					
		new Thread(server).start();;
	}

}
