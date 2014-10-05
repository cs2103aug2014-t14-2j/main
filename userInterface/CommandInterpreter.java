package userInterface;

import java.util.List;

import globalClasses.Command;
import globalClasses.Command.COMMAND_TYPE;
import globalClasses.CommandComponent;

public class CommandInterpreter {
	public static Command formCommand(String input) {
		assert(input != null);
		assert(input.length() > 0);
		
		String commandTypeString = getFirstWord(input);
		COMMAND_TYPE commandType = determineCommandType(commandTypeString);
		
		String commandComponentsString = removeFirstWord(input);
		List<CommandComponent> components = getComponents(commandComponentsString);
		
		return null;
	}

	/**
	 * Gets the first word from the string
	 * @param sentence with length > 0
	 * @return a String of all characters before the first whitespace (or the end)
	 */
	private static String getFirstWord(String sentence) {
		assert(sentence != null);
		assert(sentence.length() > 0);
		
		String[] dividedSentence = splitString(sentence);
		String commandTypeString = dividedSentence[0];
		
		return commandTypeString;
	}
	
	//List of CommandComponents: 
		// list[0]: name
		// list[1]: category
		// list[2]: start date
		// list[3]: end date
		// list[4]: location
		// list[5]: note
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {
		assert(commandTypeString != null);
		
		switch (commandTypeString.toLowerCase()) {
			case "add" :
				return COMMAND_TYPE.ADD;
			case "delete" :
				return COMMAND_TYPE.DELETE;
			case "edit" :
				return COMMAND_TYPE.EDIT;
			case "search" :
				return COMMAND_TYPE.SEARCH;
			default :
				return COMMAND_TYPE.INVALID;
		}
	}
	
	/**
	 * Removes the first substring before whitespace, as well as end whitespace
	 * @param input
	 * @return
	 */
	private static String removeFirstWord(String sentence) {
		String[] splitSentence = splitString(sentence);
		
		String shorterString = new String();
		for (int i = 1; i < sentence.length(); ++i) {
			shorterString = shorterString.concat(splitSentence[i]);
		}
		
		return shorterString;
	}
	
	/**
	 * Splits string at whitespace.
	 * @param toSplit
	 * @return
	 */
	private static String[] splitString(String toSplit) {
		assert(toSplit != null);
		assert(toSplit.length() > 0);
		String whiteSpace = "\\s+";
		 
		String noEndWhitespace = toSplit.trim();
		
		String[] dividedString = noEndWhitespace.split(whiteSpace);
		
		return dividedString;
	}
	
	
}
