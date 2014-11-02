package userInterface;

import dataManipulation.Subcommand;
import dataManipulation.Subcommand.TYPE;

public class CommandType {
	private static CommandType records;

	public enum COMMAND_TYPE {
		ADD, ALL, CATEGORY, CHANGE_DATE_TYPE, COMPLETED, EDIT, FINISH, 
		HELP, NOTE, REMOVE, REPEAT, SEARCH, SORT, TODAY, UNDO,
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

	public static COMMAND_TYPE determineCommandType(String commandTypeString) {
		assert(commandTypeString != null);
		commandTypeString = commandTypeString.trim();
		String lowerCaseCommand = commandTypeString.toLowerCase();

		switch (lowerCaseCommand) {
		case "add" :
			return COMMAND_TYPE.ADD;
		case "all" :
			return COMMAND_TYPE.ALL;
		case "category" :
			return COMMAND_TYPE.CATEGORY;
		case "cat" :
			return COMMAND_TYPE.CATEGORY;
		case "categories" :
			return COMMAND_TYPE.CATEGORY;
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
		case "notes" :
			return COMMAND_TYPE.NOTE;
		case "note" :
			return COMMAND_TYPE.NOTE;
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
		case "undo" :
			return COMMAND_TYPE.UNDO;
		case "view" :
			return COMMAND_TYPE.SEARCH;
		default :
			throw new IllegalArgumentException("unrecognized command type");
		}
	}

	public boolean isSubcommand(COMMAND_TYPE commandType, Subcommand.TYPE subcommandType) {
		switch (commandType) {
		case ADD :
			return isAddType(subcommandType);
		case ALL :
			return isAllType(subcommandType);
		case CATEGORY :
			return isCategoryType(subcommandType);
		case CHANGE_DATE_TYPE :
			return isChangeDateTypeType(subcommandType);
		case COMPLETED :
			return isCompletedType(subcommandType);
		case EDIT :
			return isEditType(subcommandType);
		case FINISH :
			return isFinishType(subcommandType);
		case HELP :
			return isHelpType(subcommandType);
		case NOTE :
			return isNoteType(subcommandType);
		case REMOVE :
			return isRemoveType(subcommandType);
		case REPEAT :
			return isRepeatType(subcommandType);
		case SEARCH :
			return isSearchType(subcommandType);
		case SORT :
			return isSortType(subcommandType);
		case TODAY :
			return isTodayType(subcommandType);
		case UNDO :
			return isUndoType(subcommandType);
		default :
			return false;
		}
	}

	private boolean isAddType(TYPE subcommandType) {
		switch (subcommandType) {
			case NAME :
				return true;
			case CATEGORY :
				return true;
			case END :
				return true;
			case LOCATION :
				return true;
			case NOTE :
				return true;
			case START :
				return true;
			default :
				return false;
		}
	}

	private boolean isAllType(TYPE subcommandType) {
		return false;
	}

	private boolean isCategoryType(TYPE subcommandType) {
		switch (subcommandType) {
			case CATEGORY :
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

	private boolean isCompletedType(TYPE subcommandType) {
		return false;
	}

	private boolean isEditType(TYPE subcommandType) {
		switch (subcommandType) {
		case NAME :
			return true;
		case CATEGORY :
			return true;
		case END :
			return true;
		case LOCATION :
			return true;
		case NOTE :
			return true;
		case START :
			return true;
		case TITLE :
			return true;
		default :
			return false;
		}
	}

	private boolean isFinishType(TYPE subcommandType) {
		return isAddType(subcommandType);
	}

	private boolean isHelpType(TYPE subcommandType) {
		return false;
	}

	private boolean isNoteType(TYPE subcommandType) {
		// TODO Auto-generated method stub
		return false;
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
		switch (subcommandType) {
			case AND :
				return true;
			case CATEGORY :
				return true;
			case DATE :
				return true;
			case END :
				return true;
			case LOCATION :
				return true;
			case NAME :
				return true;
			case NOTE :
				return true;
			case OR :
				return true;
			case PAREN :
				return true;
			case START :
				return true;
			case TEXT:
				return true;
			case TITLE :
				return true;
			default :
				return false;
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

	private boolean isTodayType(TYPE subcommandType) {
		return false;
	}

	private boolean isUndoType(TYPE subcommandType) {
		return false;
	}
}
