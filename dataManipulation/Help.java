package dataManipulation;

import java.util.List;

import userInterface.ezCMessages;

public class Help extends Command {

	public Help(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("help", commandComponents);
		}

	@Override
	public String execute() {
		return ezCMessages.getInstance().getUserHelpMessage();
	}

	@Override
	protected void checkValidity() {
		checkForNoComponents();
	}

}
