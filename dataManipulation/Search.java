package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;
import powerSearch.Searcher;
import userInterface.ezCMessages;

public class Search extends Command {

	public Search(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.SEARCH, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		if(this.getComponents().contains(Subcommand.TYPE.COMPLETED)) {
			
		}
		TotalTaskList list = TotalTaskList.getInstance();
		List<Task> results = Searcher.search(subcommands, list.getList());
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(results);
	}

}
