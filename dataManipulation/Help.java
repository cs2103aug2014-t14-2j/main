package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import userInterface.CommandType.COMMAND_TYPE;
import userInterface.ezCMessages;

public class Help extends Command {

	public Help(List<Subcommand> commandComponents) 
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.HELP, commandComponents);
		}

	@Override
	public String execute() {
		return ezCMessages.getInstance().getUserHelpMessage();
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

}
