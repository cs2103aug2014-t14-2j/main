/**
 * 
 * @author nellystix
 * 
 **/

package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.Autocomplete;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataEncapsulation.Time;
import dataManipulation.CommandType.COMMAND_TYPE;
import fileIo.FileIo;

public class Edit extends Command {

	private TotalTaskList taskList = TotalTaskList.getInstance();
	private ezCMessages messages = ezCMessages.getInstance();

	private Task trueTask = null;
	private Task preeditCopy = null;
	private Task posteditCopy = null;

	public Edit(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.EDIT, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		try {
			if (trueTask == null) {
				trueTask = getTaskToEdit();
			}
	
			return executeMainEdit();
		} catch (BadSubcommandArgException repeatException) {
			if (posteditCopy != null) {
				throw new BadSubcommandArgException("Another task matches the "
						+ "changes you want to go back to");
			} else {
				throw new BadSubcommandArgException("Another task matches the "
						+ "changes you want to make");
			}
		}
	}
	
	public Task getTask() {
		return trueTask;
	}

	public String furtherEdit(Task toEdit) throws Exception {

		trueTask = toEdit;
		
		try {
			String result = executeMainEdit();
			return result;
		} catch (BadSubcommandArgException repeatException) {
			if (posteditCopy != null) {
				throw new BadSubcommandArgException("Another task matches the "
						+ "changes you want to go back to");
			} else {
				throw new BadSubcommandArgException("Another task matches the "
						+ "changes you want to make");
			}
		}
	}
	
	private String executeMainEdit() throws Exception {
		if (preeditCopy == null) {
			preeditCopy = new Task(trueTask);
		}
		Task temp = new Task(trueTask);
		setTaskAttributes(temp, subcommands);

		addEditedTask(temp, trueTask);
		
		if (posteditCopy == null) {
			posteditCopy = new Task(trueTask);
		}

		updateAutocomplete();
		FileIo.getInstance().rewriteFile();
		String editComplete = messages.getEditMessage(preeditCopy, trueTask);
		return editComplete;
	}


	private void updateAutocomplete() {
		Autocomplete autocomplete = Autocomplete.getInstance();
		autocomplete.addCategory(trueTask.getCategory());
		autocomplete.addTitle(trueTask.getName());
		autocomplete.addLocation(trueTask.getLocation());
	}

	private Task getTaskToEdit() throws Exception {

		List<Subcommand> nameOfTaskToEdit = new ArrayList<Subcommand>();
		nameOfTaskToEdit.add(subcommands.get(0));

		List<Task> tasks = ExactMatchSearcher.literalSearch(nameOfTaskToEdit, taskList.getNotCompleted());
		if(tasks.size() == 0) {
			throw new Exception("The task you wish to edit does not exist. Please check the name of your task again.");
		}
		if(tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(tasks, ActionException.ErrorLocation.EDIT, subcommands);
			throw moreThanOne;
		}
		else {
			return tasks.get(0);
		}

	}

	private static Task setTaskAttributes(Task toEdit, List<Subcommand> taskAttributes) throws Exception {

		for(Subcommand cc : taskAttributes) {

			switch (cc.getType()) {

			case TITLE:	toEdit.setName(cc.getContents());
			break;

			case CATEGORY:	toEdit.setCategory(cc.getContents());
			break;

			case LOCATION:	toEdit.setLocation(cc.getContents());
			break;

			case STARTTIME:	toEdit.setStartTime(new Time().determineTime(cc.getContents()));
			break;

			case ENDTIME:	toEdit.setEndTime(new Time().determineTime(cc.getContents()));
			break;

			case START :	toEdit.setStartDate(new Date().determineDate(cc.getContents()));
			break;

			case END:	toEdit.setEndDate(new Date().determineDate(cc.getContents()));
			break;

			case NOTE:	toEdit.setNote(cc.getContents());
			break;

			default:
				break;
			}
		}

		return toEdit;
	}

	private void addEditedTask(Task copy, Task toEditAndAdd) throws Exception {
		boolean isRepeat = ExactMatchSearcher.isTaskDuplicate(copy);
		
		if (isRepeat) {
			throw new BadSubcommandArgException("Task you want to create already exists");
		}
		
		toEditAndAdd.setEqualTo(copy);
		taskList.update();
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

	@Override
	public String undo() throws Exception {
		if (preeditCopy == null) {
			return "cannot undo because original edit was not successful";
		}
		
		trueTask.setEqualTo(preeditCopy);
		FileIo.getInstance().rewriteFile();
		taskList.update();
        
		ezCMessages messages = ezCMessages.getInstance();
        String returnMessage = messages.getUndoEditMessage(posteditCopy, trueTask);
		return returnMessage;

	}

}
