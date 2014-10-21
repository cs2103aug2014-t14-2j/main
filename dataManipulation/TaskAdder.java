package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import fileIo.FileIo;

public class TaskAdder {
	
	/**
	 * 
	 * For the add method:
	 * @param newTask: the task which will be added to the list
	 * @param totalTaskList	the list to add the task to
	 * @param IoStream The stream to rewrite the file afterwards
	 * @return newTask if task can be added, throws an exception if it cannot be.
	 * @throws Exception is returned if duplicate task exists
	 */
	
	private static String taskName = null;
	private static String taskCategory = null;
	private static String taskLocation = null;
	private static String taskNote = null;
	private static Date taskStart = null;
	private static Date taskEnd = null;
	private static TotalTaskList taskList = TotalTaskList.getInstance();
	private static TaskFactory makeMyTask = TaskFactory.getInstance();
	
	public static Task add(List<Subcommand> taskAttributes) throws Exception {
		
		Task newTask = buildTask(taskAttributes);
		
		if(ExactMatchSearcher.isTaskDuplicate(newTask)) {	// If the task list already contains this task, throw an error
			throw new Exception("Duplicate Task Found");
		}

		else {
			taskList.add(newTask);
			FileIo IoStream = FileIo.getInstance();
			IoStream.rewriteFile();
			return newTask;
		}

	}
		
	/** For the buildTask method:
	 * 
	 * @param taskAttributes
	 * @return the task toBeAdded
	 */
	
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
	
	/** For the aseembleAttributes method:
	 * 
	 * @param taskAttributes
	 */
	
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
	
}
