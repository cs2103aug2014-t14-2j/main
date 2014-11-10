package userInterface;

/**
 * Autocomplete takes any command string and returns a list of possible
 * rewrites of that string.
 * 
 */

//@author A0126720N

import java.util.ArrayList;
import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.SuffixTree;
import dataManipulation.CommandType;
import dataManipulation.CommandType.COMMAND_TYPE;
import dataManipulation.Subcommand;
import dataManipulation.Subcommand.TYPE;

public class Autocomplete {
	private SuffixTree commands = new SuffixTree();
	private List<SuffixTree> subcommands = new ArrayList<SuffixTree>();
	private SuffixTree titles = new SuffixTree();
	private SuffixTree categories = new SuffixTree();
	private SuffixTree locations = new SuffixTree();
	private SuffixTree days = new SuffixTree();
	
	private static Autocomplete autoComp;
	
	public static Autocomplete getInstance() {
		if (autoComp == null) {
			autoComp = new Autocomplete();
		}
		
		return autoComp;
	}
	
	public void addTitle(String newTitle) {
		titles.add(newTitle);
	}
	
	public void addCategory(String newCat) {
		categories.add(newCat);
	}
	
	public void addLocation(String newLoc) {
		locations.add(newLoc);
	}
	
	public void removeTitle(String oldTitle) {
		titles.remove(oldTitle);
	}
	
	public void removeCategory(String oldCategory) {
		categories.remove(oldCategory);
	}
	
	public void removeLocation(String oldLocation) {
		locations.remove(oldLocation);
	}
	
	public List<String> complete(String toComplete) {
		if (!hasCommand(toComplete)) {
			return getMatch(commands, toComplete);
		} if (isAwaitingArg(toComplete)) {
			 return getSubcommandArgMatches(toComplete);
		}
		
		return getSubcommandMatches(toComplete);
	}

	private List<String> getSubcommandArgMatches(String toComplete) {
		String quote = getLastQuote(toComplete);
		String lastWordRemoved = removeLastWord(toComplete, quote);
		Subcommand.TYPE subcommandType = getSubcommandType(lastWordRemoved);
		SuffixTree tree = getSubcommandArgTree(subcommandType);
		
		List<String> lastWordReplacements = getMatch(tree, quote);
		
		boolean needsQuotes = true;
		
		if (tree.equals(days)) {
			needsQuotes = false;
		}
		List<String> formattedOutput = formatArgOutput(lastWordReplacements, 
				lastWordRemoved, needsQuotes);
		
		return formattedOutput;
	}

	private String removeLastWord(String toComplete, String quote) {
		String lastWordRemoved = toComplete;
		boolean quoteNotStarted = quote.isEmpty() && !toComplete.endsWith("\"");
		boolean quoteWasStarted = !quoteNotStarted;
		if (quoteWasStarted) {
			lastWordRemoved = removeLastMatch("\"" + quote, toComplete);
		}
		return lastWordRemoved;
	}

	private List<String> formatArgOutput(List<String> lastWordReplacements,
			String lastWordRemoved, boolean needsQuotes) {
		List<String> formattedTotal = new ArrayList<String>();
		String quote = "\"";
		if (!needsQuotes) {
			quote = "";
		}
		
		for (int i = 0; i < lastWordReplacements.size(); ++i) {
			String suffix = lastWordReplacements.get(i);
			String formattedString;
			
			if (suffix.isEmpty()) {
				formattedString = lastWordRemoved;
			} else {
				formattedString = lastWordRemoved + quote + suffix + quote;
			}

			formattedTotal.add(formattedString);
		}
		
		return formattedTotal;
	}

	private TYPE getSubcommandType(String sentence) {
		Subcommand.TYPE type = getLastWordSubcommandType(sentence);
		
		// HELP matches the names of command types, so word is either "Help" 
		// or a command key work
		if (type == Subcommand.TYPE.HELP) {
			String lastWord = getLastWord(sentence.trim());
			type = checkForNameType(lastWord, sentence);
		}
		
		return type;
	}

	private Subcommand.TYPE checkForNameType(String lastWord, 
			String sentence) {
		String nothing = "";
		String withoutLast = sentence.replaceFirst(lastWord, nothing);
		String firstWord = getFirstWord(sentence);
		withoutLast = withoutLast.trim();
		
		if (withoutLast.equals(firstWord) && !withoutLast.isEmpty()) {
			return Subcommand.TYPE.HELP;
		}
		
		if (!withoutLast.isEmpty()) {
			return Subcommand.TYPE.HELP;
		}
		
		COMMAND_TYPE commandType = getCommandType(sentence);
		if (isNameType(commandType)) {
			return Subcommand.TYPE.NAME;
		} else {
			return Subcommand.TYPE.HELP;
		}
	}

	private boolean isNameType(COMMAND_TYPE commandType) {
		switch (commandType) {
		case ADD :
			return true;
		case EDIT :
			return true;
		case FINISH :
			return true;
		case REMOVE :
			return true;
		case REPEAT :
			return true;
		default :
			return false;	// no match
		}
	}

	private SuffixTree getSubcommandArgTree(TYPE subcommandType) {
		switch (subcommandType) {
			case NAME :
				return titles;
			case TITLE :
				return titles;
			case LOCATION :
				return locations;
			case CATEGORY :
				return categories;
			case DATE :
				return days;
			case START :
				return days;
			case END :
				return days;
			default :
				return new SuffixTree();	// no match
		}
	}

	private String getLastQuote(String sentence) {
		String quote = "\"";
		
		boolean hasStartedQuote = checkHasStartedQuote(sentence);
		if (sentence.endsWith(quote) || !hasStartedQuote) {
			String nothing = "";
			return nothing;
		}
		
		String[] split = sentence.split(quote);
		int lastIndex = split.length - 1;
		return split[lastIndex];
	}

	private boolean checkHasStartedQuote(String sentence) {
		int numberQuotes = getNumberOfOccurrences("\"", sentence);
		if (isEven(numberQuotes)) {
			return false;
		} else {
			return true;	// one unmatched quote
		}
	}

	private boolean isAwaitingArg(String toComplete) {
		int numberQuotes = getNumberOfOccurrences("\"", toComplete);
		if (isEven(numberQuotes)) {
			boolean isLastWordASubcommand = isLastWordSubcommand(toComplete);	// all started quotes end
			if (!isLastWordASubcommand) {
				return false;
			} else {
				return doesLastSubcommandNeedArg(toComplete);
			}
		} else {
			return true;	// one unmatched quote
		}
	}

	private boolean doesLastSubcommandNeedArg(String sentence) {
		Subcommand.TYPE type = getLastWordSubcommandType(sentence);
		switch (type) {
			case FREE :
				return false;
			case FREQUENCY :
				return false;
			case AND :
				return false;
			case OR :
				return false;
			default :
				return true;
		}
	}

	private Subcommand.TYPE getLastWordSubcommandType(String sentence) {
		sentence = sentence.trim();
		String lastWord = getLastWord(sentence);
		Subcommand.TYPE type = Subcommand.determineComponentType(lastWord);
		return type;
	}

	private boolean isLastWordSubcommand(String sentence) {
		sentence = sentence.trim();
		String lastWord = getLastWord(sentence);
		Subcommand.TYPE type = Subcommand.determineComponentType(lastWord);
		if (type != Subcommand.TYPE.INVALID && type != Subcommand.TYPE.HELP) {
			return true;
		} else if (checkForNameType(lastWord, sentence) == Subcommand.TYPE.NAME) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isEven(int number) {
		if (number%2 == 0) {
			return true;
		} else {
			return false;
		}
	}

	private int getNumberOfOccurrences(String splitter, String sentence) {
		int count = 0;
		
		if (sentence.endsWith(splitter)) {
			++count;
		}
		
		if (sentence.startsWith(splitter)) {
			++count;
		}
		
		String[] split = sentence.split(splitter);
		
		count = count + split.length - 1;
		
		return count;
	}

	private List<String> getSubcommandMatches(String toComplete) {
		COMMAND_TYPE commandType = getCommandType(toComplete);
		SuffixTree tree = getSubcommandTree(commandType);
		String lastWord = getLastWord(toComplete);
		List<String> lastWordReplacements = getMatch(tree, lastWord);
		String lastWordRemoved = removeLastMatch(lastWord, toComplete);
		List<String> formattedOutput = formatOutput(lastWordReplacements, 
				lastWordRemoved);
		
		return formattedOutput;
	}

	private COMMAND_TYPE getCommandType(String toComplete) {
		if (toComplete.trim().startsWith(COMMAND_TYPE.CHANGE_DATE_TYPE.toString())) {
			return COMMAND_TYPE.CHANGE_DATE_TYPE;
		}
		
		String commandString = getFirstWord(toComplete);
		
		try {
			return CommandType.determineCommandType(commandString);
		} catch (BadCommandException e) {
			return COMMAND_TYPE.INVALID;
		}
	}

	private String getFirstWord(String sentence) {
		String space = " ";
		String[] splitString = sentence.split(space);
		
		return splitString[0];
	}

	private SuffixTree getSubcommandTree(COMMAND_TYPE commandType) {
		int index = 0;
		
		for (COMMAND_TYPE type : COMMAND_TYPE.values()) {
			if (commandType.equals(type)) {
				return subcommands.get(index);
			}
			
			++index;
		}
		
		return new SuffixTree();
	}

	private String getLastWord(String sentence) {
		String space = " ";
		
		if (sentence.endsWith(space)) {
			return "";	// no last word
		}
		
		String[] splitString = sentence.split(space);
		int lastIndex = splitString.length - 1;
		
		return splitString[lastIndex];
	}

	private String removeLastMatch(String lastWord, String toTruncate) {
		int removedSize = lastWord.length();
		int lastIndex = toTruncate.length() - removedSize;
		return toTruncate.substring(0, lastIndex);
	}

	private List<String> formatOutput(List<String> lastWordReplacements, 
			String toComplete) {
		List<String> formattedTotal = new ArrayList<String>();
		
		for (int i = 0; i < lastWordReplacements.size(); ++i) {
			String suffix = lastWordReplacements.get(i);
			String formattedString = toComplete + suffix;
			formattedTotal.add(formattedString);
		}
		
		return formattedTotal;
	}

	private List<String> getMatch(SuffixTree findFrom, String toComplete) {
		List<String> matches = findFrom.getMatchList(toComplete);
		if (matches.isEmpty()) {
			matches.add(toComplete);
		}
		
		return matches;
	}

	private boolean hasCommand(String toComplete) {
		String space = " ";
		
		if (toComplete.contains(space)) {
			return true;
		} else {
			return false;
		}
	}
	
	private Autocomplete() {
		initializeCommands();
		initializeSubcommands();
		initializeDays();
	}
	
	private void initializeDays() {
		for (Subcommand.WEEKDAYS day : Subcommand.WEEKDAYS.values()) {
			days.add(day.toString());
		}
	}

	private void initializeSubcommands() {
		CommandType records = CommandType.getInstance();
		
		int index = 0;
		
		for (COMMAND_TYPE type : COMMAND_TYPE.values()) {
			List<String> list = records.getSubcommands(type);
			SuffixTree tree = subcommands.get(index);
			
			for (int i = 0; i < list.size(); ++i) {
				tree.add(list.get(i));
			}
			
			++index;
		}
	}

	private void initializeCommands() {
		for (COMMAND_TYPE type : COMMAND_TYPE.values()) {
			commands.add(type.toString());
			subcommands.add(new SuffixTree());
		}
	}
}
