package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;
import powerSearch.Searcher;
import userInterface.ezCMessages;

public class Search extends Command {

	public Search(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super("search", commandComponents);
	}

	@Override
	public String execute() throws Exception {
		TotalTaskList list = TotalTaskList.getInstance();
		List<Task> results = Searcher.search(subcommands, list.getList());
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(results);
	}

}
