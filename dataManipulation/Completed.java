package dataManipulation;

import java.util.List;

public class Completed extends Command {

	public Completed(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("completed", commandComponents);
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
