package userInterface;

import dataManipulation.*;


public class CommandHandler {
	
	/**
	 * Will call the appropriate method from data manipulation or search
	 * based on the command type
	 * @param command
	 * @return a string containing the feedback returned by the called method
	 * @throws Exception 
	 */
	public static String executeCommand(Command cmd) throws Exception {
		
		return cmd.execute();
		
		
	}

}
