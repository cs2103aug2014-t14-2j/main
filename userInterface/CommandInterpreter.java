package userInterface;

import java.util.ArrayList;
import java.util.List;

import userInterface.CommandType.COMMAND_TYPE;
import dataManipulation.Add;
import dataManipulation.All;
import dataManipulation.Category;
import dataManipulation.ChangeDateType;
import dataManipulation.Command;
import dataManipulation.Completed;
import dataManipulation.Edit;
import dataManipulation.Finish;
import dataManipulation.Help;
import dataManipulation.Note;
import dataManipulation.Remove;
import dataManipulation.Repeat;
import dataManipulation.Search;
import dataManipulation.Sort;
import dataManipulation.Subcommand;
import dataManipulation.Today;
import dataManipulation.Undo;

public class CommandInterpreter {
	private static CommandInterpreter commandInterpreter;
	private CommandType commands;

	private CommandInterpreter() {
		commands = CommandType.getInstance();
	}

	public static CommandInterpreter getInstance() {
		if (commandInterpreter == null) {
			commandInterpreter = new CommandInterpreter();
		}
		return commandInterpreter;
	}

	public Command formCommand(String input) throws IllegalArgumentException {
		assert (input != null);
		if (input.isEmpty()) {
			throw new IllegalArgumentException("no command given");
		}

		if (isSpecialCommand(input)) {
			Command command = determineSpecialCommand(input);
			return command;
		}

		String commandTypeString = getFirstWord(input);
		COMMAND_TYPE commandType = commands
				.determineCommandType(commandTypeString);
		Command command;

		List<Subcommand> components;

		if (isNoComponentCommand(commandType)) {
			components = getNoSubcommands(input);
		} else {
			input = removeFirstWord(input);
			components = getComponents(input, commandType);
		}

		command = makeCommand(commandType, components);

		return command;
	}

	// -----------------------------------------------------------------------------
	// ---------------- Command Related Methods
	// ------------------------------------
	// -----------------------------------------------------------------------------

	private Command makeCommand(COMMAND_TYPE type, List<Subcommand> subcommands) {
		switch (type) {
		case ADD:
			return new Add(subcommands);
		case ALL:
			return new All(subcommands);
		case CATEGORY:
			return new Category(subcommands);
		case CHANGE_DATE_TYPE:
			return new ChangeDateType(subcommands);
		case COMPLETED:
			return new Completed(subcommands);
		case EDIT:
			return new Edit(subcommands);
		case FINISH:
			return new Finish(subcommands);
		case HELP:
			return new Help(subcommands);
		case NOTE:
			return new Note(subcommands);
		case REMOVE:
			return new Remove(subcommands);
		case REPEAT:
			return new Repeat(subcommands);
		case SEARCH:
			return new Search(subcommands);
		case SORT:
			return new Sort(subcommands);
		case TODAY:
			return new Today(subcommands);
		case UNDO:
			return new Undo(subcommands);
		default:
			throw new IllegalArgumentException("invalid command type");
		}
	}

	private boolean isSpecialCommand(String input) {
		input = input.toLowerCase();

		String[] splitInput = splitString(input);

		if (isChangeDateType(splitInput)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isChangeDateType(String[] splitInput) {
		String[] changeDateTypeExample = makeChangeDateTypeExample();

		if (splitInput.length != changeDateTypeExample.length) {
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

		if (!usersOption.equals(firstOption)
				&& !usersOption.equals(secondOption)) {
			return false;
		}

		return true;
	}

	private String[] makeChangeDateTypeExample() {
		int length = 4; // "change" "date" "type" "D/M", etc
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
	private Command determineSpecialCommand(String input) {
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

	// -----------------------------------------------------------------------------
	// ------------------ Subcommand Related Methods
	// -------------------------------
	// -----------------------------------------------------------------------------

	private List<Subcommand> getComponents(String string,
			COMMAND_TYPE commandType) {
		assert (string != null);
		assert (!string.isEmpty());

		List<Subcommand> components = new ArrayList<Subcommand>();

		Subcommand component;
		// Search and Sort must label their first subcommands
		if (commandType != COMMAND_TYPE.SEARCH
				&& commandType != COMMAND_TYPE.SORT) {
			component = getFirstComponent(commandType, string);
			if (isQuotationComponent(component)) {
				string = eraseQuoteComponent(string);
			} else {
				string = eraseNoQuoteComponent(component, string);
			}
			components.add(component);
		}
		if (commandType == COMMAND_TYPE.SORT) {
			Subcommand.TYPE sortType = Subcommand.determineComponentType(string
					.replaceAll("\\s+", "").toLowerCase());
			components.add(new Subcommand(sortType, ""));
		} else {
			while (string.length() > 0) {
				component = getNextComponent(string);
				if (isQuotationComponent(component)) {
					string = eraseQuoteComponent(string);
				} else {
					string = eraseNoQuoteComponent(component, string);
				}
				components.add(component);
			}
		}

		return components;
	}

	private List<Subcommand> getNoSubcommands(String input) {
		assert (input != null);

		try {
			input = removeFirstWord(input); // will throw if there's no input
		} catch (IllegalArgumentException e) {
			input = ""; // ok - just means that the command was the last one
		}

		return createNoComponent(input);
	}

	private static List<Subcommand> createNoComponent(String string) {
		assert (string != null);

		string = string.trim();

		if (!string.isEmpty()) {
			throw new IllegalArgumentException("too many arguments");
		}

		List<Subcommand> componentList = new ArrayList<Subcommand>();

		return componentList;
	}

	private boolean isNoComponentCommand(COMMAND_TYPE commandType) {
		switch (commandType) {
		case ALL:
			return true;
		case COMPLETED:
			return true;
		case HELP:
			return true;
		case TODAY:
			return true;
		case UNDO:
			return true;
		default:
			return false;
		}
	}

	/**
	 * The first component should be directly after the command type Will be
	 * surrounded by quotations if formatted correctly
	 * 
	 * Assumes that the command type has already been removed from the string
	 * 
	 * @param string
	 * @return
	 */
	private Subcommand getFirstComponent(COMMAND_TYPE commandType, String string) {
		string = string.trim(); // remove whitespace

		Subcommand.TYPE componentType = determineFirstComponentType(commandType);

		String componentData = getComponentData(string, componentType);

		Subcommand component = new Subcommand(componentType, componentData);
		return component;
	}

	private Subcommand.TYPE determineFirstComponentType(COMMAND_TYPE commandType) {
		switch (commandType) {
		case ADD:
			return Subcommand.TYPE.NAME;
		case CATEGORY:
			return Subcommand.TYPE.CATEGORY;
		case EDIT:
			return Subcommand.TYPE.NAME;
		case FINISH:
			return Subcommand.TYPE.NAME;
		case NOTE:
			return Subcommand.TYPE.NAME;
		case REMOVE:
			return Subcommand.TYPE.NAME;
		case REPEAT:
			return Subcommand.TYPE.NAME;
		default:
			return Subcommand.TYPE.INVALID;
		}
	}

	private Subcommand getNextComponent(String componentSentence) {
		String componentTypeString = getFirstWord(componentSentence);
		Subcommand.TYPE componentType = Subcommand
				.determineComponentType(componentTypeString);

		if (componentType != Subcommand.TYPE.FREQUENCY
				&& componentType != Subcommand.TYPE.AND
				&& componentType != Subcommand.TYPE.OR) {
			componentSentence = removeFirstWord(componentSentence);
		}

		String componentData = getComponentData(componentSentence,
				componentType);

		Subcommand component = new Subcommand(componentType, componentData);
		return component;
	}

	/**
	 * Assumes that the String is not between quotation marks (which would allow
	 * the user to write subcommand keywords)
	 * 
	 * @param componentPart
	 * @return
	 */
	private boolean isSubcommand(String possibleSubcommand) {
		Subcommand.TYPE possibleType = Subcommand
				.determineComponentType(possibleSubcommand);

		if (possibleType == Subcommand.TYPE.INVALID) {
			return false;
		} else {
			return true;
		}
	}

	private String eraseNoQuoteComponent(Subcommand component, String sentence) {
		assert (sentence != null);
		assert (component != null);

		String match = extractComponentMatch(component, sentence);

		String emptyString = "";

		sentence = sentence.replaceFirst(match, emptyString);

		if (sentence.equals(match)) {
			return emptyString; // replaceFirst will not replace the entire
								// string
		}

		sentence = sentence.trim();

		return sentence;
	}

	private String eraseQuoteComponent(String sentence)
			throws IndexOutOfBoundsException {
		assert (sentence != null);
		char quote = '"';

		int indexOfNextQuote = sentence.indexOf(quote);
		if (indexOfNextQuote == -1) {
			throw new IndexOutOfBoundsException("No double quote found");
		}

		String withoutUntilFirstQuote = sentence
				.substring(indexOfNextQuote + 1);

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

		String withoutUntilSecondQuote = withoutUntilFirstQuote
				.substring(indexOfNextQuote + 1);

		withoutUntilSecondQuote = withoutUntilSecondQuote.trim();

		return withoutUntilSecondQuote;
	}

	private boolean isQuotationComponent(Subcommand component) {
		Subcommand.TYPE type = component.getType();

		switch (type) {
		case CATEGORY:
			return true;
		case LOCATION:
			return true;
		case NAME:
			return true;
		case NOTE:
			return true;
		case TITLE:
			return true;
		default:
			return false;
		}
	}

	// -----------------------------------------------------------------------------
	// ------------------ Subcommand Data Related Methods
	// --------------------------
	// -----------------------------------------------------------------------------

	private String getComponentData(String userCommand,
			Subcommand.TYPE componentType) {
		if (componentType == Subcommand.TYPE.START
				|| componentType == Subcommand.TYPE.END
				|| componentType == Subcommand.TYPE.DATE
				|| componentType == Subcommand.TYPE.FREQUENCY
				|| componentType == Subcommand.TYPE.AND
				|| componentType == Subcommand.TYPE.OR) {
			return getNoQuoteComponentData(userCommand);
		}

		userCommand = userCommand.trim();
		return getBetweenQuoteData(userCommand);
	}

	private String getNoQuoteComponentData(String userCommand) {
		assert (!userCommand.isEmpty());

		String[] splitCommand = splitString(userCommand);
		String removedWhitespace = " ";

		String wholeData = splitCommand[0];
		String dataPart = new String();
		for (int i = 1; (i < splitCommand.length) && (!isSubcommand(dataPart)); ++i) {
			dataPart = splitCommand[i];
			if (!isSubcommand(dataPart)) {
				wholeData = wholeData + removedWhitespace + dataPart;
			}
		}
		wholeData = wholeData.trim();

		return wholeData;
	}

	private String getBetweenQuoteData(String userCommand)
			throws IllegalArgumentException {
		String emptyString = "";
		String doubleQuoteMark = "\"";

		userCommand = userCommand.trim();

		checkFirstQuotation(userCommand); // first character should be a
											// quotation
		userCommand = userCommand.replaceFirst(doubleQuoteMark, emptyString);
		String commandData = getUntilQuotation(userCommand);

		return commandData;
	}

	private void checkFirstQuotation(String userCommand) {
		String doubleQuote = "\"";
		if (!userCommand.startsWith(doubleQuote)) {
			throw new IllegalArgumentException(
					"invalid subcommand argument: double quotes");
		}
	}

	private String getUntilQuotation(String userCommand) {
		assert (userCommand != null);
		if (userCommand.isEmpty()) {
			throw new IllegalArgumentException(
					"invalid subcommand argument: double quotes");
		}

		String fullString = userCommand.substring(0, 1);
		String singleChar = new String();
		String doubleQuote = "\"";

		for (int i = 1; (i != userCommand.length())
				&& (!singleChar.equals(doubleQuote)); ++i) {
			fullString = fullString + singleChar;
			singleChar = userCommand.substring(i, i + 1);
		}

		// check to see if it ever saw another quotation mark
		if (!singleChar.equals(doubleQuote)) {
			throw new IllegalArgumentException(
					"invalid subcommand argument: double quotes");
		}

		return fullString;
	}

	private String extractComponentMatch(Subcommand component, String sentence) {
		String[] splitSentence = splitString(sentence);
		String matchingString = component.getContents();
		String growingMatch = splitSentence[0];
		String space = " ";

		assert (sentence.contains(matchingString));

		for (int i = 1; ((!growingMatch.contains(matchingString)) && (i < splitSentence.length)); ++i) {
			growingMatch = growingMatch.concat(space);
			growingMatch = growingMatch.concat(splitSentence[i]);
		}
		growingMatch = growingMatch.trim();

		return growingMatch;
	}

	// -----------------------------------------------------------------------------
	// ------------------ General Methods
	// ------------------------------------------
	// -----------------------------------------------------------------------------

	/**
	 * Gets the first word from the string
	 * 
	 * @param sentence
	 *            with length > 0
	 * @return a String of all characters before the first whitespace (or the
	 *         end)
	 */
	private String getFirstWord(String sentence) {
		assert (sentence != null);
		assert (!sentence.isEmpty());

		String[] dividedSentence = splitString(sentence);
		String commandTypeString = dividedSentence[0];

		return commandTypeString;
	}

	/**
	 * Removes the first substring before whitespace, as well as end whitespace
	 * 
	 * @param input
	 * @return
	 */
	private String removeFirstWord(String sentence) {
		assert (sentence != null);

		String[] splitSentence = splitString(sentence);

		int minimumSize = 2; // must be at least 2 words in command;
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
	 * 
	 * @param toSplit
	 * @return
	 */
	private String[] splitString(String toSplit) {
		assert (toSplit != null);
		assert (!toSplit.isEmpty());
		String whiteSpace = "\\s+";

		String noEndWhitespace = toSplit.trim();

		String[] dividedString = noEndWhitespace.split(whiteSpace);

		return dividedString;
	}

}
