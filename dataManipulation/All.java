package dataManipulation;

import java.util.List;

public class All extends Command {

	public All(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("all", commandComponents);
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
