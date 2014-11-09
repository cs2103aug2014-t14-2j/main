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
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Edit extends Command {
	
	private TotalTaskList taskList = TotalTaskList.getInstance();
	private ezCMessages messages = ezCMessages.getInstance();
	private Command addNewTask;
	private Command removeOldTask;

	public Edit(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.EDIT, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		
		Task preEdit = getTaskToEdit();
		List<Subcommand> taskToEditSubcommands = new Add(subcommands).dismantleTask(preEdit);
		Task taskToEdit = new Add(subcommands).buildTask(taskToEditSubcommands);
		Task postEdit = editTask(taskToEdit, subcommands);
		
		addEditedTask(preEdit, postEdit);
		
		String editComplete = messages.getEditMessage(preEdit, postEdit);
		return editComplete;
		
	}
	
	public Task furtherEdit(Task toEdit, List<Subcommand> taskAttributes) throws Exception {
		
		Task postEdit = editTask(toEdit, subcommands);
		
		addEditedTask(toEdit, postEdit);
		
		return postEdit;
		
	}
	
	private Task getTaskToEdit() throws Exception {
		
		List<Subcommand> nameOfTaskToEdit = new ArrayList<Subcommand>();
		nameOfTaskToEdit.add(subcommands.get(0));
		
		List<Task> tasks = ExactMatchSearcher.literalSearch(nameOfTaskToEdit, taskList.getAllTasks());
		if(tasks.size() == 0) {
			throw new Exception("the task you wish to edit does not exist");
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
	
	private void addEditedTask(Task oldTask, Task newTask) throws Exception {
		
		List<Subcommand> oldTaskSubC = new Add(subcommands).dismantleTask(oldTask);
		List<Subcommand> newTaskSubC = new Add(subcommands).dismantleTask(newTask);
		removeOldTask = new Remove(oldTaskSubC);
		addNewTask = new Add(newTaskSubC);
		
		removeOldTask.execute();
		UndoRedoList.getInstance().pushUndoCommand(removeOldTask);	//Push the remove into the undo stack
		addNewTask.execute();
		UndoRedoList.getInstance().pushUndoCommand(addNewTask);	//Push the add into the undo stack
		
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

	@Override
	public String undo() throws Exception {
		
		Command negatedEditCommandRemove = addNewTask;
		Command negatedEditCommandAdd = removeOldTask;
		negatedEditCommandRemove.execute();
		negatedEditCommandAdd.execute();
		String returnMessage = "";
		return returnMessage;
		
	}

}
