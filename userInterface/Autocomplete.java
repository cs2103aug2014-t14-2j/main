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
import dataManipulation.Subcommand;
import dataManipulation.CommandType.COMMAND_TYPE;
import dataManipulation.Subcommand.TYPE;

public class Autocomplete {
	private SuffixTree commands = new SuffixTree();
	private List<SuffixTree> subcommands = new ArrayList<SuffixTree>();
	private SuffixTree titles = new SuffixTree();
	private SuffixTree categories = new SuffixTree();
	private SuffixTree locations = new SuffixTree();
	
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
		List<String> formattedOutput = formatArgOutput(lastWordReplacements, 
				lastWordRemoved);
		
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
			String lastWordRemoved) {
		List<String> formattedTotal = new ArrayList<String>();
		String quote = "\"";
		
		for (int i = 0; i < lastWordReplacements.size(); ++i) {
			String suffix = lastWordReplacements.get(i);
			String formattedString = lastWordRemoved + quote + suffix + quote;
			formattedTotal.add(formattedString);
		}
		
		return formattedTotal;
	}

	private TYPE getSubcommandType(String sentence) {
		sentence = sentence.trim();
		String lastWord = getLastWord(sentence);
		Subcommand.TYPE type = Subcommand.determineComponentType(lastWord);
		
		if (type == Subcommand.TYPE.INVALID) {
			type = checkForNameType(lastWord, sentence);
		}
		
		return type;
	}

	private Subcommand.TYPE checkForNameType(String lastWord, 
			String sentence) {
		String emptyString = "";
		String withoutLast = sentence.replaceFirst(lastWord, emptyString);
		withoutLast = withoutLast.trim();
		
		if (!withoutLast.equals(getFirstWord(sentence)) && !withoutLast.isEmpty()) {
			return Subcommand.TYPE.INVALID;
		}
		
		COMMAND_TYPE commandType = getCommandType(sentence);
		if (isNameType(commandType)) {
			return Subcommand.TYPE.NAME;
		} else {
			return Subcommand.TYPE.INVALID;
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
			return isLastWordSubcommand(toComplete);	// all started quotes end
		} else {
			return true;	// one unmatched quote
		}
	}

	private boolean isLastWordSubcommand(String sentence) {
		sentence = sentence.trim();
		String lastWord = getLastWord(sentence);
		if (Subcommand.determineComponentType(lastWord) != Subcommand.TYPE.INVALID) {
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
	
	public static void main(String[] args) {
		Autocomplete.getInstance();
	}
}
