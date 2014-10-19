package userInterface;

import java.util.ArrayList;
import java.util.List;

import dataManipulation.Command;
import dataManipulation.Subcommand;

public class CommandInterpreter {
	public static Command formCommand(String input) throws IllegalArgumentException {
		assert(input != null);
		if (input.isEmpty()) {
			throw new IllegalArgumentException("no command given");
		}
		
		if (isSpecialCommand(input)) {
			Command command = determineSpecialCommand(input);
			return command;
		}
		
		String commandTypeString = getFirstWord(input);
		String commandType = determineCommandType(commandTypeString);
		Command command;
		
		if (commandType == String.INVALID) {
			throw new IllegalArgumentException("unrecognized command type");
		} 

		List<Subcommand> components;
		
		if (isNoComponentCommand(commandType)) {
			components = getNoSubcommands(input, commandType);
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

		Subcommand component = new Subcommand(Subcommand.TYPE.DATE_TYPE, 
				splitInput[lastPosition]);
		
		List<Subcommand> list = new ArrayList<Subcommand>();
		list.add(component);
		
		Command command = new ChangeDateType(list);

		return command;
	}
	
	// ADD, ALL, CATEGORY, CHANGE_DATE_TYPE, COMPLETED, EDIT, FILTER, FINISH, 
	// HELP, NOTE, REMOVE, REPEAT, SEARCH, SORT, TODAY, UNDO, VIEW,
	// INVALID
	private static String determineCommandType(String commandTypeString) {
		assert(commandTypeString != null);
		commandTypeString = commandTypeString.trim();
		String lowerCaseCommand = commandTypeString.toLowerCase();
		
		switch (lowerCaseCommand) {
			case "add" :
				return lowerCaseCommand;
			case "all" :
				return lowerCaseCommand;
			case "category" :
				return lowerCaseCommand;
			case "cat" :
				return lowerCaseCommand;
			case "categories" :
				return lowerCaseCommand;
			case "completed" :
				return lowerCaseCommand;
			case "delete" :
				return lowerCaseCommand;
			case "edit" :
				return lowerCaseCommand;
			case "filter" :
				return lowerCaseCommand;
			case "finish" :
				return lowerCaseCommand;
			case "help" :
				return lowerCaseCommand;
			case "notes" :
				return lowerCaseCommand;
			case "note" :
				return lowerCaseCommand;
			case "remove" :
				return lowerCaseCommand;
			case "repeat" :
				return lowerCaseCommand;
			case "search" :
				return lowerCaseCommand;
			case "sort" :
				return lowerCaseCommand;
			case "today" :
				return lowerCaseCommand;
			case "undo" :
				return lowerCaseCommand;
			case "view" :
				return lowerCaseCommand;
			default :
				throw new IllegalArgumentException("bad command type");
		}
	}
	
//-----------------------------------------------------------------------------
//------------------ Subcommand Related Methods -------------------------------
//-----------------------------------------------------------------------------
	
	private static List<Subcommand> getComponents(String string,
			String commandType) {
		assert(string != null);
		assert(!string.isEmpty());

		List<Subcommand> components = new ArrayList<Subcommand>();
		
		Subcommand component;
		// Search and Sort must label their first subcommands
		if (commandType != String.SEARCH && commandType != String.SORT) {
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

	private static List<Subcommand> getNoSubcommands(String input,
			String commandType) {
		assert(input != null);
		
		try {
			input = removeFirstWord(input);
		} catch (IllegalArgumentException e) {
			input = "";
		}
		
		return createNoComponent(input, commandType);
	}
	
	private static List<Subcommand> createNoComponent(String string,
			String commandType) {
		assert(string != null);
		
		string = string.trim();
		
		if (!string.isEmpty()) {
			throw new IllegalArgumentException("too many arguments");
		}
		
		List<Subcommand> componentList = new ArrayList<Subcommand>();
		
		return componentList;
	}

	private static boolean isNoComponentCommand(String commandType) {
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
	private static Subcommand getFirstComponent(String commandType,
			String string) {
		string = string.trim();	// remove whitespace
		
		Subcommand.TYPE componentType = determineFirstComponentType(commandType);
		
		String componentData = getComponentData(string, componentType);
		
		Subcommand component = 
				new Subcommand(componentType, componentData);
		return component;
	}

	private static Subcommand.TYPE determineFirstComponentType(
			String commandType) {
		switch (commandType) {
			case "add" :
				return Subcommand.TYPE.NAME;
			case "category" :
				return Subcommand.TYPE.CATEGORY;
			case "edit" :
				return Subcommand.TYPE.NAME;
			case "finish" :
				return Subcommand.TYPE.NAME;
			case "note" :
				return Subcommand.TYPE.NAME;
			case "remove" :
				return Subcommand.TYPE.NAME;
			case "repeat" :
				return Subcommand.TYPE.NAME;
			default :
				return Subcommand.TYPE.INVALID;
		}
	}
	
	private static Subcommand getNextComponent(String componentSentence) {
		String componentTypeString = getFirstWord(componentSentence);
		Subcommand.TYPE componentType = 
				determineComponentType(componentTypeString);
		
		if (componentType != Subcommand.TYPE.FREQUENCY) {
			componentSentence = removeFirstWord(componentSentence);
		}
		
		String componentData = getComponentData(componentSentence, 
				componentType);
		
		Subcommand component = 
				new Subcommand(componentType, componentData);
		return component;
	}

	private static Subcommand.TYPE determineComponentType(
			String componentTypeString) {
		componentTypeString = componentTypeString.trim();
		
		switch (componentTypeString.toLowerCase()) {
		case ("(") :
			return Subcommand.TYPE.PAREN;
		case (")") :
			return Subcommand.TYPE.PAREN;
		case ("and") :
			return Subcommand.TYPE.LINK;
		case ("&") :
			return Subcommand.TYPE.LINK;
		case ("annually") :
			return Subcommand.TYPE.FREQUENCY;
		case ("begin") :
			return Subcommand.TYPE.START;
		case ("-b") :
			return Subcommand.TYPE.START;
		case ("category") :
			return Subcommand.TYPE.CATEGORY;
		case ("cat") :
			return Subcommand.TYPE.CATEGORY;
		case ("-c") :
			return Subcommand.TYPE.CATEGORY;
		case ("daily") :
			return Subcommand.TYPE.FREQUENCY;
		case ("date") :
			return Subcommand.TYPE.DATE;
		case ("-dt") :
			return Subcommand.TYPE.DATE;
		case ("deadline") :
			return Subcommand.TYPE.END;
		case ("-dl") :
			return Subcommand.TYPE.END;
		case ("end") :
			return Subcommand.TYPE.END;
		case ("-e") :
			return Subcommand.TYPE.END;
		case ("location") :
			return Subcommand.TYPE.LOCATION;
		case ("-l") :
			return Subcommand.TYPE.LOCATION;
		case ("monthly") :
			return Subcommand.TYPE.FREQUENCY;
		case ("note") :
			return Subcommand.TYPE.NOTE;
		case ("-n") :
			return Subcommand.TYPE.NOTE;
		case ("once") :
			return Subcommand.TYPE.FREQUENCY;
		case ("start") :
			return Subcommand.TYPE.START;
		case ("-s") :
			return Subcommand.TYPE.START;
		case ("title") :
			return Subcommand.TYPE.TITLE;
		case ("-t") :
			return Subcommand.TYPE.TITLE;
		case ("weekly") :
			return Subcommand.TYPE.FREQUENCY;
		default :
			return Subcommand.TYPE.INVALID;
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
		Subcommand.TYPE possibleType = 
				determineComponentType(possibleSubcommand);
		
		if (possibleType == Subcommand.TYPE.INVALID) {
			return false;
		} else {
			return true;
		}
	}

	private static String eraseNoQuoteComponent(Subcommand component,
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
	
	private static boolean isQuotationComponent(Subcommand component) {
		Subcommand.TYPE type = component.getType();
		
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
			Subcommand.TYPE componentType) {
		if (componentType == Subcommand.TYPE.START ||
				componentType == Subcommand.TYPE.END ||
				componentType == Subcommand.TYPE.DATE ||
				componentType == Subcommand.TYPE.FREQUENCY) {
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
			Subcommand component, String sentence) {
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
