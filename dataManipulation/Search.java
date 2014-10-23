package dataManipulation;

import java.util.List;

import dataEncapsulation.Task;
import powerSearch.Searcher;
import userInterface.ezCMessages;

public class Search extends Command {

	public Search(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("search", commandComponents);
	}

	@Override
	public String execute() throws Exception {
		TotalTaskList list = TotalTaskList.getInstance();
		List<Task> results = Searcher.search(subcommands, list.getList());
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(results);
	}

	@Override
	protected void checkValidity() {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			switch (component.getType()) {
				case AND :
					break;	// valid
				case CATEGORY :
					break;
				case DATE :
					break;
				case END :
					break;
				case LOCATION :
					break;
				case NAME :
					break;
				case NOTE :
					break;
				case OR :
					break;
				case PAREN :
					break;
				case START :
					break;
				case TEXT:
					break;
				case TITLE :
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
	}

}
