package dataManipulation;

//@author A0126720N

import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Overdue extends Command{
	public Overdue(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.OVERDUE, commandComponents);
	}

	@Override
	public String execute() {
		List<Task> overdueTasks = getOverdueTasks();
		String stringTasks = getStringOfAllTasks(overdueTasks);
		return stringTasks;
	}

	private List<Task> getOverdueTasks() {
		TotalTaskList totalList = TotalTaskList.getInstance();
		List<Task> overdueTasks = totalList.getOverdue();
		
		return overdueTasks;
	}

	private String getStringOfAllTasks(List<Task> list) {
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(list);
	}

}
