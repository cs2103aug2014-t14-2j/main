package userInterface;

public class CommandType {
	private static CommandType records;

	public enum COMMAND_TYPE {
		ADD, ALL, CATEGORY, CHANGE_DATE_TYPE, COMPLETED, EDIT, END, FINISH, 
		HELP, NOTE, REMOVE, REPEAT, SEARCH, SORT, TEXT, TODAY, UNDO,
		INVALID;

		@Override
		public String toString() {
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

	public COMMAND_TYPE determineCommandType(String commandTypeString) {
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

}
