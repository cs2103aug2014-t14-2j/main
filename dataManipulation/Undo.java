package dataManipulation;

import java.util.List;

public class Undo extends Command {

	public Undo(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("undo", commandComponents);
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
