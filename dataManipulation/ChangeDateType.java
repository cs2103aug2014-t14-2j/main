package dataManipulation;

import java.util.List;

public class ChangeDateType extends Command {

	public ChangeDateType(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("change date type", commandComponents);
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void checkValidity() {
		checkForComponentAmount(1);
		boolean isComponentDateType = 
			checkForSpecificComponent(Subcommand.TYPE.DATE_TYPE);
		
		if (!isComponentDateType) {
			throw new IllegalArgumentException("invalid subcommand");
		}
	}

}
