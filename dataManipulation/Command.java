package dataManipulation;

import java.util.List;

/**
 * A class for holding the command in an easy-to-manage way.
 * Note that each command type is associated with specific command component
 * types, as follows:
 *
 * ADD: NAME, CATEGORY, END, LOCATION, NOTE, START
 * 
 * All: NONE
 * 
 * CATEGORY: CATEGORY
 * 
 * CHANGE_DATE_TYPE: DATE_TYPE
 * 
 * COMPLETED: NONE
 * 
 * EDIT: NAME, CATEGORY, END, LOCATION, NOTE, START, TITLE
 * 
 * FINISH: NAME
 * 
 * HELP: NONE
 * 
 * NOTE: NAME
 * 
 * REMOVE: NAME
 * 
 * REPEAT: NAME, FREQUENCY, START, END
 * 
 * SEARCH: 	TEXT, CATEGORY, DATE, END, LOCATION, NOTE, START, TITLE, LINK, PAREN
 * 
 * SORT: START or END
 * 
 * TODAY: NONE
 * 
 * UNDO: NONE
 * 
 * @author Natalie Rawle
 *
 */

public abstract class Command {
	protected String type;
	protected List<Subcommand> components;
	
	public abstract String execute();
	
	public Command(String commandType, 
			List<Subcommand> commandComponents) 
					throws IllegalArgumentException {
		type = commandType;
		components = commandComponents;
		
		checkValidity();
	}
	
	public String getType() {
		return type;
	}
	
	public List<Subcommand> getComponents() {
		return components;
	}
	
	protected abstract void checkValidity();

	protected void checkForNoComponents() throws IllegalArgumentException {
		if (!components.isEmpty()) {
			throw new IllegalArgumentException("too many subcommands");
		}
	}
	
	protected boolean checkForSpecificComponent(Subcommand.TYPE givenType) {
		for (int i = 0; i < components.size(); ++i) {
			Subcommand currentComponent = components.get(i);
			
			if (currentComponent.getType() == givenType) {
				return true;
			}
		}
		
		return false;
	}
	
	protected void checkForComponentAmount(int amount) throws IllegalArgumentException {
		if (components.size() > amount) {
			throw new IllegalArgumentException("too many subcommands");
		} else if (components.size() < amount) {
			throw new IllegalArgumentException("not enough information");
		}
	}
	
	@Override
	public String toString() {
		String formattedType = "Command Type: " + type;
		String newLine = "\n";
		String fullMessage = new String(formattedType + newLine);
		
		for (int i = 0; i < components.size(); ++i) {
			fullMessage = fullMessage + components.get(i).toString() + newLine;
		}
		
		return fullMessage;
	}
}
