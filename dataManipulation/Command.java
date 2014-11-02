package dataManipulation;

import java.util.List;
import userInterface.CommandType;
import userInterface.CommandType.COMMAND_TYPE;

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
	protected COMMAND_TYPE type;
	protected List<Subcommand> subcommands;
	
	public abstract String execute() throws Exception;
	
	public Command(String commandType, 
			List<Subcommand> commandComponents) 
					throws IllegalArgumentException {
		if (commandType == null || commandComponents == null) {
			throw new IllegalArgumentException("null argument for Command constructor");
		}
		
		type = CommandType.determineCommandType(commandType);
		subcommands = commandComponents;
		
		checkValidity();
	}
	
	public COMMAND_TYPE getType() {
		return type;
	}
	
	public List<Subcommand> getComponents() {
		return subcommands;
	}
	
	protected abstract void checkValidity();

	protected void checkForNoComponents() throws IllegalArgumentException {
		if (!subcommands.isEmpty()) {
			throw new IllegalArgumentException("too many subcommands");
		}
	}
	
	protected boolean checkForSpecificComponent(Subcommand.TYPE givenType) {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand currentComponent = subcommands.get(i);
			
			if (currentComponent.getType() == givenType) {
				return true;
			}
		}
		
		return false;
	}
	
	protected void checkForNoDuplicateSubcommands() throws IllegalArgumentException {
		if (subcommands.size() == 0) {
			return;
		}
		
		Subcommand current;
		Subcommand temp;
		
		for (int i = 0; i < subcommands.size() - 1; ++i) {
			current = subcommands.get(i);
			for (int j = i + 1; j < subcommands.size(); ++j) {
				temp = subcommands.get(j);
				if (current.getType().toString().equals(temp.getType().toString())) {
					throw new IllegalArgumentException("duplicate subcommands");
				}
			}
		}
		
		return;
	}
	
	protected void checkForComponentAmount(int amount) throws IllegalArgumentException {
		if (subcommands.size() > amount) {
			throw new IllegalArgumentException("too many subcommands");
		} else if (subcommands.size() < amount) {
			throw new IllegalArgumentException("not enough information");
		}
	}
	
	@Override
	public String toString() {
		String formattedType = "Command Type: " + type;
		String newLine = "\n";
		String fullMessage = new String(formattedType + newLine);
		
		for (int i = 0; i < subcommands.size(); ++i) {
			fullMessage = fullMessage + subcommands.get(i).toString() + newLine;
		}
		
		return fullMessage;
	}
}
