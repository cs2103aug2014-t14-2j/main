package dataManipulation;

import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.CommandType.COMMAND_TYPE;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;
import fileIo.FileIo;

public class Remove extends Command {
	
	private static List<Task> tasksFound;
	private static Task taskToRemove;
	private static Task taskRemoved;
	private static List<Subcommand> sc;

	public Remove(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.REMOVE, commandComponents);
		sc = commandComponents;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		taskRemoved = remove(sc);
		return "Just deleted: \n" + taskRemoved.toString();
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}
	public static Task remove(List<Subcommand> cc) throws Exception {
		
		tasksFound = ExactMatchSearcher.exactSearch(cc.get(0), TotalTaskList.getInstance().getList());
		if (tasksFound.size() > 1) {
			ActionException moreThanOne = new ActionException(tasksFound, ActionException.ErrorLocation.DELETE,
											cc);
			throw moreThanOne;
		}
		taskToRemove = tasksFound.get(0) ;
		taskRemoved = taskToRemove;
		doDeleteTask(taskToRemove);
		return taskRemoved;
		
	}
	public static Task doDeleteTask(Task toRemove) {
		Task a = toRemove;
		TotalTaskList.getInstance().remove(toRemove);
		
		FileIo stream = FileIo.getInstance();
		TotalTaskList.getInstance().remove(toRemove);
		stream.rewriteFile();
		return a;
	}

}
