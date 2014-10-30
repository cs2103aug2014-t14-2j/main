package dataManipulation;


import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import fileIo.FileIo;

public class Edit extends Command {
	
	private static List<Task> taskList = TotalTaskList.getInstance().getList();
	private static ezCMessages messages = ezCMessages.getInstance();

	public Edit(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("edit", commandComponents);
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
	
	private Task getTaskToEdit() throws Exception {
		
		List<Task> tasks = ExactMatchSearcher.exactSearch(subcommands.get(0), taskList);
		if(tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(taskList, ActionException.ErrorLocation.EDIT, subcommands);
			throw moreThanOne;
		}
		else {
			return tasks.get(0);
		}
	}
	
	public static Task editTask(Task toEdit, List<Subcommand> taskAttributes) throws Exception {
		
		Task editedTask = setTaskAttributes(toEdit, taskAttributes);
		
		if(ExactMatchSearcher.isTaskDuplicate(editedTask)) {
			ActionException moreThanOne = new ActionException(taskList, ActionException.ErrorLocation.EDIT, taskAttributes);
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

			case END:	toEdit.setEndDate(Date.determineDate(cc.getContents()));
						break;

			case NOTE:	toEdit.setNote(cc.getContents());
						break;

			default:
				break;
			}
		}
			
			return toEdit;
	}
	
	private void addEditedTask(Task oldTask, Task newTask) {
		taskList.remove(oldTask);
		taskList.add(newTask);
		FileIo IoStream = FileIo.getInstance();
		IoStream.rewriteFile();
	}

	@Override
	protected void checkValidity() {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			switch (component.getType()) {
				case NAME :
					break; // valid
				case CATEGORY :
					break;
				case END :
					break;
				case LOCATION :
					break;
				case NOTE :
					break;
				case START :
					break;
				case TITLE :
					break;
				default :
					throw new IllegalArgumentException("invalid subcommand");
			}
		}
		
		checkForNoDuplicateSubcommands();
	}

}
