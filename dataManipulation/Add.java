//@author A0115696W

package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.Autocomplete;
import userInterface.ezCMessages;
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
	private TotalTaskList taskList = TotalTaskList.getInstance();
	private ezCMessages message = ezCMessages.getInstance();

	public Add(List<Subcommand> subcommands)
			throws BadCommandException, BadSubcommandException, BadSubcommandArgException {
		super(COMMAND_TYPE.ADD, subcommands);
		boolean hasDateSubcommand = hasSubcommandType(Subcommand.TYPE.DATE);
		if (hasDateSubcommand) {
			parseDateToStartAndEnd();
		}
	}
	
	public Add() throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.ADD, new ArrayList<Subcommand>());
	}

	@Override
	public String execute() throws Exception {

		Task newTask = buildTask(subcommands);

		if(ExactMatchSearcher.isTaskDuplicate(newTask)) {
			throw new BadSubcommandArgException("The task you are trying to add already exists.");
		}

		else {
			addTaskToList(newTask);
			FileIo IoStream = FileIo.getInstance();
			IoStream.rewriteFile();
			updateAutocomplete();
			flushSubcommand();
			String returnMessage = message.getAddMessage(newTask);
			return returnMessage;
		}
		
	}

	//@author A0126720N
	private void updateAutocomplete() {
		Autocomplete autocomplete = Autocomplete.getInstance();
		autocomplete.addTitle(taskName);
		autocomplete.addCategory(taskCategory);
		autocomplete.addLocation(taskLocation);
	}

	//@author A0111014J
	public boolean addTaskToList(Task toAdd) {
		boolean result = false;
		Date today = new Date();

		if(!toAdd.getHasDeadline() || !(toAdd.getEndDate().isBefore(today))) {
			taskList.addNotCompleted(toAdd);
			result = true;
		}
		else if(toAdd.getEndDate().isBefore(today) && !(toAdd.getIsComplete())) {
			taskList.addOverdue(toAdd);
			result = true;
		}
		else if((toAdd.getEndDate().isBefore(today) && toAdd.getIsComplete()) || toAdd.getIsComplete()) {
			taskList.addCompleted(toAdd);
			result = true;
		}
		return result;

	}

	public Task buildTask(List<Subcommand> taskAttributes) throws Exception {

		assembleAttributes(taskAttributes);

		Task toBeAdded = new Task(taskName, taskCategory, taskLocation, taskNote, taskStart, taskEnd, startTime, endTime);

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

	public static List<Subcommand> dismantleTask(Task taskToDismantle) throws BadSubcommandException, BadSubcommandArgException {

		List<Subcommand> taskDetails = new ArrayList<Subcommand>();

		taskDetails.add(new Subcommand(Subcommand.TYPE.NAME, taskToDismantle.getName()));
		
		if(taskToDismantle.hasCategory()) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.CATEGORY, taskToDismantle.getCategory()));
		}
		
		if(taskToDismantle.getHasLocation()) { 
			taskDetails.add(new Subcommand(Subcommand.TYPE.LOCATION, taskToDismantle.getLocation()));
		}

		taskDetails.add(new Subcommand(Subcommand.TYPE.START, taskToDismantle.getStartDate().toString()));

		if(taskToDismantle.getHasDeadline()) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.END, taskToDismantle.getEndDate().toString()));
		}
		
		if(taskToDismantle.hasStartTime()) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.STARTTIME, taskToDismantle.getStartTime().toString()));
		}

		if(taskToDismantle.hasEndTime()) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.ENDTIME, taskToDismantle.getEndTime().toString()));
		}

		if(taskToDismantle.getHasNote()) {
			taskDetails.add(new Subcommand(Subcommand.TYPE.NOTE, taskToDismantle.getNote()));
		}

		return taskDetails;
	}

	private void assembleAttributes(List<Subcommand> taskAttributes) throws Exception {

		for(Subcommand cc : taskAttributes) {

			switch (cc.getType()) {

			case NAME:	setTaskName(cc.getContents());
			break;
			
			case TITLE:	setTaskName(cc.getContents());
			break;

			case CATEGORY:	setTaskCategory(cc.getContents());
			break;

			case LOCATION:	setTaskLocation(cc.getContents());
			break;

			case NOTE:	setTaskNote(cc.getContents());
			break;
			
			case ON: setTaskStart(cc.getContents()); setTaskEnd(cc.getContents());
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

	private void setTaskEnd(String contents) throws Exception {
		taskEnd = new Date().determineDate(contents);
	}

	private void setTaskStart(String contents) throws Exception {
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

	//@author A0126720N
	@Override
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
		checkDateMatches();
	}

	private void checkDateMatches() throws BadSubcommandException {
		boolean hasGeneralDate = hasSubcommandType(Subcommand.TYPE.DATE);
		boolean hasStartDate = hasSubcommandType(Subcommand.TYPE.START);
		boolean hasEndDate = hasSubcommandType(Subcommand.TYPE.END);

		if ((hasGeneralDate && hasStartDate) || (hasGeneralDate && hasEndDate)) {
			throw new BadSubcommandException("cannot specify start and/or end when specifying \"date\"");
		}
	}

	@Override
	public String undo() throws Exception {
		
		Remove negatedAddCommand = new Remove(subcommands);
		return negatedAddCommand.executeRemoveLiteral();
		
	}

}
