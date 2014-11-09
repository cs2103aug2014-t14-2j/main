package dataManipulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.NoResultException;
import dataEncapsulation.Task;
import dataEncapsulation.sortTaskByEndDate;
import dataEncapsulation.sortTaskByName;
import dataEncapsulation.sortTaskByStartDate;
import dataManipulation.CommandType.COMMAND_TYPE;


public class Sort extends Command {
	private List<Task> allTasks;
	private Comparator<Task> byStartDate = new sortTaskByStartDate();
	private Comparator<Task> byEndDate = new sortTaskByEndDate();
	private Comparator<Task> byName = new sortTaskByName();


	public Sort(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.SORT, commandComponents);
	}

	@Override
	public String execute() throws NoResultException {
		allTasks = TotalTaskList.getInstance().getList();
		switch (subcommands.get(0).getType()) {
		case BYNAME:
			return ezCMessages.getInstance().getStringOfTasks(sortByName());
		case BYEND:
			return ezCMessages.getInstance().getStringOfTasks(sortByEndDate());
		case BYSTART:
			return ezCMessages.getInstance().getStringOfTasks(sortByStartDate());
		default:
			return "Sort by name or date!";
		}
		
	}

	private List<Task> sortByEndDate() {
		List<Task> sortedByDate = new ArrayList<Task>(allTasks);
		Collections.sort(sortedByDate, byEndDate);
		return sortedByDate;
	}
	
	private List<Task> sortByStartDate() {
		List<Task> sortedByDate = new ArrayList<Task>(allTasks);
		Collections.sort(sortedByDate, byStartDate);
		return sortedByDate;
	}
	
	private List<Task> sortByName() {
		List<Task> sortedByName = new ArrayList<Task>(allTasks);
		Collections.sort(sortedByName, byName);
		return sortedByName;
	}
	
	@Override
	protected void checkValidity() throws BadSubcommandException {
		checkForComponentAmount(1);
		super.checkValidity();
	}

	@Override
	public String undo() throws Exception {
		return null;
	}

}
