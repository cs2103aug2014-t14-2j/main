package dataManipulation;

import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.NoResultException;
import dataEncapsulation.Task;
import dataEncapsulation.ActionException.ErrorLocation;
import dataManipulation.CommandType.COMMAND_TYPE;
import fileIo.FileIo;

public class Unfinish extends Command {

	public Unfinish(List<Subcommand> commandComponents) 
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.UNFINISH, commandComponents);
	}

	@Override
	public String undo() throws Exception {
		Finish reverse = new Finish(subcommands);
		return reverse.literalFinish();
	}

	@Override
	public String execute() throws Exception {
		Task markedTask = markAsNotCompleted();
		ezCMessages messages = ezCMessages.getInstance();
		String stringTask = messages.getUnfinishMessage(markedTask);
		rewriteFile();
		return stringTask;
	}

	private void rewriteFile() {
		FileIo IoStream = FileIo.getInstance();
		IoStream.rewriteFile();
	}

	private Task markAsNotCompleted() throws Exception {
		TotalTaskList masterList = TotalTaskList.getInstance();
		List<Task> taskToBeMarked = ExactMatchSearcher.literalSearch(subcommands, masterList.getCompleted());
		if (taskToBeMarked.size() > 1) {
			throw new ActionException(taskToBeMarked, ErrorLocation.UNFINISH, subcommands);
		} else if (taskToBeMarked.size() == 0) {
			throw new Exception("no match found");
		}
		Task toUncomplete = taskToBeMarked.get(0);
		unfinishTask(toUncomplete);

		return toUncomplete;
	}

	private void unfinishTask(Task toUncomplete) throws Exception {
		TotalTaskList masterList = TotalTaskList.getInstance();
		List<Task> toSearchThrough = masterList.getCompleted();
		boolean wasSuccess = toSearchThrough.remove(toUncomplete);
		if (!wasSuccess) {
			throw new Exception("no match found");
		}

		toUncomplete.setIncomplete();
		if (toUncomplete.isOverdue()) {
			masterList.addOverdue(toUncomplete);
		} else {
			masterList.addNotCompleted(toUncomplete);
		}
	}


	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

	public String literalUnfinish() throws BadCommandException, 
	BadSubcommandException, BadSubcommandArgException, Exception {
		TotalTaskList list = TotalTaskList.getInstance();
		List<Task> completed = list.getCompleted();
		Task perfectMatch = findLiteralMatch(subcommands, completed);

		completed.remove(perfectMatch);
		perfectMatch.setIncomplete();
		list.addNotCompleted(perfectMatch);
		list.update();

		ezCMessages messages = ezCMessages.getInstance();
		return messages.getUnfinishMessage(perfectMatch);
	}

	private Task findLiteralMatch(List<Subcommand> subcommands, List<Task> list) 
			throws BadCommandException, 
			BadSubcommandException, BadSubcommandArgException, Exception {

		Task match = (new Add(subcommands)).buildTask(subcommands);
		TotalTaskList totalList = TotalTaskList.getInstance();
		List<Task> currentTasks = totalList.getCompleted();

		for (int i = 0; i < currentTasks.size(); ++i) {
			Task current = currentTasks.get(i);
			if (current.isEqualTask(match)) {
				return current;
			}
		}

		throw new NoResultException("The task that you are trying to finish cannot be found.");
	}


}
