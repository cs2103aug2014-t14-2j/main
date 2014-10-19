package dataManipulation;

import java.util.List;

public class Today extends Command {

	public Today(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("today", commandComponents);
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void checkValidity() {
		checkForNoComponents();
	}

}
