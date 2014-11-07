package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataManipulation.CommandType.COMMAND_TYPE;

/**
 * A class for holding the command in an easy-to-manage way.
 * Note that each command type is associated with specific command component
 *
 */

//@author A0126720N

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
		System.out.println("SC SIZE: " + subcommands.size());
		System.out.println("AMT: " + amount);
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
