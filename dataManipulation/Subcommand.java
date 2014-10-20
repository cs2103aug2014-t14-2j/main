package dataManipulation;
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
 * @author Natalie Rawle
 * 
 */

public class Subcommand {
	public enum TYPE {
		AND, CATEGORY, DATE, DATE_TYPE, END, LINK, LOCATION, NAME, NONE, NOTE,
		FREQUENCY, OR, PAREN, START, TEXT, TITLE,
		INVALID
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
			String componentContents) throws IllegalArgumentException {
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
	
	private void checkValidity() throws IllegalArgumentException {
		if (type == TYPE.INVALID) {
			throw new IllegalArgumentException("invalid subcommand type");
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
	
	private void checkParenContents() {
		boolean isValid = false;
		
		if (contents.equals(PAREN.OPEN_PAREN.toString())) {
			isValid = true;
		} else if (contents.equals(PAREN.CLOSE_PAREN.toString())) {
			isValid = true;
		}
		
		if (!isValid) {
			throw new IllegalArgumentException("invalid paren specified");
		}
	}

	private void checkFrequencyContents() throws IllegalArgumentException {
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
			throw new IllegalArgumentException("invalid frequency specified");
		}
	}
	
	private void checkDateTypeContents() throws IllegalArgumentException {
		boolean isValid = false;
		
		if (contents.equals(DATE_TYPE.DAY_MONTH.toString())) {
			isValid = true;
		} else if (contents.equals(DATE_TYPE.MONTH_DAY.toString())) {
			isValid = true;
		}
		
		if (!isValid) {
			throw new IllegalArgumentException("invalid date format specified");
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
		case ("daily") :
			return TYPE.FREQUENCY;
		case ("date") :
			return TYPE.DATE;
		case ("deadline") :
			return TYPE.END;
		case ("end") :
			return TYPE.END;
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
		case ("weekly") :
			return TYPE.FREQUENCY;
		default :
			return TYPE.INVALID;
		}
	}
}
