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
<<<<<<< HEAD
		AND, BYEND, BYNAME, BYSTART, CATEGORY, DATE, DATE_TYPE, END, LOCATION, NAME, NONE, NOTE,
		FREQUENCY, OR, PAREN, START, TEXT, TITLE,
		INVALID, COMPLETED;
=======
		AND, BYEND, BYNAME, BYSTART, CATEGORY, DATE, DATE_TYPE, END, ENDTIME, 
		LOCATION, NAME, NONE, NOTE, FREQUENCY, OR, PAREN, START, STARTTIME, 
		TEXT, TITLE,
		INVALID;
>>>>>>> 747a57a5c89899c595acf5a73ced0bd872a7c883
		
		@Override
		public String toString() {
			if (name().equals(INVALID.name())) {
				String nothing = "";
				return nothing;
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
			default :
				break;	// no restrictions on contents
		}
		
		return;
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
		case ("byname") :
			return TYPE.BYNAME;
		case ("byend") :
			return TYPE.BYEND;
		case ("bystart") :
			return TYPE.BYSTART;
		case ("and") :
			return TYPE.AND;
		case ("&") :
			return TYPE.AND;
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
		case ("end") :
			return TYPE.END;
		case ("from") : 
			return TYPE.STARTTIME;
		case ("location") :
			return TYPE.LOCATION;
		case ("monthly") :
			return TYPE.FREQUENCY;
		case ("note") :
			return TYPE.NOTE;
		case ("or") :
			return TYPE.OR;
		case ("once") :
			return TYPE.FREQUENCY;
		case ("start") :
			return TYPE.START;
		case ("title") :
			return TYPE.TITLE;
		case ("to") : 
			return TYPE.ENDTIME;
		case ("weekly") :
			return TYPE.FREQUENCY;
		default :
			return TYPE.INVALID;
		}
	}
}
