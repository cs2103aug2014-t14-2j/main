package dataManipulation;

import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;

/**
 * 
 * The data associated with a particular command. Note that certain component
 * types must match up with certain command types. (not checked in this class)
 * 
 * Note that for certain command component types, there are some restrictions
 * for component data based on component type, as checked by the program.
 * 
 * NAME is the actual name of the Task. TITLE is only used when editing the
 * task's name.
 * 
 */

//@author A0126720N

public class Subcommand {
	public enum TYPE {
		AND, BYEND, BYNAME, BYSTART, CATEGORY, COMPLETED, DATE, DATE_TYPE, END,
		ENDTIME, FREE, HELP, LOCATION, NAME, NONE, NOTE, FREQUENCY, ON, OR, PAREN, START, 
		STARTTIME, TEXT, TITLE,
		INVALID;
		
		@Override
		public String toString() {
			if (name().equals(INVALID.name())) {
				String nothing = "";
				return nothing;
			} else if (name().equals(ENDTIME.name())) {
				return "to";
			} else if (name().equals(STARTTIME.name())) {
				return "from";
			}
			
			return name().toLowerCase();
		}
	}
	
	// All possibilities for the frequency keyword
	public enum FREQUENCY {
		DAILY, WEEKLY, MONTHLY, ANNUALLY, ONCE;
		
		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
	}
	
	// All keywords for the help type
	public enum HELP_KEY {
		ADD, ALL, EDIT, FINISH, LIST, REDO, REMOVE, REPEAT, SEARCH, TODAY, UNDO, UNFINISH,
		DATE, TIME, COMPLETE, COMPLETED;
		
		@Override
		public String toString() {
			return this.name().toString().toLowerCase();
		}
	}
	
	public enum PAREN {
		OPEN_PAREN, CLOSE_PAREN;
		
		@Override
		public String toString() {
			if (this.name().equals("OPEN_PAREN")) {
				return "(";
			} else {
				return ")";
			}
		}
	}
	
	public enum WEEKDAYS {
		TOMORROW, TODAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, 
		SATURDAY, SUNDAY;
		
		@Override
		public String toString() {
			return this.name().toString().toLowerCase();
		}
	}
	
	// The two possibilities (plus invalid) for the date display/read type
	public enum DATE_TYPE {
		DAY_MONTH, MONTH_DAY;
		
		@Override
		public String toString() {
			switch(this) {
				case DAY_MONTH :
					return "d/m";
				case MONTH_DAY :
					return "m/d";
				default :
					return this.name().toLowerCase();
			}
		}
	}
	
	private TYPE type;
	private String contents;
	
	public Subcommand(TYPE componentType, 
			String componentContents) throws BadSubcommandException, BadSubcommandArgException {
		type = componentType;
		contents = componentContents;
		
		checkValidity();
	}

	public TYPE getType() {
		return type;
	}
	
	public String getContents() {
		return contents;
	}
	
	private void checkValidity() throws BadSubcommandException, BadSubcommandArgException {
		if (type == TYPE.INVALID) {
			throw new BadSubcommandException("invalid subcommand type");
		}
		
		switch (type) {
			case FREQUENCY :
				checkFrequencyContents();
				break;
			case DATE_TYPE :
				checkDateTypeContents();
				break;
			case PAREN :
				checkParenContents();
				break;
			case HELP :
				checkHelpKeyContents();
				break;
			default :
				break;	// no restrictions on contents
		}
		
		return;
	}
	
	private void checkHelpKeyContents() throws BadSubcommandArgException {
		boolean isValid = false;
		
		if (contents.equals(HELP_KEY.ADD.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.ALL.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.EDIT.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.FINISH.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.DATE.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.LIST.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.COMPLETE.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.COMPLETED.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.REDO.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.REMOVE.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.REPEAT.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.SEARCH.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.TIME.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.TODAY.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.UNDO.toString())) {
			isValid = true;
		} else if (contents.equals(HELP_KEY.UNFINISH.toString())) {
			isValid = true;
		}
		
		if (!isValid) {
			throw new BadSubcommandArgException("There is no help feature for the command you have input.");
		}
	}

	private void checkParenContents() throws BadSubcommandArgException {
		boolean isValid = false;
		
		if (contents.equals(PAREN.OPEN_PAREN.toString())) {
			isValid = true;
		} else if (contents.equals(PAREN.CLOSE_PAREN.toString())) {
			isValid = true;
		}
		
		if (!isValid) {
			throw new BadSubcommandArgException("invalid paren specified");
		}
	}

	private void checkFrequencyContents() throws BadSubcommandArgException {
		boolean isValid = false;
		
		if (contents.equals(FREQUENCY.DAILY.toString())) {
			isValid = true;
		} else if (contents.equals(FREQUENCY.WEEKLY.toString())) {
			isValid = true;
		} else if (contents.equals(FREQUENCY.MONTHLY.toString())) {
			isValid = true;
		} else if (contents.equals(FREQUENCY.ANNUALLY.toString())) {
			isValid = true;
		} else if (contents.equals(FREQUENCY.ONCE.toString())) {
			isValid = true;
		}
		
		if (!isValid) {
			throw new BadSubcommandArgException("invalid frequency specified");
		}
	}
	
	private void checkDateTypeContents() throws BadSubcommandArgException {
		boolean isValid = false;
		
		if (contents.equals(DATE_TYPE.DAY_MONTH.toString())) {
			isValid = true;
		} else if (contents.equals(DATE_TYPE.MONTH_DAY.toString())) {
			isValid = true;
		}
		
		if (!isValid) {
			throw new BadSubcommandArgException("invalid date format specified");
		}
	}
	
	@Override
	public String toString() {
		String formattedType = "Component Type: " + type;
		String newLine = "\n";
		String formattedContents = "Contents: " + contents;
		
		String total = formattedType + newLine + formattedContents;
		return total;
	}
	
	public static Subcommand.TYPE determineComponentType(
			String componentTypeString) {
		componentTypeString = componentTypeString.trim();
		
		switch (componentTypeString.toLowerCase()) {
		case ("(") :
			return TYPE.PAREN;
		case (")") :
			return TYPE.PAREN;
		case ("by name") :
			return TYPE.BYNAME;
		case ("by end") :
			return TYPE.BYEND;
		case ("by start") :
			return TYPE.BYSTART;
		case ("and") :
			return TYPE.AND;
		case ("&") :
			return TYPE.AND;
		case ("add") :
			return TYPE.HELP;
		case ("all") :
			return TYPE.HELP;
		case ("annually") :
			return TYPE.FREQUENCY;
		case ("begin") :
			return TYPE.START;
		case ("category") :
			return TYPE.CATEGORY;
		case ("cat") :
			return TYPE.CATEGORY;
		case ("completed") :
			return TYPE.COMPLETED;
		case ("complete") :
			return TYPE.COMPLETED;
		case ("daily") :
			return TYPE.FREQUENCY;
		case ("date") :
			return TYPE.DATE;
		case ("deadline") :
			return TYPE.END;
		case ("edit") :
			return TYPE.HELP;
		case ("end") :
			return TYPE.END;
		case ("finish") :
			return TYPE.HELP;
		case ("free") :
			return TYPE.FREE;
		case ("from") : 
			return TYPE.STARTTIME;
		case ("list") :
			return TYPE.HELP;
		case ("location") :
			return TYPE.LOCATION;
		case ("monthly") :
			return TYPE.FREQUENCY;
		case ("note") :
			return TYPE.NOTE;
		case ("on") : 
			return TYPE.ON;
		case ("or") :
			return TYPE.OR;
		case ("once") :
			return TYPE.FREQUENCY;
		case ("redo") :
			return TYPE.HELP;
		case ("remove") :
			return TYPE.HELP;
		case ("repeat") :
			return TYPE.HELP;
		case ("search") :
			return TYPE.HELP;
		case ("start") :
			return TYPE.START;
		case ("time") :
			return TYPE.HELP;
		case ("title") :
			return TYPE.TITLE;
		case ("to") : 
			return TYPE.ENDTIME;
		case ("today") :
			return TYPE.HELP;
		case ("undo") :
			return TYPE.HELP;
		case ("unfinish") :
			return TYPE.HELP;
		case ("weekly") :
			return TYPE.FREQUENCY;
		default :
			return TYPE.INVALID;
		}
	}
}
