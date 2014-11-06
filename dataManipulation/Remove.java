package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.Searcher;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;
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
		
		List<Task> completedTasks = TotalTaskList.getInstance().getCompleted();
		List<Task> currentTasks = TotalTaskList.getInstance().getList();
		List<Task> overdueTasks = TotalTaskList.getInstance().getOverdue();
		List<List<Task>> categorizedTasks = new ArrayList<List<Task>>();
		categorizedTasks.add(completedTasks);
		categorizedTasks.add(currentTasks);
		categorizedTasks.add(overdueTasks);
		int i = 0;
		
		for(i = 0; i < categorizedTasks.size(); i++) {
			tasksFound = Searcher.search(cc, categorizedTasks.get(i));
			if(tasksFound.size() > 0) {
				break;
			}
		}
		
		if (tasksFound.size() > 1) {
			ActionException moreThanOne = new ActionException(tasksFound, ActionException.ErrorLocation.DELETE,
											cc);
			throw moreThanOne;
		}
		taskToRemove = tasksFound.get(0) ;
		taskRemoved = taskToRemove;
		doDeleteTask(taskToRemove, i);
		return taskRemoved;
		
	}
	public static Task doDeleteTask(Task toRemove, int i) {
		Task a = toRemove;
		
		switch(i) {
			case 0 :
				TotalTaskList.getInstance().removeCompleted(a);
				break;
				
			case 1 :
				TotalTaskList.getInstance().remove(a);
				break;
				
			case 2 :
				TotalTaskList.getInstance().removeOverdue(a);
				break;
				
		}
	//	TotalTaskList.getInstance().remove(toRemove);
		
		FileIo stream = FileIo.getInstance();
	//	TotalTaskList.getInstance().remove(toRemove);
		stream.rewriteFile();
		return a;
	}

}
