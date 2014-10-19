package dataManipulation;

import java.util.List;

public class Sort extends Command {

	public Sort(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("sort", commandComponents);
	}

	@Override
	public String execute() {
		return null;
	}

	@Override
	protected void checkValidity() {
		checkForComponentAmount(1);
		
		boolean hasStart = 
				checkForSpecificComponent(Subcommand.TYPE.START);
		boolean hasEnd = 
				checkForSpecificComponent(Subcommand.TYPE.END);
		
		if (!hasStart && !hasEnd) {
			throw new IllegalArgumentException("invalid subcommand");
		}
	}

}
