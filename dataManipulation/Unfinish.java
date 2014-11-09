package dataManipulation;

import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
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
		return reverse.execute();
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

}
