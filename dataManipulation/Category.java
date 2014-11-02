package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;

public class Category extends Command {

	public Category(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super("category", commandComponents);
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		checkForComponentAmount(1);
		super.checkValidity();
	}

}
