package userInterface;

import java.util.ArrayList;
import java.util.List;

import globalClasses.Command;
import globalClasses.Command.COMMAND_TYPE;
import globalClasses.CommandComponent;
import globalClasses.CommandComponent.COMPONENT_TYPE;

public class CommandInterpreter {
	public static Command formCommand(String input) {
		assert(input != null);
		assert(!input.isEmpty());
		
		String commandTypeString = getFirstWord(input);
		COMMAND_TYPE commandType = determineCommandType(commandTypeString);
		
		String commandComponentsString = removeFirstWord(input);
		List<CommandComponent> components = 
				getComponents(commandType, commandComponentsString);
		
		Command command = new Command(commandType, components);
		
		return command;
	}

	/**
	 * Gets the first word from the string
	 * @param sentence with length > 0
	 * @return a String of all characters before the first whitespace (or the end)
	 */
	private static String getFirstWord(String sentence) {
		assert(sentence != null);
		assert(!sentence.isEmpty());
		
		String[] dividedSentence = splitString(sentence);
		String commandTypeString = dividedSentence[0];
		
		return commandTypeString;
	}
	
	// ADD, ALL, CATEGORY, CHANGE_DATE_TYPE, COMPLETED, EDIT, FILTER, FINISH, 
	// HELP, NOTE, REMOVE, REPEAT, SEARCH, SORT, TODAY, UNDO, VIEW,
	// INVALID
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {
		assert(commandTypeString != null);
		
		switch (commandTypeString.toLowerCase()) {
			case "add" :
				return COMMAND_TYPE.ADD;
			case "-ad" :
				return COMMAND_TYPE.ADD;
			case "all" :
				return COMMAND_TYPE.ALL;
			case "-al" :
				return COMMAND_TYPE.ALL;
			case "category" :
				return COMMAND_TYPE.CATEGORY;
			case "cat" :
				return COMMAND_TYPE.CATEGORY;
			case "categories" :
				return COMMAND_TYPE.CATEGORY;
			case "-ca" :
				return COMMAND_TYPE.CATEGORY;
			case "completed" :
				return COMMAND_TYPE.COMPLETED;
			case "-co" :
				return COMMAND_TYPE.COMPLETED;
			case "edit" :
				return COMMAND_TYPE.EDIT;
			case "-e" :
				return COMMAND_TYPE.EDIT;
			case "filter" :
				return COMMAND_TYPE.SEARCH;
			case "-fl" :
				return COMMAND_TYPE.SEARCH;
			case "finish" :
				return COMMAND_TYPE.FINISH;
			case "-fn" :
				return COMMAND_TYPE.FINISH;
			case "help" :
				return COMMAND_TYPE.FINISH;
			case "-h" :
				return COMMAND_TYPE.FINISH;
			case "notes" :
				return COMMAND_TYPE.NOTE;
			case "note" :
				return COMMAND_TYPE.NOTE;
			case "-n" :
				return COMMAND_TYPE.NOTE;
			case "remove" :
				return COMMAND_TYPE.REMOVE;
			case "-rm" :
				return COMMAND_TYPE.REMOVE;
			case "repeat" :
				return COMMAND_TYPE.REPEAT;
			case "-rp" :
				return COMMAND_TYPE.REPEAT;
			case "search" :
				return COMMAND_TYPE.SEARCH;
			case "-se" :
				return COMMAND_TYPE.SEARCH;
			case "sort" :
				return COMMAND_TYPE.SEARCH;
			case "-so" :
				return COMMAND_TYPE.SEARCH;
			case "today" :
				return COMMAND_TYPE.TODAY;
			case "-t" :
				return COMMAND_TYPE.TODAY;
			case "undo" :
				return COMMAND_TYPE.UNDO;
			case "-u" :
				return COMMAND_TYPE.UNDO;
			case "view" :
				return COMMAND_TYPE.SEARCH;
			case "-v" :
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
		assert(sentence != null);
		String[] splitSentence = splitString(sentence);
		
		String shorterString = new String();
		for (int i = 1; i < sentence.length(); ++i) {
			shorterString = shorterString.concat(splitSentence[i]);
		}
		
		return shorterString;
	}
	
	private static List<CommandComponent> getComponents(String string,
			COMMAND_TYPE commandType) {
		assert(string != null);
		assert(!string.isEmpty());

		List<CommandComponent> components = new ArrayList<CommandComponent>();
		
		for (int i = 0; i < string.length(); ++i) {
			CommandComponent component = getNextComponent(string);
			string = eraseComponent(component, string);
			components.add(component);
		}
		
		return components;
	}
	
	private static CommandComponent getNextComponent(String componentSentence) {
		String componentTypeString = getFirstWord(componentSentence);
		COMPONENT_TYPE componentType = 
				determineComponentType(componentTypeString);
		
		String withoutFirstType = removeFirstWord(componentSentence);
		String componentData = getComponentData(withoutFirstType);
		
		CommandComponent component = 
				new CommandComponent(componentType, componentData);
		return component;
	}
	
	//List of CommandComponents: 
			// list[0]: name
			// list[1]: category
			// list[2]: start date
			// list[3]: end date
			// list[4]: location
			// list[5]: note

	private static COMPONENT_TYPE determineComponentType(
			String componentTypeString) {
		switch (componentTypeString.toLowerCase()) {
			case ("name") :
				return COMPONENT_TYPE.NAME;
		}
		return null;
	}

	private static String getComponentData(String withoutFirstType) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String eraseComponent(CommandComponent component,
			String sentence) {
		assert(sentence != null);
		assert(component != null);
		
		String match = extractComponentMatch(component, sentence);
	
		String emptyString = "";
		
		sentence = sentence.replaceFirst(match, emptyString);
		sentence = sentence.trim();
				
		return sentence;
	}

	private static String extractComponentMatch(
			CommandComponent component, String sentence) {
		String[] splitSentence = splitString(sentence);
		String matchingString = component.getContents();
		String growingMatch = splitSentence[0];
		
		assert(sentence.contains(matchingString));
		
		for (int i = 1; !growingMatch.contains(matchingString); ++i) {
			growingMatch = growingMatch.concat(splitSentence[i]);
		}
		
		return growingMatch;
	}

	/**
	 * Splits string at whitespace.
	 * @param toSplit
	 * @return
	 */
	private static String[] splitString(String toSplit) {
		assert(toSplit != null);
		assert(!toSplit.isEmpty());
		String whiteSpace = "\\s+";
		 
		String noEndWhitespace = toSplit.trim();
		
		String[] dividedString = noEndWhitespace.split(whiteSpace);
		
		return dividedString;
	}
	
	
}
