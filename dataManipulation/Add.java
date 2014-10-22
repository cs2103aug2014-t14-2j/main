package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import fileIo.FileIo;

public class Add extends Command {
	
	private static String taskName = null;
	private static String taskCategory = null;
	private static String taskLocation = null;
	private static String taskNote = null;
	private static Date taskStart = null;
	private static Date taskEnd = null;
	private static TotalTaskList taskList = TotalTaskList.getInstance();
	private static List<Task> tasks = TotalTaskList.getInstance().getList();
	private static TaskFactory makeMyTask = TaskFactory.getInstance();
	private static ezCMessages message = ezCMessages.getInstance();

	public Add(List<Subcommand> subcommands)
					throws IllegalArgumentException {
		super("add", subcommands);
	}

	@Override
	public String execute() throws Exception {
		
		Task newTask = buildTask(subcommands);
		
		if(ExactMatchSearcher.isTaskDuplicate(newTask)) {	// If the task list already contains this task, throw an error
			ActionException moreThanOne = new ActionException(tasks, ActionException.ErrorLocation.ADD, subcommands);
			throw moreThanOne;
		}

		else {
			taskList.add(newTask);
			FileIo IoStream = FileIo.getInstance();
			IoStream.rewriteFile();
			String returnMessage = message.getAddMessage(newTask);
			return returnMessage;
		}
	}
	
	public static Task buildTask(List<Subcommand> taskAttributes) {

		assembleAttributes(taskAttributes);

		Task toBeAdded = makeMyTask.makeTask(taskName, taskCategory, taskLocation, taskNote, taskStart, taskEnd);

		return toBeAdded;

	}
	
	public static List<Subcommand> dismantleTask(Task taskToDismantle) {
		
		List<Subcommand> taskDetails = new ArrayList<Subcommand>();
		
		taskDetails.add(new Subcommand(Subcommand.TYPE.NAME, taskToDismantle.getName()));
		taskDetails.add(new Subcommand(Subcommand.TYPE.CATEGORY, taskToDismantle.getCategory()));
		taskDetails.add(new Subcommand(Subcommand.TYPE.LOCATION, taskToDismantle.getLocation()));
		taskDetails.add(new Subcommand(Subcommand.TYPE.START, taskToDismantle.getStartDate().toString()));
		taskDetails.add(new Subcommand(Subcommand.TYPE.END, taskToDismantle.getEndDate().toString()));
		taskDetails.add(new Subcommand(Subcommand.TYPE.NOTE, taskToDismantle.getNote()));
		
		return taskDetails;
	}
	
	private static void assembleAttributes(List<Subcommand> taskAttributes) {
		
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
			
			default:
				break;
			
			}

		}
		
	}
	
	private static void setTaskEnd(String contents) {
		taskEnd = Date.determineDate(contents);
	}

	private static void setTaskStart(String contents) {
		taskStart = Date.determineDate(contents);
	}

	private static void setTaskNote(String contents) {
		taskNote = contents;
	}

	private static void setTaskLocation(String contents) {
		taskLocation = contents;
	}

	private static void setTaskCategory(String contents) {
		taskCategory = contents;
	}

	private static void setTaskName(String contents) {
		taskName = contents;
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
			default :
				throw new IllegalArgumentException("invalid subcommand");
			}
		}
		
		checkForNoDuplicateSubcommands();
	}

}
