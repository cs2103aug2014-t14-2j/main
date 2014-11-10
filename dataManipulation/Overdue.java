package dataManipulation;

//@author A0126720N

import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.NoResultException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Overdue extends Command{
	public Overdue(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.OVERDUE, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		TotalTaskList totalList = TotalTaskList.getInstance();
		totalList.update();
		List<Task> overdueTasks = totalList.getOverdue();
		
		ezCMessages overdueTasksMessage = ezCMessages.getInstance();
		String displayOverdueTasks = overdueTasksMessage.getOverdueTasksListMessage();
		displayOverdueTasks += getStringOfAllTasks(overdueTasks);
		
		return displayOverdueTasks;
	}

	private String getStringOfAllTasks(List<Task> list) throws NoResultException {
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(list);
	}

	@Override
	public String undo() throws Exception {
		return null;
	}

}
