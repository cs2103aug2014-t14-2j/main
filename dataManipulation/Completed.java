package dataManipulation;

import java.util.List;

import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.NoResultException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Completed extends Command {

	public Completed(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.COMPLETED, commandComponents);
	}

	@Override
	public String execute() throws NoResultException {
		List<Task> allTasks = getAllCompletedTasks();
		String stringTasks = getStringOfAllTasks(allTasks);
		
		ezCMessages completedTasksMessage = ezCMessages.getInstance();
		String displayCompletedTasks = completedTasksMessage.getCompletedTasksListMessage();
		displayCompletedTasks += stringTasks;
		
		return displayCompletedTasks;
	}

	private List<Task> getAllCompletedTasks() {
		TotalTaskList totalList = TotalTaskList.getInstance();
		return totalList.getCompleted();
	}

	private String getStringOfAllTasks(List<Task> list) throws NoResultException {
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getStringOfTasks(list);
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

	@Override
	public String undo() throws Exception {
		return null;
	}

}
