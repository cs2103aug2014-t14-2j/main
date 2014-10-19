package dataManipulation;

import java.util.List;

public class Category extends Command {

	public Category(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("category", commandComponents);
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void checkValidity() {
		checkForComponentAmount(1);
		boolean isComponentCategory = 
				checkForSpecificComponent(Subcommand.TYPE.CATEGORY);
			
			if (!isComponentCategory) {
				throw new IllegalArgumentException("invalid subcommand");
			}
	}

}
