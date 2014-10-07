package userInterface;

import java.util.ArrayList;
import java.util.List;

import globalClasses.Command;
import globalClasses.Command.COMMAND_TYPE;
import globalClasses.CommandComponent;
import globalClasses.CommandComponent.COMPONENT_TYPE;

public class CommandInterpreter {
	public static Command formCommand(String input) throws IllegalArgumentException {
		assert(input != null);
		assert(!input.isEmpty());
		
		if (isSpecialCommand(input)) {
			Command command = determineSpecialCommand(input);
			return command;
		}
		
		String commandTypeString = getFirstWord(input);
		COMMAND_TYPE commandType = determineCommandType(commandTypeString);
		Command command;
		
		if (commandType == COMMAND_TYPE.INVALID) {
			throw new IllegalArgumentException("unrecognized command type");
		} 

		List<CommandComponent> components;
		
		if (isNoComponentCommand(commandType)) {
			components = getNoCommandComponents(input, commandType);
		} else {
			input = removeFirstWord(input);
			components = getComponents(input, commandType);
		}
		
		command = new Command(commandType, components);
		
		return command;
	}
	
//-----------------------------------------------------------------------------
//---------------- Command Related Methods ------------------------------------
//-----------------------------------------------------------------------------

	private static boolean isSpecialCommand(String input) {
		input = input.toLowerCase();
		
		String[] splitInput = splitString(input);
		
		if (isChangeDateType(splitInput)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isChangeDateType(String[] splitInput) {
		String[] changeDateTypeExample = makeChangeDateTypeExample();
		
		if(splitInput.length != changeDateTypeExample.length) {
			return false;
		}
		
		// last element of command is a variable and not the command itself
		int commandLength = splitInput.length - 1;
		int lastPosition = splitInput.length - 1;
		
		for (int i = 0; i < commandLength; ++i) {
			if (!splitInput[i].equals(changeDateTypeExample[i])) {
				return false;
			}
		}
		
		String firstOption = "d/m";
		String secondOption = "m/d";
		String usersOption = splitInput[lastPosition];
		
		if (!usersOption.equals(firstOption) && !usersOption.equals(secondOption)) {
			return false;
		}
		
		return true;
	}

	private static String[] makeChangeDateTypeExample() {
		int length = 4;	// "change" "date" "type" "D/M", etc
		String[] example = new String[length];
		
		int iterator = 0;
		
		example[iterator++] = "change";
		example[iterator++] = "date";
		example[iterator++] = "type";
		example[iterator++] = "d/m";
		
		return example;
	}

	/**
	 * assumes that input has already been checked to match the special command
	 * formats. Will only check variables.
	 * 
	 * Currently, there is only one special command, so it directly asks for
	 * "change date type d/m" at "d/m" (position index 3)
	 * 
	 * @param input
	 * @return
	 */
	private static Command determineSpecialCommand(String input) {
		input = input.toLowerCase();
		
		String[] splitInput = splitString(input);
		int lastPosition = splitInput.length - 1;

		CommandComponent component = new CommandComponent(COMPONENT_TYPE.DATE_TYPE, 
				splitInput[lastPosition]);
		
		List<CommandComponent> list = new ArrayList<CommandComponent>();
		list.add(component);
		
		Command command = new Command(COMMAND_TYPE.CHANGE_DATE_TYPE, list);

		return command;
	}
	
	// ADD, ALL, CATEGORY, CHANGE_DATE_TYPE, COMPLETED, EDIT, FILTER, FINISH, 
	// HELP, NOTE, REMOVE, REPEAT, SEARCH, SORT, TODAY, UNDO, VIEW,
	// INVALID
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {
		assert(commandTypeString != null);
		commandTypeString = commandTypeString.trim();
		
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
	
//-----------------------------------------------------------------------------
//------------------ Subcommand Related Methods -------------------------------
//-----------------------------------------------------------------------------
	
	private static List<CommandComponent> getComponents(String string,
			COMMAND_TYPE commandType) {
		assert(string != null);
		assert(!string.isEmpty());

		List<CommandComponent> components = new ArrayList<CommandComponent>();
		
		CommandComponent component;
		// Search and Sort must label their first subcommands
		if (commandType != COMMAND_TYPE.SEARCH && commandType != COMMAND_TYPE.SORT) {
			component = getFirstComponent(commandType, string);
			if (isQuotationComponent(component)) {
				string = eraseQuoteComponent(string);
			} else {
				string = eraseNoQuoteComponent(component, string);
			}
			components.add(component);
		}
		
		while (string.length() > 0) {
			component = getNextComponent(string);
			if (isQuotationComponent(component)) {
				string = eraseQuoteComponent(string);
			} else {
				string = eraseNoQuoteComponent(component, string);
			}
			components.add(component);
		}
		
		return components;
	}

	private static List<CommandComponent> getNoCommandComponents(String input,
			COMMAND_TYPE commandType) {
		assert(input != null);
		
		try {
			input = removeFirstWord(input);
		} catch (IllegalArgumentException e) {
			input = "";
		}
		
		return createNoComponent(input, commandType);
	}
	
	private static List<CommandComponent> createNoComponent(String string,
			COMMAND_TYPE commandType) {
		assert(string != null);
		
		string = string.trim();
		
		if (!string.isEmpty()) {
			throw new IllegalArgumentException("too many arguments");
		}
		
		List<CommandComponent> componentList = new ArrayList<CommandComponent>();
		
		return componentList;
	}

	private static boolean isNoComponentCommand(COMMAND_TYPE commandType) {
		switch (commandType) {
			case ALL :
				return true;
			case COMPLETED :
				return true;
			case HELP :
				return true;
			case TODAY :
				return true;
			case UNDO :
				return true;
			default :
				return false;
		}
	}

	/**
	 * The first component should be directly after the command type
	 * Will be surrounded by quotations if formatted correctly
	 * 
	 * Assumes that the command type has already been removed from the string
	 * 
	 * @param string
	 * @return
	 */
	private static CommandComponent getFirstComponent(COMMAND_TYPE commandType,
			String string) {
		string = string.trim();	// remove whitespace
		
		COMPONENT_TYPE componentType = determineFirstComponentType(commandType);
		
		String componentData = getComponentData(string, componentType);
		
		CommandComponent component = 
				new CommandComponent(componentType, componentData);
		return component;
	}

	private static COMPONENT_TYPE determineFirstComponentType(
			COMMAND_TYPE commandType) {
		switch (commandType) {
			case ADD :
				return COMPONENT_TYPE.NAME;
			case CATEGORY :
				return COMPONENT_TYPE.CATEGORY;
			case EDIT :
				return COMPONENT_TYPE.NAME;
			case FINISH :
				return COMPONENT_TYPE.NAME;
			case NOTE :
				return COMPONENT_TYPE.NAME;
			case REMOVE :
				return COMPONENT_TYPE.NAME;
			case REPEAT :
				return COMPONENT_TYPE.NAME;
			default :
				return COMPONENT_TYPE.INVALID;
		}
	}
	
	private static CommandComponent getNextComponent(String componentSentence) {
		String componentTypeString = getFirstWord(componentSentence);
		COMPONENT_TYPE componentType = 
				determineComponentType(componentTypeString);
		
		if (componentType != COMPONENT_TYPE.FREQUENCY) {
			componentSentence = removeFirstWord(componentSentence);
		}
		
		String componentData = getComponentData(componentSentence, 
				componentType);
		
		CommandComponent component = 
				new CommandComponent(componentType, componentData);
		return component;
	}

	private static COMPONENT_TYPE determineComponentType(
			String componentTypeString) {
		componentTypeString = componentTypeString.trim();
		
		switch (componentTypeString.toLowerCase()) {
		case ("and") :
			return COMPONENT_TYPE.AND;
		case ("&") :
			return COMPONENT_TYPE.AND;
		case ("annually") :
			return COMPONENT_TYPE.FREQUENCY;
		case ("begin") :
			return COMPONENT_TYPE.START;
		case ("-b") :
			return COMPONENT_TYPE.START;
		case ("category") :
			return COMPONENT_TYPE.CATEGORY;
		case ("cat") :
			return COMPONENT_TYPE.CATEGORY;
		case ("-c") :
			return COMPONENT_TYPE.CATEGORY;
		case ("daily") :
			return COMPONENT_TYPE.FREQUENCY;
		case ("date") :
			return COMPONENT_TYPE.DATE;
		case ("-dt") :
			return COMPONENT_TYPE.DATE;
		case ("deadline") :
			return COMPONENT_TYPE.END;
		case ("-dl") :
			return COMPONENT_TYPE.END;
		case ("end") :
			return COMPONENT_TYPE.END;
		case ("-e") :
			return COMPONENT_TYPE.END;
		case ("location") :
			return COMPONENT_TYPE.LOCATION;
		case ("-l") :
			return COMPONENT_TYPE.LOCATION;
		case ("monthly") :
			return COMPONENT_TYPE.FREQUENCY;
		case ("note") :
			return COMPONENT_TYPE.NOTE;
		case ("-n") :
			return COMPONENT_TYPE.NOTE;
		case ("once") :
			return COMPONENT_TYPE.FREQUENCY;
		case ("start") :
			return COMPONENT_TYPE.START;
		case ("-s") :
			return COMPONENT_TYPE.START;
		case ("title") :
			return COMPONENT_TYPE.TITLE;
		case ("-t") :
			return COMPONENT_TYPE.TITLE;
		case ("weekly") :
			return COMPONENT_TYPE.FREQUENCY;
		default :
			return COMPONENT_TYPE.INVALID;
		}
	}

	/**
	 * Assumes that the String is not between quotation marks (which would
	 * allow the user to write subcommand keywords)
	 * 
	 * @param componentPart
	 * @return
	 */
	private static boolean isSubcommand(String possibleSubcommand) {
		COMPONENT_TYPE possibleType = 
				determineComponentType(possibleSubcommand);
		
		if (possibleType == COMPONENT_TYPE.INVALID) {
			return false;
		} else {
			return true;
		}
	}

	private static String eraseNoQuoteComponent(CommandComponent component,
			String sentence) {
		assert(sentence != null);
		assert(component != null);
		
		String match = extractComponentMatch(component, sentence);
	
		String emptyString = "";
		
		sentence = sentence.replaceFirst(match, emptyString);
		
		if (sentence.equals(match)) {
			return emptyString;	// replaceFirst will not replace the entire string
		}
		
		sentence = sentence.trim();
				
		return sentence;
	}
	
	private static String eraseQuoteComponent(String sentence) 
			throws IndexOutOfBoundsException {
		assert(sentence != null);
		char quote = '"';
		
		int indexOfNextQuote = sentence.indexOf(quote);
		if (indexOfNextQuote == -1) {
			throw new IndexOutOfBoundsException("No double quote found");
		}
		
		String withoutUntilFirstQuote = sentence.substring(indexOfNextQuote + 1);
		
		indexOfNextQuote = withoutUntilFirstQuote.indexOf(quote);
		int indexOfEnd = withoutUntilFirstQuote.length() - 1;
		// if the component is the last component
		if (indexOfNextQuote == indexOfEnd) {
			String emptyString = "";
			return emptyString;
		}
		
		if (indexOfNextQuote == -1) {
			throw new IndexOutOfBoundsException("No double quote found");
		}
		
		String withoutUntilSecondQuote = 
				withoutUntilFirstQuote.substring(indexOfNextQuote + 1);

		withoutUntilSecondQuote = withoutUntilSecondQuote.trim();
				
		return withoutUntilSecondQuote;
	}
	
	private static boolean isQuotationComponent(CommandComponent component) {
		COMPONENT_TYPE type = component.getType();
		
		switch (type) {
			case CATEGORY :
				return true;
			case LOCATION :
				return true;
			case NAME :
				return true;
			case NOTE :
				return true;
			case TITLE :
				return true;
			default :
				return false;
		}
	}

//-----------------------------------------------------------------------------
//------------------ Subcommand Data Related Methods --------------------------
//-----------------------------------------------------------------------------

	private static String getComponentData(String userCommand, 
			COMPONENT_TYPE componentType) {
		if (componentType == COMPONENT_TYPE.START ||
				componentType == COMPONENT_TYPE.END ||
				componentType == COMPONENT_TYPE.DATE ||
				componentType == COMPONENT_TYPE.FREQUENCY) {
			return getDateComponentData(userCommand);
		}
		
		userCommand = userCommand.trim();
		return getBetweenQuoteData(userCommand);
	}

	private static String getDateComponentData(String userCommand) {
		assert(!userCommand.isEmpty());
		
		String[] splitCommand = splitString(userCommand);
		String removedWhitespace = " ";
		
		String wholeData = splitCommand[0];
		String dataPart = new String();
		for (int i = 1; (i < splitCommand.length) && 
				(!isSubcommand(dataPart)); ++i) {
			dataPart = splitCommand[i];
			if (!isSubcommand(dataPart)) {
				wholeData = wholeData + removedWhitespace + dataPart;
			}
		}
		wholeData = wholeData.trim();
		
		return wholeData;
	}
	
	private static String getBetweenQuoteData(String userCommand) 
			throws IllegalArgumentException {
		String emptyString = "";
		String doubleQuoteMark = "\"";
		
		userCommand = userCommand.trim();
		
		checkFirstQuotation(userCommand);	// first character should be a quotation
		userCommand = userCommand.replaceFirst(doubleQuoteMark, emptyString);
		String commandData = getUntilQuotation(userCommand);
		
		return commandData;
	}
	
	private static void checkFirstQuotation(String userCommand) {
		String doubleQuote = "\"";
		if (!userCommand.startsWith(doubleQuote)) {
			throw new IllegalArgumentException("invalid subcommand argument");
		}
	}
	
	private static String getUntilQuotation(String userCommand) {
		assert(userCommand != null);
		if (userCommand.isEmpty()) {
			throw new IllegalArgumentException("invalid subcommand argument");
		}
		
		String fullString = userCommand.substring(0, 1);
		String singleChar = new String();
		String doubleQuote = "\"";
		
		for (int i = 1; (i != userCommand.length()) && (!singleChar.equals(doubleQuote)); ++i) {
			fullString = fullString + singleChar;
			singleChar = userCommand.substring(i, i+1);
		}
		
		// check to see if it ever saw another quotation mark
		if (!singleChar.equals(doubleQuote)) {
			throw new IllegalArgumentException("invalid subcommand argument");
		}
		
		return fullString;
	}
	
	private static String extractComponentMatch(
			CommandComponent component, String sentence) {
		String[] splitSentence = splitString(sentence);
		String matchingString = component.getContents();
		String growingMatch = splitSentence[0];
		String space = " ";
		
		assert(sentence.contains(matchingString));
		
		for (int i = 1; ((!growingMatch.contains(matchingString)) && 
				(i < splitSentence.length)); ++i) {
			growingMatch = growingMatch.concat(space);
			growingMatch = growingMatch.concat(splitSentence[i]);
		}
		growingMatch = growingMatch.trim();
		
		return growingMatch;
	}
	
//-----------------------------------------------------------------------------
//------------------ General Methods ------------------------------------------
//-----------------------------------------------------------------------------
	
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
	
	/**
	 * Removes the first substring before whitespace, as well as end whitespace
	 * @param input
	 * @return
	 */
	private static String removeFirstWord(String sentence) {
		assert(sentence != null);
		
		String[] splitSentence = splitString(sentence);
		
		int minimumSize = 2;	// must be at least 2 words in command;
		if (splitSentence.length < minimumSize) {
			throw new IllegalArgumentException("too short of argument");
		}
		
		String shorterString = new String();
		String space = " ";
		for (int i = 1; i < splitSentence.length; ++i) {
			shorterString = shorterString.concat(space);
			shorterString = shorterString.concat(splitSentence[i]);
		}
		shorterString = shorterString.trim();
		
		return shorterString;
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
