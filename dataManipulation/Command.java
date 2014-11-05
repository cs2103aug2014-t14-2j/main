package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
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
 * SEARCH: 	TEXT, CATEGORY, DATE, END, LOCATION, NOTE, START, TITLE, LINK, PAREN, COMPLETED
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
	
	public Command(COMMAND_TYPE commandType, 
			List<Subcommand> commandComponents) 
					throws BadCommandException, BadSubcommandException {
		if (commandType == null || commandComponents == null) {
			throw new IllegalArgumentException("null argument for Command constructor");
		}
		
		type = commandType;
		subcommands = commandComponents;
		
		checkValidity();
	}
	
	public COMMAND_TYPE getType() {
		return type;
	}
	
	public List<Subcommand> getComponents() {
		return subcommands;
	}
	
	protected void checkValidity() throws BadSubcommandException {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			CommandType checker = CommandType.getInstance();

			if (!checker.isSubcommand(type, component.getType())) {
				throw new BadSubcommandException("invalid subcommand");
			}
		}
	}

	protected void checkForNoComponents() throws BadSubcommandException {
		if (!subcommands.isEmpty()) {
			throw new BadSubcommandException("too many subcommands");
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
	
	protected void checkForNoDuplicateSubcommands() throws BadSubcommandException {
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
					throw new BadSubcommandException("duplicate subcommands");
				}
			}
		}
		
		return;
	}
	
	protected void checkForComponentAmount(int amount) throws BadSubcommandException {
		if (subcommands.size() > amount) {
			throw new BadSubcommandException("too many subcommands");
		} else if (subcommands.size() < amount) {
			throw new BadSubcommandException("not enough information");
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
