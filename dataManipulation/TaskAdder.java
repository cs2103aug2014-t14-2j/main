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
	
	private static String taskName = null;
	private static String taskCategory = null;
	private static String taskLocation = null;
	private static String taskNote = null;
	private static Date taskStart = null;
	private static Date taskEnd = null;
	
	public static Task add(List<CommandComponent> taskAttributes) throws Exception {
		
		Task newTask = buildTask(taskAttributes);
		
		assert ezC.totalTaskList != null;
		
		if(isDuplicate(newTask)) {	// If the task list already contains this task, throw an error
			throw new Exception("Duplicate Task Found");
		}

		else {
			ezC.totalTaskList.add(newTask);
			Collections.sort(ezC.totalTaskList, new globalClasses.sortTaskByEndDate());
			FileIo IoStream = new FileIo();
			IoStream.rewriteFile(ezC.totalTaskList);
			return newTask;
		}

	}
	
	private static boolean isDuplicate(Task toCheck) {
		
		for(Task t : ezC.totalTaskList) {
			if(t.getName().equals(toCheck.getName())) {
				return true;
			}
		}
		
		return false;
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
	
	private static void assembleAttributes(List<CommandComponent> taskAttributes) {
		
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
