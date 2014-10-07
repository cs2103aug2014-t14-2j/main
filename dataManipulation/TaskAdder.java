package dataManipulation;

import fileIo.FileIo;
import globalClasses.CommandComponent;
import globalClasses.Date;
import globalClasses.Task;
import globalClasses.ezC;

import java.util.Collections;
import java.util.List;

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
	
	private static String taskName = "";
	private static String taskCategory = "";
	private static String taskLocation = "";
	private static String taskNote = "";
	private static Date taskStart = null;
	private static Date taskEnd = null;
	
	public static Task add(List<CommandComponent> taskAttributes) throws Exception {

		Task newTask = buildTask(taskAttributes);

		if(!ezC.totalTaskList.contains(newTask)) {	// If the list doesn't contain this task, add it
			ezC.totalTaskList.add(newTask);
			Collections.sort(ezC.totalTaskList, new globalClasses.sortTaskByEndDate());
			FileIo IoStream = new FileIo();
			IoStream.rewriteFile(ezC.totalTaskList);
			return newTask;
		}

		else {
			throw new Exception("Duplicate Task Found");
		}

	}
	
	/** For the buildTask method:
	 * 
	 * @param taskAttributes
	 * @return the task toBeAdded
	 */
	
	public static Task buildTask(List<CommandComponent> taskAttributes) {
		
		assembleAttributes(taskAttributes);
		
		Task toBeAdded = TaskFactory.makeTask(taskName, taskCategory, taskLocation, taskNote, taskStart, taskEnd);
		
		return toBeAdded;
		
	}
	
	/** For the aseembleAttributes method:
	 * 
	 * @param taskAttributes
	 */
	
	public static void assembleAttributes(List<CommandComponent> taskAttributes) {
		
		for(CommandComponent cc : taskAttributes) {

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
		taskEnd = ezC.determineDate(contents);
	}

	private static void setTaskStart(String contents) {
		taskStart = ezC.determineDate(contents);
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
