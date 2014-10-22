package dataManipulation;

import java.util.List;
import powerSearch.Searcher;
import dataEncapsulation.ActionException;
import dataEncapsulation.Task;
import fileIo.FileIo;

public class Remove extends Command {
	
	private static List<Task> tasksFound;
	private static Task taskToRemove;
	private static Task taskRemoved;
	private static List<Subcommand> sc;

	public Remove(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("remove", commandComponents);
		sc = commandComponents;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		taskRemoved = remove(sc);
		return taskRemoved.toString();
	}

	@Override
	protected void checkValidity() {
		checkForComponentAmount(1);
		boolean hasTitleComponent =
				checkForSpecificComponent(Subcommand.TYPE.NAME);
		
		if (!hasTitleComponent) {
			throw new IllegalArgumentException("invalid subcommand");
		}
	}
	public static Task remove(List<Subcommand> cc) throws Exception {
		
		tasksFound = Searcher.search(cc, TotalTaskList.getInstance().getList());
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
