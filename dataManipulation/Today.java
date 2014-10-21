package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.Searcher;
import userInterface.ezCMessages;
import dataEncapsulation.Date;
import dataEncapsulation.Task;

public class Today extends Command {

	public Today(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("today", commandComponents);
	}

	@Override
	public String execute() throws Exception {
		Date today = new Date();
		Subcommand date = new Subcommand(Subcommand.TYPE.DATE, today.toString());
		List<Subcommand> holder = new ArrayList<Subcommand>();
		holder.add(date);
		
		TotalTaskList totalList = TotalTaskList.getInstance();
		
		List<Task> results = Searcher.search(holder, totalList.getList());
		
		ezCMessages messages = ezCMessages.getInstance();
		
		return messages.getStringOfTasks(results);
	}

	@Override
	protected void checkValidity() {
		checkForNoComponents();
	}

}
