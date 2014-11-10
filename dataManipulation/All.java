package dataManipulation;

//@author A0126720N

import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.NoResultException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public class All extends Command {

	public All(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.ALL, commandComponents);
	}

	@Override
	public String execute() throws NoResultException {
		ezCMessages allTasksMessage = ezCMessages.getInstance();
		String displayAllTasks = allTasksMessage.getAllTasksListMessage();
		
		List<Task> allTasks = getAllUncompletedTasks();
		String stringTasks = getStringOfAllTasks(allTasks);
		
		displayAllTasks += stringTasks;
		return displayAllTasks;
	}

	private List<Task> getAllUncompletedTasks() {
		TotalTaskList totalList = TotalTaskList.getInstance();
		List<Task> tasks = totalList.getNotCompleted();
		
		return tasks;
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
