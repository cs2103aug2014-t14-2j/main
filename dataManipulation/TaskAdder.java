package dataManipulation;

import java.util.List;

import fileIo.FileIo;
import globalClasses.Task;

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
	public static void doAddTask(String contentsToAdd, FileIo IoStream) {
		
		/*Task toBeAdded = new Task();
		if(!ezC.totalTaskList.contains(toBeAdded)) {	// If the list doesn't contain this task, add it
			totalTaskList.add(toBeAdded);
		}
		IoStream.rewriteFile(totalTaskList);*/
	}
}
