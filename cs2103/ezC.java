package cs2103;

import java.util.Collections;
import java.util.Date;
import java.util.List;
//
public class ezC {
	
	// NATALIE - FileIoStream relies on your pre-written FileIoStream Class?
	static List<Task> totalTaskList;
	String fileName;
	FileIoStream fileStream (given fileName)
	List<String> Read();
	
	// YUI WEI
	public static Date determineDate(String dateString) {
		return null;
	}
	
	// NATALIE
	public static List<Task> decipherFileContents(List<String> fileContent) {
		
	}
	
	// VERNON
	public static Task extractTask(String userInput) {
		
	}
	
	// NELSON
	// Add task to overall list
	// Keep list AND file sorted by deadline
	// Deny duplicates to be added 
	public static void doAddTask(Task toBeAdded) {
		
		if(!totalTaskList.contains(toBeAdded)) {	// If the list doesn't contain this task, add it
			totalTaskList.add(toBeAdded);
		}
		Collections.sort(totalTaskList);	// Task class needs to implement comparable and do an overriding method
								// for compareTo() to sort by name/deadline/etc.
								// Maybe have another method to purely deal with sorting?
		fileStream.write(totalTaskList);	// Using Natalie's FileIO class to write the task list back
											// to the file
	}
	
	// NELSON
	// Print a message to user indicating task X was added
	public static void confirmAddTask(Task addedTaskToPrintToUser) {
		
		System.out.println(addedTaskToPrintToUser.getName() + " has been added to your task list.");	// Should I throw this to a specific print funtion? Seems retarded
		
	}
	
	// NELSON
	// Locate task to be edited within list of tasks
	// Replace specified parts (How to determine what to edit?)
	// Need to edit Task Object AND actual file
	// Returns a copy of edited task (Task Object)
	// Should REJECT DUPLICATE entries
	public static Task doEditTask(Task toEdit, String editedContent) {
		
		if(totalTaskList.contains(toEdit)) {
			Task editedTask = totalTaskList.get(totalTaskList.indexOf(toEdit));	// Gets the task that we want to edit from the task list
			// What am I editing here?
		}
		else {
			System.out.println("The task \"" + toEdit.getName() + "\" that you are trying to edit does not exist in the task list.");	// Should this be in a separate method too?
		}
		
	}
	
	// NELSON
	// Prints the task that got edited
	public static void confirmEditTask(Task oldTask, Task newTask) {
		
		System.out.println("Your task \"" + newTask.getName() + "\" has been edited.");
		
	}
	
	// YUI WEI
	public static List<Task> findTask(String taskName) {
		
	}
	
	// VERNON
	public static Task narrowSearch(List<Task> narrowedTaskList) {
		
	}
	
	// YUI WEI
	public static Task doDeleteTask(Task taskToDelete) {
		
	}
	
	// YUI WEI
	public static void confirmDeleteTask(Task deletedTaskToPrintToUser) {
		
	}
	
	// VERNON
	public static void doShowAll() {
		
	}
	
	// VERNON
	public static void doShowToday() {
		
	}
}
