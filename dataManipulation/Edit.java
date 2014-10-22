package dataManipulation;

import java.util.List;

import powerSearch.ExactMatchSearcher;
import powerSearch.Searcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataEncapsulation.UndoRedoProcessor;
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
		Task toEdit = getTaskToEdit();
		Task preEdit = toEdit;
		Task postEdit = editTask(toEdit, subcommands);
		
		addEditedTask(preEdit, postEdit);
		UndoRedoProcessor.preEditTaskStack.add(toEdit);	// Add the pre-edited task into the pre edited task stack
		
		String editComplete = messages.getEditMessage(preEdit, postEdit);
		return editComplete;
	}
	
	private Task getTaskToEdit() throws Exception {
		
		List<Task> tasks = Searcher.search(subcommands, taskList);
		if(tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(taskList, ActionException.ErrorLocation.EDIT, subcommands);
			throw moreThanOne;
		}
		else {
			return tasks.get(0);
		}
	}
	
	public Task editTask(Task toEdit, List<Subcommand> taskAttributes) throws Exception {
		
		Task editTaskExceptName = new Add(taskAttributes).buildTask(taskAttributes);
		Task editTaskIncludingName = setTaskAttributes(editTaskExceptName, taskAttributes);
		
		if(ExactMatchSearcher.isTaskDuplicate(editTaskIncludingName)) {
			ActionException moreThanOne = new ActionException(taskList, ActionException.ErrorLocation.EDIT, taskAttributes);
			throw moreThanOne;
		}
		else {
			return editTaskIncludingName;
		}
	}
	
	private Task setTaskAttributes(Task toEdit, List<Subcommand> taskAttributes) {
		
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
	
	public void addEditedTask(Task oldTask, Task newTask) {
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
