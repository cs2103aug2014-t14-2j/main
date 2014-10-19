package dataManipulation;

import java.util.List;

public class Note extends Command {

	public Note(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("note", commandComponents);
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void checkValidity() {
		checkForComponentAmount(1);
		boolean hasTitleComponent =
				checkForSpecificComponent(Subcommand.TYPE.NAME);
		
		if (!hasTitleComponent) {
			throw new IllegalArgumentException("invalid subcommand");
		}
	}

}
