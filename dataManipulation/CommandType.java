package dataManipulation;

//@author A0126720N

import java.util.ArrayList;
import java.util.List;

import dataManipulation.Subcommand.TYPE;

import dataEncapsulation.BadCommandException;

public class CommandType {
	private static CommandType records;

	public enum COMMAND_TYPE {
		ADD, ALL, CHANGE_DATE_TYPE, COMPLETED, EDIT, FINISH, 
		HELP, OVERDUE, REDO, REMOVE, REPEAT, SEARCH, SORT, TODAY, UNFINISH,
		UNDO,
		INVALID;

		@Override
		public String toString() {
			if (name().equals("INVALID")) {
				String nothing = "";
				return nothing;
			} else if (name().contains("_")) {
				return name().replace("_", " ").toLowerCase();
			}
			
			return this.name().toLowerCase();
		}
	}

	private CommandType() {}

	public static CommandType getInstance() {
		if (records == null) {
			records = new CommandType();
		}
		return records;
	}

	public static COMMAND_TYPE determineCommandType(String commandTypeString) 
			throws BadCommandException {
		assert(commandTypeString != null);
		commandTypeString = commandTypeString.trim();
		String lowerCaseCommand = commandTypeString.toLowerCase();

		switch (lowerCaseCommand) {
		case "add" :
			return COMMAND_TYPE.ADD;
		case "all" :
			return COMMAND_TYPE.ALL;
		case "completed" :
			return COMMAND_TYPE.COMPLETED;
		case "delete" :
			return COMMAND_TYPE.REMOVE;
		case "edit" :
			return COMMAND_TYPE.EDIT;
		case "filter" :
			return COMMAND_TYPE.SEARCH;
		case "finish" :
			return COMMAND_TYPE.FINISH;
		case "help" :
			return COMMAND_TYPE.HELP;
		case "overdue" :
			return COMMAND_TYPE.OVERDUE;
		case "redo" :
			return COMMAND_TYPE.REDO;
		case "remove" :
			return COMMAND_TYPE.REMOVE;
		case "repeat" :
			return COMMAND_TYPE.REPEAT;
		case "search" :
			return COMMAND_TYPE.SEARCH;
		case "sort" :
			return COMMAND_TYPE.SORT;
		case "today" :
			return COMMAND_TYPE.TODAY;
		case "unfinish" :
			return COMMAND_TYPE.UNFINISH;
		case "undo" :
			return COMMAND_TYPE.UNDO;
		case "view" :
			return COMMAND_TYPE.SEARCH;
		default :
			throw new BadCommandException("unrecognized command type");
		}
	}

	public boolean isSubcommand(COMMAND_TYPE commandType, Subcommand.TYPE subcommandType) {
		switch (commandType) {
		case ADD :
			return isAddType(subcommandType);
		case CHANGE_DATE_TYPE :
			return isChangeDateTypeType(subcommandType);
		case EDIT :
			return isEditType(subcommandType);
		case FINISH :
			return isFinishType(subcommandType);
		case REMOVE :
			return isRemoveType(subcommandType);
		case REPEAT :
			return isRepeatType(subcommandType);
		case SEARCH :
			return isSearchType(subcommandType);
		case SORT :
			return isSortType(subcommandType);
		case UNFINISH :
			return isUnfinishType(subcommandType);
		default :
			return false;
		}
	}

	/**
	 * This method returns the name of all subcommand types
	 * as they are visible to the user. That is, instead of returning
	 * Subcommand.TYPE.FREQUENCY, it will return a list of the strings
	 * once, weekly, monthly, yearly, and so on.
	 * @param commandType
	 * @return
	 */
	public List<String> getSubcommands(COMMAND_TYPE commandType) {
		switch (commandType) {
		case ADD :
			return getAddSubcommands();
		case CHANGE_DATE_TYPE :
			return getChangeDateTypeSubcommands();
		case EDIT :
			return getEditSubcommands();
		case FINISH :
			return getFinishSubcommands();
		case REMOVE :
			return getRemoveSubcommands();
		case REPEAT :
			return getRepeatSubcommands();
		case SEARCH :
			return getSearchSubcommands();
		case SORT :
			return getSortSubcommands();
		default :
			return new ArrayList<String>();
		}
	}
	
	private List<String> getAddSubcommands() {
		List<String> list = new ArrayList<String>();
		list.add(Subcommand.TYPE.DATE.toString());
		list.add(Subcommand.TYPE.CATEGORY.toString());
		list.add(Subcommand.TYPE.END.toString());
		list.add(Subcommand.TYPE.LOCATION.toString());
		list.add(Subcommand.TYPE.NOTE.toString());
		list.add(Subcommand.TYPE.START.toString());
		list.add(Subcommand.TYPE.STARTTIME.toString());
		list.add(Subcommand.TYPE.ENDTIME.toString());
		
		return list;
	}

	private List<String> getChangeDateTypeSubcommands() {
		List<String> list = new ArrayList<String>();
		for (Subcommand.DATE_TYPE type : Subcommand.DATE_TYPE.values()) {
			list.add(type.toString());
		}
		
		return list;
	}

	private List<String> getEditSubcommands() {
		List<String> list = getAddSubcommands();
		list.add(Subcommand.TYPE.TITLE.toString());
		
		return list;
	}

	private List<String> getFinishSubcommands() {
		return getAddSubcommands();
	}

	private List<String> getRemoveSubcommands() {
		return getAddSubcommands();
	}

	private List<String> getRepeatSubcommands() {
		List<String> list = new ArrayList<String>();
		
		for (Subcommand.FREQUENCY type : Subcommand.FREQUENCY.values()) {
			list.add(type.toString());
		}
		
		list.add(Subcommand.TYPE.NAME.toString());
		list.add(Subcommand.TYPE.START.toString());
		list.add(Subcommand.TYPE.END.toString());
		
		return list;
	}

	private List<String> getSearchSubcommands() {
		List<String> list = getEditSubcommands();
		list.add(Subcommand.TYPE.AND.toString());
		list.add(Subcommand.TYPE.OR.toString());
		list.add(Subcommand.TYPE.COMPLETED.toString());
		
		return list;
	}

	private List<String> getSortSubcommands() {
		return new ArrayList<String>();
	}

	private boolean isAddType(TYPE subcommandType) {
		switch (subcommandType) {
			case NAME :
				return true;
			case CATEGORY :
				return true;
			case DATE :
				return true;
			case END :
				return true;
			case LOCATION :
				return true;
			case NOTE :
				return true;
			case START :
				return true;
			case STARTTIME :
				return true;
			case ENDTIME :
				return true;
			default :
				return false;
		}
	}

	private boolean isChangeDateTypeType(TYPE subcommandType) {
		switch (subcommandType) {
		case DATE_TYPE :
			return true;
		default :
			return false;
		}
	}

	private boolean isEditType(TYPE subcommandType) {
		boolean isEditType = isAddType(subcommandType);
		switch (subcommandType) {
		case TITLE :
			return true;
		default :
			return isEditType;
		}
	}

	private boolean isFinishType(TYPE subcommandType) {
		return isAddType(subcommandType);
	}

	private boolean isRemoveType(TYPE subcommandType) {
		return isAddType(subcommandType);
	}

	private boolean isRepeatType(TYPE subcommandType) {
		switch (subcommandType) {
			case FREQUENCY :	 // valid
				return true;
			case NAME :
				return true;
			case START :
				return true;
			case END :
				return true;
			default :
				return false;
		}
	}

	private boolean isSearchType(TYPE subcommandType) {
		boolean isSearchType = isEditType(subcommandType);
		switch (subcommandType) {
			case AND :
				return true;
			case COMPLETED :
				return true;
			case OR :
				return true;
			case TEXT:
				return true;
			default :
				return isSearchType;
		}
	}

	private boolean isSortType(TYPE subcommandType) {
		switch (subcommandType) {
			case BYNAME :
				return true;
			case BYSTART :
				return true;
			case BYEND :
				return true;
			default :
				return false;
		}
	}

	private boolean isUnfinishType(TYPE subcommandType) {
		return isAddType(subcommandType);
	}
}
