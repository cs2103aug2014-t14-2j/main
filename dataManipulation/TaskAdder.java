package dataManipulation;

import java.util.Collections;

import userInterface.ezCMessages;
import fileIo.FileIo;
import globalClasses.Task;
import globalClasses.ezC;

public class TaskAdder {
	
	// NELSON
	// Add task to overall list
	// Keep list AND file sorted by deadline
	// Deny duplicates to be added
	/**
	 * 
	 * @param toBeAdded the task which will be added to the list
	 * @param totalTaskList	the list to add the task to
	 * @param IoStream The stream to rewrite the file afterwards
	 * @return the task which was added to the list
	 */
	public static void doAddTask(Task newTask, FileIo IoStream) {
		if(!ezC.totalTaskList.contains(newTask)) {	// If the list doesn't contain this task, add it
			ezC.totalTaskList.add(newTask);
			Collections.sort(ezC.totalTaskList, new globalClasses.sortTask());
			IoStream.rewriteFile(ezC.totalTaskList);
		}
		
		else {
			ezCMessages.printErrorMessage("duplicate add task");
		}
		
	}
	
}
