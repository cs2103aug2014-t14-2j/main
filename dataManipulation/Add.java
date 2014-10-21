package dataManipulation;

import java.util.List;

public class Add extends Command {

	public Add(List<Subcommand> subcommands)
					throws IllegalArgumentException {
		super("add", subcommands);
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void checkValidity() {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);

			switch (component.getType()) {
			case NAME :
				break; // valid
			case CATEGORY :
				break;
			case END :
				break;
			case LOCATION :
				break;
			case NOTE :
				break;
			case START :
				break;
			default :
				throw new IllegalArgumentException("invalid subcommand");
			}
		}
		
		checkForNoDuplicateSubcommands();
	}

}
