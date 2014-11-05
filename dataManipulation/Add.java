/**
  * @author nellystix
 */

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

public class Add extends Command {
	
	private String taskName = null;
	private String taskCategory = null;
	private String taskLocation = null;
	private String taskNote = null;
	private Date taskStart = null;
	private Date taskEnd = null;
	private Time startTime = null;
	private Time endTime = null;
	private static TotalTaskList taskList = TotalTaskList.getInstance();
	private static List<Task> tasks = TotalTaskList.getInstance().getList();
	private static TaskFactory makeMyTask = TaskFactory.getInstance();
	private static ezCMessages message = ezCMessages.getInstance();

	public Add(List<Subcommand> subcommands)
					throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.ADD, subcommands);
	}

	@Override
	public String execute() throws Exception {
		
		Task newTask = buildTask(subcommands);
		
		if(ExactMatchSearcher.isTaskDuplicate(newTask)) {	// If the task list already contains this task, throw an error
			ActionException moreThanOne = new ActionException(tasks, ActionException.ErrorLocation.ADD, subcommands);
			throw moreThanOne;
		}

		else {
			addTaskToList(newTask);
			FileIo IoStream = FileIo.getInstance();
			IoStream.rewriteFile();
			flushSubcommand();
			String returnMessage = message.getAddMessage(newTask);
			return returnMessage;
		}
	}
	
	private void addTaskToList(Task toAdd) {
		
		Date today = new Date();
		
		if(!toAdd.getHasDeadline() || !(toAdd.getEndDate().isBefore(today))) {
			taskList.getList().add(toAdd);
		}
		else if(toAdd.getEndDate().isBefore(today) && !(toAdd.getIsComplete())) {
			taskList.getOverdue().add(toAdd);
		}
		else if((toAdd.getEndDate().isBefore(today) && toAdd.getIsComplete()) || toAdd.getIsComplete()) {
			taskList.getCompleted().add(toAdd);
		}
		
	}
	
	public Task buildTask(List<Subcommand> taskAttributes) throws Exception {

		assembleAttributes(taskAttributes);

		Task toBeAdded = makeMyTask.makeTask(taskName, taskCategory, taskLocation, taskNote, taskStart, taskEnd);

		return toBeAdded;

	}
	
	private void flushSubcommand() {
		taskName = null;
		taskCategory = null;
		taskLocation = null;
		taskNote = null;
		taskStart = null;
		taskEnd = null;
		startTime = null;
		taskEnd = null;
	}
	
	public List<Subcommand> dismantleTask(Task taskToDismantle) throws BadSubcommandException, BadSubcommandArgException {
		
		List<Subcommand> taskDetails = new ArrayList<Subcommand>();
		
		taskDetails.add(new Subcommand(Subcommand.TYPE.NAME, taskToDismantle.getName()));
		
		if(taskToDismantle.getCategory() != null) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.CATEGORY, taskToDismantle.getCategory()));
		}
		
		if(taskToDismantle.getLocation() != null) { 
			taskDetails.add(new Subcommand(Subcommand.TYPE.LOCATION, taskToDismantle.getLocation()));
		}
		
		taskDetails.add(new Subcommand(Subcommand.TYPE.START, taskToDismantle.getStartDate().toString()));
		
		if(taskToDismantle.getEndDate().getDay() != 0) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.END, taskToDismantle.getEndDate().toString()));
		}
		
		if(taskToDismantle.getStartTime() != null) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.STARTTIME, taskToDismantle.getStartTime().toString()));
		}
		
		if(taskToDismantle.getEndTime() != null) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.ENDTIME, taskToDismantle.getEndTime().toString()));
		}
		
		if(taskToDismantle.getNote() != null) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.NOTE, taskToDismantle.getNote()));
		}
		
		return taskDetails;
	}
	
	private void assembleAttributes(List<Subcommand> taskAttributes) throws Exception {
		
		for(Subcommand cc : taskAttributes) {

			switch (cc.getType()) {

			case NAME:	setTaskName(cc.getContents());
						break;
			
			case CATEGORY:	setTaskCategory(cc.getContents());
							break;
			
			case LOCATION:	setTaskLocation(cc.getContents());
							break;
			
			case NOTE:	setTaskNote(cc.getContents());
						break;
			
			case START:	setTaskStart(cc.getContents());
						break;
			
			case END:	setTaskEnd(cc.getContents());
						break;
						
			case STARTTIME: setTaskStartTime(cc.getContents());
							break;
			
			case ENDTIME:	setTaskEndTime(cc.getContents());
							break;
			
			default:
				break;
			
			}

		}
		
	}
	
	private void setTaskEnd(String contents) {
		taskEnd = new Date().determineDate(contents);
	}

	private void setTaskStart(String contents) {
		taskStart = new Date().determineDate(contents);
	}

	private void setTaskNote(String contents) {
		taskNote = contents;
	}

	private void setTaskLocation(String contents) {
		taskLocation = contents;
	}

	private void setTaskCategory(String contents) {
		taskCategory = contents;
	}

	private void setTaskName(String contents) {
		taskName = contents;
	}
	
	private void setTaskStartTime(String contents) throws Exception {
		startTime = new Time().determineTime(contents);
	}
	
	private void setTaskEndTime(String contents) throws Exception {
		endTime = new Time().determineTime(contents);
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

}
