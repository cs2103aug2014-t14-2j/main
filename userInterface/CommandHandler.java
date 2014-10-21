package userInterface;

import dataEncapsulation.ActionException;
import dataManipulation.*;


public class CommandHandler {
	
	private static String ret;
	/**
	 * Will call the appropriate method from data manipulation or search
	 * based on the command type
	 * @param command
	 * @return a string containing the feedback returned by the called method
	 * @throws Exception 
	 */
	public static String executeCommand(Command cmd) {
		try {
			ret = cmd.execute();
		} catch (ActionException e) {
			ret = ExceptionHandler.furtherAction(e);
		} catch (Exception e) {
			ret = ezCMessages.getErrorMessage(e);
		}
		return ret;
	}

}
