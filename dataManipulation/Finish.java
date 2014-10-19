package dataManipulation;

import java.util.List;

public class Finish extends Command {

	public Finish(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("finish", commandComponents);
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
