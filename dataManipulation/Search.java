package dataManipulation;

import java.util.List;

public class Search extends Command {

	public Search(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("search", commandComponents);
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void checkValidity() {
		for (int i = 0; i < components.size(); ++i) {
			Subcommand component = components.get(i);
			
			switch (component.getType()) {
				case LINK :	 // valid
					break;
				case TITLE :
					break;
				case CATEGORY :
					break;
				case DATE :
					break;
				case END :
					break;
				case LOCATION :
					break;
				case NOTE :
					break;
				case PAREN :
					break;
				case START :
					break;
				case TEXT:
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
	}

}
