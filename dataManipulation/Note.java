package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;

public class Note extends Command {

	public Note(List<Subcommand> commandComponents) 
			throws BadCommandException, BadSubcommandException {
		super("note", commandComponents);
	}

	@Override
	public String execute() {
		String unimplemented = "This command has not been finished. :)";
		return unimplemented;
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

}
