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
	public static Task doAddTask(Task toBeAdded, List<Task> totalTaskList, FileIo IoStream) {

		if(!totalTaskList.contains(toBeAdded)) {	// If the list doesn't contain this task, add it
			totalTaskList.add(toBeAdded);
		}
		//Collections.sort(totalTaskList);	// Task class needs to implement comparable and do an overriding method
		// for compareTo() to sort by name/deadline/etc.
		// Maybe have another method to purely deal with sorting?
		IoStream.rewriteFile(totalTaskList);	// Using Natalie's FileIO class to write the task list back
		// to the file
		
		return null;
	}
}
