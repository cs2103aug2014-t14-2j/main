package dataManipulation;

//@author A0115696W

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.Autocomplete;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.NoResultException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;
import fileIo.FileIo;

public class Remove extends Command {
	
	private List<Task> tasksFound;
	private Task taskToRemove;
	private Task taskRemoved;
	
	private static final String NO_MATCH_MESSAGE = "The task that you are trying to delete cannot be found.";

	public Remove(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException, 
			BadSubcommandArgException {
		super(COMMAND_TYPE.REMOVE, commandComponents);
		boolean hasDateSubcommand = hasSubcommandType(Subcommand.TYPE.DATE);
		if (hasDateSubcommand) {
			parseDateToStartAndEnd();
		}
	}

	@Override
	public String execute() throws Exception {
		taskRemoved = remove(subcommands);
		updateFile();
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getDeleteMessage(taskRemoved);
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}
	
	 //@author A0111014J
	public Task remove(List<Subcommand> cc) throws Exception {
		
		int indexOfDeletionList = initializeTasksFound(cc);
		
		if (tasksFound.size() > 1) {
			ActionException moreThanOne = new ActionException(tasksFound, ActionException.ErrorLocation.DELETE,
											cc);
			throw moreThanOne;
		} else if (tasksFound.isEmpty()) {
			throw new NoResultException(NO_MATCH_MESSAGE);
		}
		taskToRemove = tasksFound.get(0);
		taskRemoved = taskToRemove;
		doDeleteTask(taskToRemove, indexOfDeletionList);
		return taskRemoved;
		
	}
	
	//@author A0126720N
	public String executeRemoveLiteral() throws Exception {
		int indexOfDeletionList = initializeTasksFound(subcommands);
		List<Task> listToDeleteFrom = getDeletionList(indexOfDeletionList);
		Task perfectMatch = findLiteralMatch(subcommands, listToDeleteFrom);
		taskRemoved = doDeleteTask(perfectMatch, indexOfDeletionList);
		updateFile();
		ezCMessages messages = ezCMessages.getInstance();
		return messages.getDeleteMessage(taskRemoved);
	}

	//@author A0111014J
	private Task findLiteralMatch(List<Subcommand> subcommands, 
			List<Task> listToDeleteFrom) throws BadCommandException, 
			BadSubcommandException, BadSubcommandArgException, Exception {
		Task match = (new Add(subcommands)).buildTask(subcommands);
		for (int i = 0; i < listToDeleteFrom.size(); ++i) {
			if (match.isEqualTask(listToDeleteFrom.get(i))) {
				return listToDeleteFrom.get(i);
			}
		}
		
		throw new NoResultException(NO_MATCH_MESSAGE);
	}

	private int initializeTasksFound(List<Subcommand> cc)
			throws BadSubcommandException, BadSubcommandArgException,
			BadCommandException {
		tasksFound = new ArrayList<Task>();
		List<Task> currentTasks = TotalTaskList.getInstance().getList();
		List<Task> overdueTasks = TotalTaskList.getInstance().getOverdue();
		List<List<Task>> categorizedTasks = new ArrayList<List<Task>>();
		categorizedTasks.add(currentTasks);
		categorizedTasks.add(overdueTasks);
		int j = 0;
		
		for(int i = 0; i < categorizedTasks.size(); i++) {
			List<Task> tasksThusFar = ExactMatchSearcher.literalSearch(cc, categorizedTasks.get(i));
			if(tasksThusFar.size() != 0) {
				tasksFound.addAll(tasksThusFar);
				j = i;
			}
			
		}
		return j;
	}
	
	public static Task doDeleteTask(Task toRemove, int i) throws NoResultException {
		List<Task> toDeleteFrom = getDeletionList(i);
		toDeleteFrom.remove(toRemove);
		
		return toRemove;
	}
	
	private static List<Task> getDeletionList(int index) throws NoResultException {
		switch(index) {
		
		case 0 :
			return TotalTaskList.getInstance().getList();
			
		case 1 :
			return TotalTaskList.getInstance().getOverdue();
			
		default:
			throw new NoResultException(NO_MATCH_MESSAGE);
			
		}
	}
	
	private void updateFile() {
		FileIo stream = FileIo.getInstance();
		stream.rewriteFile();
	}
	
	//@author A0126720N-unused
	@SuppressWarnings("unused")
	private void updateAutocomplete(Task task) {
		Autocomplete autocomplete = Autocomplete.getInstance();
		
		autocomplete.removeTitle(task.getName());
		autocomplete.removeCategory(task.getCategory());
		
		if (task.getHasLocation()) {
			autocomplete.removeLocation(task.getLocation());
		}
	}

	@Override
	public String undo() throws Exception {
		
		List<Subcommand> removedTaskSubC = Add.dismantleTask(taskRemoved);
		Command negatedRemoveCommand = new Add(removedTaskSubC);
		return negatedRemoveCommand.execute();
		
	}

}
