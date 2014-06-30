package Test_Suite;

import java.io.File;

import Controllers.AccountsController;
import Global.AccountNumberGenerator;
import Global.TransactionNumberGenerator;
import Services.Logging.Logger;

public class TestSetUp 
{
	public static void setUpTestingEnvironment(String testName) {
		// Clears Test Folder
		File testFolder = new File("Test", testName);
		
		if (testFolder.exists())
			deleteFile(testFolder);
		
		// Resets account number generator
		AccountNumberGenerator accNumGenerator = AccountNumberGenerator.getInstance();
		accNumGenerator.resetCounter();
		// Resets transaction number generator
		TransactionNumberGenerator trNumGenerator = TransactionNumberGenerator.getTrNumInstance();
		trNumGenerator.resetCounter();
		// Clears account list
		AccountsController accController = AccountsController.getInstance();
		accController.clearAccounts();
		// Changes logging directory to ./Test/
		Logger.changeDirectory("Test//" + testName);
	} // setTestingEnvironment
	
	public static void tearDownTestingEnvironment() {
		// Changes logging directory to default
		Logger.resetDirectory();
	} // tearDownTestingEnvironment
	
	private static void deleteFile(File file) {
    	if ( file.isDirectory() ) {
    		if( file.list().length==0 ) {
    		   file.delete(); 
    		} 
    		else {
         	   String files[] = file.list();
        	   for (String temp : files) {
        	      File fileDelete = new File(file, temp);
        	      deleteFile(fileDelete);
        	   } // for 
        	   if (file.list().length==0){
           	     file.delete();
        	   } // if
    		} //else
    	}
    	else {
    		file.delete();
    	} // else
	} // deleteFile
} // TestSetUp
