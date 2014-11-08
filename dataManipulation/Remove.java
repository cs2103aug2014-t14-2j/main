package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
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
	private static Task taskRemovedForEdit;
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
	
	/*
	 * @author Nelson / A0111014J
	 */
	
	public void executeForEdit() throws Exception {
		
		removeForEdit(sc);
		
	}
	
	public static Task remove(List<Subcommand> cc) throws Exception {
		
		tasksFound = new ArrayList<Task>();
		List<Task> currentTasks = TotalTaskList.getInstance().getList();
		List<Task> overdueTasks = TotalTaskList.getInstance().getOverdue();
		List<List<Task>> categorizedTasks = new ArrayList<List<Task>>();
		categorizedTasks.add(currentTasks);
		categorizedTasks.add(overdueTasks);
		int j = 0;
		
		for(int i = 0; i < categorizedTasks.size(); i++) {
			List<Task> tasksThusFar = Searcher.search(cc, categorizedTasks.get(i));
			if(tasksThusFar.size() != 0) {
				tasksFound.addAll(tasksThusFar);
				j = i;
			}
			
		}
		
		if (tasksFound.size() > 1) {
			ActionException moreThanOne = new ActionException(tasksFound, ActionException.ErrorLocation.DELETE,
											cc);
			throw moreThanOne;
		}
		taskToRemove = tasksFound.get(0) ;
		taskRemoved = taskToRemove;
		doDeleteTask(taskToRemove, j);
		return taskRemoved;
		
	}
	
	public static void removeForEdit(List<Subcommand> listOfSubs) {
		
		Task exactTask = null;
		List<Task> currentTasks = TotalTaskList.getInstance().getList();
		List<Task> overdueTasks = TotalTaskList.getInstance().getOverdue();
		List<List<Task>> categorizedTasks = new ArrayList<List<Task>>();
		categorizedTasks.add(currentTasks);
		categorizedTasks.add(overdueTasks);
		int i = 0;
		
		for(i = 0; i < categorizedTasks.size(); i++) {
			Task exactTaskFound = ExactMatchSearcher.exactTaskSearch(listOfSubs, categorizedTasks.get(i));
			if(exactTaskFound != null) {
				exactTask = exactTaskFound;
				break;
			}
		}
		
		doDeleteTask(exactTask, i);
		
	}
	
	public static Task doDeleteTask(Task toRemove, int i) {
		Task a = toRemove;
		
		switch(i) {
				
			case 0 :
				TotalTaskList.getInstance().removeNotCompleted(a);
				break;
				
			case 1 :
				TotalTaskList.getInstance().removeOverdue(a);
				break;
				
			default:
				System.out.println("Delete failed");
				break;
				
		}
		
		FileIo stream = FileIo.getInstance();
		stream.rewriteFile();
		return a;
	}

}
