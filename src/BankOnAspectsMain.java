import Controllers.BankDemoController;
import Interface.BankDemoInterface;
import Server.Server;

public class BankOnAspectsMain {

	public static void main(String[] args) 
	{
		Server.runServer();
		BankDemoInterface newDemo = new BankDemoInterface(new BankDemoController());
		newDemo.setVisible(true);
	}

}
