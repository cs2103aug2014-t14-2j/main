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
	public String execute() throws Exception {
		TotalTaskList totalList = TotalTaskList.getInstance();
		totalList.update();
		List<Task> overdueTasks = totalList.getOverdue();
		return getStringOfAllTasks(overdueTasks);
	}

	private String getStringOfAllTasks(List<Task> list) {
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(list);
	}

}
