/**
 * 
 * @author nellystix
 * 
 **/

package dataManipulation;

import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;
import fileIo.FileIo;

public class Edit extends Command {
	
	private TotalTaskList taskList = TotalTaskList.getInstance();
	private ezCMessages messages = ezCMessages.getInstance();

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
		
		UndoRedoList.getInstance().pushPreEditedTask(preEdit);	// Add the pre-edited task into the pre edited task stack
		
		String editComplete = messages.getEditMessage(preEdit, postEdit);
		return editComplete;
	}
	
	public Task furtherEdit(Task toEdit, List<Subcommand> taskAttributes) throws Exception {
		
		Task postEdit = editTask(toEdit, subcommands);
		
		addEditedTask(toEdit, postEdit);
		
		UndoRedoList.getInstance().pushPreEditedTask(toEdit);	// Add the pre-edited task into the pre edited task stack
		
		return postEdit;
		
	}
	
/*	private Task exactTaskSearch(Task taskToCheck) {
		
		List<List<Task>> categorizedList = new ArrayList<List<Task>>();
		categorizedList.add(TotalTaskList.getInstance().getList());
		categorizedList.add(TotalTaskList.getInstance().getCompleted());
		categorizedList.add(TotalTaskList.getInstance().getOverdue());
		
		for(List<Task> lt : categorizedList) {
		
			for(Task t : lt) {
				
				if(t.getName().toLowerCase().equals(taskToCheck.getName().toLowerCase())) {
					
					if(taskToCheck.getCategory() == null) {	// If task's category is null
						if(t.getCategory() != null) {		// Check if one of the task's category in the list is NOT null
							break;							// Break if they are different
						}
					}
						
					else if(t.getCategory() == null) {		// ELSE, this means that the task's category is not null and we check for one of the task's category in the list being null, i.e. different
						break;
					}
						
					else {									// ELSE, this means both are not null
						if(t.getCategory().toLowerCase().equals(taskToCheck.getCategory().toLowerCase())) { 	// Check for whether both categories are the same
						
							if(taskToCheck.getHasLocation() == false) {
								if(t.getHasLocation() == true) {
									break;
								}
							}
						
							else if(t.getHasLocation() == false) {
								break;
							}
							
							else {
								if(t.getLocation().toLowerCase().equals(taskToCheck.getLocation().toLowerCase())) {
								
									if(taskToCheck.getHasNote() == false) {
										if(t.getHasNote() == true) {
											break;
										}
									}
							
									else if(t.getHasNote() == false) {
										break;
									}
									
									else {
										if(t.getNote().toLowerCase().equals(taskToCheck.getNote().toLowerCase())) {
								
											if(t.getStartDate().toString().toLowerCase().equals(taskToCheck.getStartDate().toString().toLowerCase())) {
									
												if(taskToCheck.getEndDate().getDay() == 0) {
													if(t.getEndDate().getDay() != 0) {
														break;
													}
												}
												
												else if(t.getEndDate().getDay() == 0) {
													break;
												}
												
												else {
													if(t.getEndDate().isEqual(taskToCheck.getEndDate())) {
														return taskToCheck;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	} */
	
	private Task getTaskToEdit() throws Exception {
		
		List<Task> tasks = ExactMatchSearcher.exactSearch(subcommands.get(0), taskList.getAllTasks());
		if(tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(taskList.getList(), ActionException.ErrorLocation.EDIT, subcommands);
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
	
	private static Task setTaskAttributes(Task toEdit, List<Subcommand> taskAttributes) {
		
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
		Command removeOldTask = new Remove(oldTaskSubC);
		Command addNewTask = new Add(newTaskSubC);
		
		removeOldTask.execute();
		addNewTask.execute();
		FileIo IoStream = FileIo.getInstance();
		IoStream.rewriteFile(); 
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

}
