package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
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
		List<Task> results = new ArrayList<Task>();
		Date today = new Date();
		Subcommand date = new Subcommand(Subcommand.TYPE.DATE, today.toString());
		List<Task> allTasks = TotalTaskList.getInstance().getList();
		for (Task t : allTasks) {
			Date end = t.getEndDate();
			Date start = t.getStartDate();
			boolean taskEndsAfterToday = today.isBefore(end);
			boolean taskEndsToday = today.isEquals(end);
			boolean taskStartsToday = today.isEquals(start);
			
			boolean taskStartedBeforeToday = start.isBefore(today);
			boolean taskInProgress = taskEndsAfterToday && taskStartedBeforeToday;
			
			if (taskEndsToday || taskStartsToday || taskInProgress) {
				results.add(t);
			}
		}
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(results);
	}

}
