package globalClasses;
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

public class CommandComponent {
	public enum COMPONENT_TYPE {
		CATEGORY, DATE, DATE_TYPE, END, LINK, LOCATION, NAME, NONE, NOTE,
		FREQUENCY, PAREN, START, TEXT, TITLE,
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
	
	// both possibilities for Link
	public enum LINK {
		AND, OR;
		
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
	
	private COMPONENT_TYPE type;
	private String contents;
	
	public CommandComponent(COMPONENT_TYPE componentType, 
			String componentContents) throws IllegalArgumentException {
		type = componentType;
		contents = componentContents;
		
		checkValidity();
	}

	public COMPONENT_TYPE getType() {
		return type;
	}
	
	public String getContents() {
		return contents;
	}
	
	private void checkValidity() throws IllegalArgumentException {
		if (type == COMPONENT_TYPE.INVALID) {
			throw new IllegalArgumentException("invalid subcommand type");
		}
		
		switch (type) {
			case FREQUENCY :
				checkFrequencyContents();
				break;
			case DATE_TYPE :
				checkDateTypeContents();
				break;
			case LINK :
				checkLinkContents();
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

	private void checkLinkContents() {
		boolean isValid = false;
		
		if (contents.equals(LINK.AND.toString())) {
			isValid = true;
		} else if (contents.equals(LINK.OR.toString())) {
			isValid = true;
		}
		
		if (!isValid) {
			throw new IllegalArgumentException("invalid link specified");
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
}
