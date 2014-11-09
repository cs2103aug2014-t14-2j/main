/**
 * 
 * @author nellystix
 * 
 **/

package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
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

	private Task trueTask;
	private Task preeditCopy;
	private Task posteditCopy = null;

	public Edit(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.EDIT, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		if (posteditCopy != null) {
			List<Subcommand> oldAttributes = Add.dismantleTask(posteditCopy);
			setTaskAttributes(trueTask, oldAttributes);
			FileIo.getInstance().rewriteFile();
			String editComplete = messages.getEditMessage(preeditCopy, trueTask);
			return editComplete;
		}
		
		trueTask = getTaskToEdit();

		preeditCopy = new Task(trueTask);
		Task temp = new Task(trueTask);
		setTaskAttributes(temp, subcommands);

		addEditedTask(temp, trueTask);
		posteditCopy = new Task(trueTask);

		FileIo.getInstance().rewriteFile();
		String editComplete = messages.getEditMessage(preeditCopy, trueTask);
		return editComplete;

	}

	/**
	 * takes a task and a list of subcommands and changes the task's subcommands to equal those
	 * specified while leaving the others the same. Saves returns a copy of the task to be
	 * edited
	 * @param task
	 * @param subcoms
	 * @return
	 * @throws BadSubcommandException
	 * @throws BadSubcommandArgException
	 * @throws BadCommandException
	 * @throws Exception
	 */
	private Task makeTasksAttributesEqual(Task task, List<Subcommand> subcoms) throws BadSubcommandException,
		BadSubcommandArgException, BadCommandException, Exception {
		List<Subcommand> taskToEditSubcommands = Add.dismantleTask(task);
		Task copy = new Add(taskToEditSubcommands).buildTask(taskToEditSubcommands);
		System.out.println("got past add");
		task = editTask(task, subcoms);
		return copy;
	}

	private Task makeTasksAttributesEqual(Task task, Task toCopy) throws BadSubcommandException,
		BadSubcommandArgException, BadCommandException, Exception {
		List<Subcommand> subcommandCopy = Add.dismantleTask(toCopy);
		return makeTasksAttributesEqual(task, subcommandCopy);
	}

	public Task furtherEdit(Task toEdit, List<Subcommand> taskAttributes) throws Exception {

		Task postEdit = editTask(toEdit, subcommands);

		addEditedTask(toEdit, postEdit);

		return postEdit;

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

	private Task editTask(Task toEdit, List<Subcommand> taskAttributes) throws Exception {

		Task editedTask = setTaskAttributes(toEdit, taskAttributes);

		if(ExactMatchSearcher.isTaskDuplicate(editedTask)) {
			System.out.println("duplicate");
			ActionException moreThanOne = new ActionException(taskList.getAllTasks(), ActionException.ErrorLocation.EDIT, taskAttributes);
			throw moreThanOne;
		}
		else {
			return editedTask;
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
		
		copy = new Task(toEditAndAdd);
		setTaskAttributes(toEditAndAdd, subcommands);

		taskList.update();
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

	@Override
	public String undo() throws Exception {
		List<Subcommand> oldAttributes = Add.dismantleTask(preeditCopy);
		setTaskAttributes(trueTask, oldAttributes);
        
		ezCMessages messages = ezCMessages.getInstance();
        String returnMessage = messages.getUndoEditMessage(posteditCopy, trueTask);
		return returnMessage;

	}

}
