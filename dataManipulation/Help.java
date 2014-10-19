package dataManipulation;

import java.util.List;

public class Help extends Command {

	public Help(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("help", commandComponents);
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
