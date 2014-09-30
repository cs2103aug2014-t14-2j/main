package cs2103;

import java.util.Date;
import java.util.List;
//
public class ezC {
	
	// NATALIE - FileIoStream relies on your pre-written FileIoStream Class?
	static List<Task> totalTaskList;
	String fileName;
	TextIoStream fileStream (given fileName)
	List<String> Read();
	
	// YUI WEI
	public static Date determineDate(String dateString) {
		String month, day, year;
		int mm, dd, yyyy;
		if (dateString.contains(",")) {
			int space = dateString.indexOf(' ');
			int comma = dateString.indexOf(',');
			month = dateString.substring(0, space);
			day = dateString.substring(space + 1, comma);
			year = dateString.substring(comma);
			mm = monthInteger(month);
		} else if (dateString.contains("/")) {
			String dateStr[] = dateString.split("/", 3);
			day = dateStr[0];
			month = dateStr[1];
			year = dateStr[2];
			mm = Integer.parseInt(month);
		} else {
			String dateStr[] = dateString.split(" ", 3);
			day = dateStr[0];
			month = dateStr[1];
			year = dateStr[2];
			mm = Integer.parseInt(month);
		}
		dd = Integer.parseInt(day);
		yyyy = Integer.parseInt(year);
		
		Date userDate = new Date(dd, mm, yyyy);
		return userDate;
	}
	//this should probably be made into method in the Date class.
	static int monthInteger(String month1) {
		int monthNum;
		switch (month1.toLowerCase()) {
			case "january" :		monthNum = 1; 				break;
			case "february":		monthNum = 2;				break;
			case "march":			monthNum = 3;				break;
			case "april":			monthNum = 4;				break;
			case "may":				monthNum = 5;				break;
			case "june":			monthNum = 6;				break;
			case "july":			monthNum = 7;				break;
			case "august":			monthNum = 8;				break;
			case "september":		monthNum = 9;				break;
			case "october":			monthNum = 10;				break;
			case "november":		monthNum = 11;				break;
			case "december":		monthNum = 12;				break;
			default: 				monthNum = 0;				break;
		}
		return monthNum;
	}


	// NATALIE
	public static List<Task> decipherFileContents(List<String> fileContent) {
		List<Task> taskList = TaskFileReader.getAllTasks(fileContent);
		return taskList;
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
		totalTaskList.sort();	// Task class needs to implement comparable and do an overriding method
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
		List<Task> matches = new List<Task>();
		for (int i=0; i<totalTaskList.size(); i++) {
			Task cur = totalTaskList.get(i);
			if (cur.getName().contains(taskName)) {
				matches.add(cur);
			}
		}
		if (matches.size() == 0) {
			System.out.println("None found."); //need a standard for this.
		}
		return matches;
	}
	
	// VERNON
	public static Task narrowSearch(List<Task> narrowedTaskList) {
		
	}
	
	// YUI WEI
	public static Task doDeleteTask(Task taskToDelete) {
		totalTaskList.remove(toDelete);
	}
	
	// YUI WEI
	public static void confirmDeleteTask(Task deletedTaskToPrintToUser) {
				System.out.println(deleted.toString + "has been deleted.");
	}
	
	// VERNON
	public static void doShowAll() {
		
	}
	
	// VERNON
	public static void doShowToday() {
		
	}
}
