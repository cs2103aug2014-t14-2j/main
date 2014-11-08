package dataManipulation;

import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataManipulation.CommandType.COMMAND_TYPE;

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

	@Override
	public String undo() throws Exception {
		return null;
	}

}
