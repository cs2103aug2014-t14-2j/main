package globalClasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Date;
// test for yui wei
import java.util.List;

import userInterface.UserInterface;
import userInterface.ezCMessages;
import fileIo.FileIo;
import fileIo.TaskFileReader;
import fileIo.TextIoStream;

public class ezC {
	
	private static List<Task> totalTaskList;
	private static FileIo IoStream = new FileIo();
	
	// YUI WEI
	// note from Nat: Could we put this in a separate class so other functions
	// can call it? Kinda like TaskFactory?
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
	
	// this should probably be made into method in the Date class.
	// Nat: Month should also be an enum
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
	
	// VERNON
	public static Task extractTask(String userInput) {
		return null;
	}
	
	// NELSON
	// Add task to overall list
	// Keep list AND file sorted by deadline
	// Deny duplicates to be added 
	public static void doAddTask(Task toBeAdded) {
		
		if(!totalTaskList.contains(toBeAdded)) {	// If the list doesn't contain this task, add it
			totalTaskList.add(toBeAdded);
		}
		Collections.sort(totalTaskList, new sortTask());	// Task class needs to implement comparable and do an overriding method
								// for compareTo() to sort by name/deadline/etc.
								// Maybe have another method to purely deal with sorting?
		IoStream.rewriteFile(totalTaskList);	// Using Natalie's FileIO class to write the task list back
											// to the file
	}
	
	// NELSON
	// Print a message to user indicating task X was added
	public static void confirmAddTask(Task addedTaskToPrintToUser) {
		
		System.out.println(addedTaskToPrintToUser.getName() + " has been added to your task list.");	// Should I throw this to a specific print funtion? Seems retarded
		
	}
	
	// NELSON
	// Locate task to be edited within list of tasks
	// Need to edit Task Object AND actual file
	// Returns a copy of edited task (Task Object)
	// Should REJECT DUPLICATE entries
	public static void doEditTask(Task toEdit, String editedContent) {
		
		if(totalTaskList.contains(toEdit)) {
			
			String taskSplit[] = editedContent.split(" ", 2);	//	Splits the string of the content into 2 parts: Which part to edit and the actual new content
			String editedComponent = taskSplit[0];
			String actualEditedContent = taskSplit[1];
			
			Task editedTask = totalTaskList.get(totalTaskList.indexOf(toEdit));	// Gets the task that we want to edit from the task list
			
			if(editedComponent.toLowerCase().equals("name")) {
				editedTask.setName(actualEditedContent);
				if(isExactMatch(editedTask)) {	// Requires Kash's exact match method
					printErrorMessage("duplicate task");	//	Throws an error message is duplicate task found, need a separate method for error message printing
				}
				else {
					totalTaskList.remove(toEdit);
					totalTaskList.add(editedTask);
					Collections.sort(totalTaskList, new sortTask());
				}
			}
			else if(editedComponent.toLowerCase().equals("location")) {
				editedTask.setLocation(actualEditedContent);
				if(isExactMatch(editedTask)) {
					printErrorMessage("duplicate task");
				}
				else {
					addEditedTask(toEdit, editedTask);
				}
			}
			else if(editedComponent.toLowerCase().equals("notes")) {
				editedTask.setNote(actualEditedContent);
				if(isExactMatch(editedTask)) {
					printErrorMessage("duplicate task");
				}
				else {
					addEditedTask(toEdit, editedTask);
				}
			}
			else if(editedComponent.toLowerCase().equals("category")) {
				editedTask.setCategory(actualEditedContent);
				if(isExactMatch(editedTask)) {
					printErrorMessage("duplicate task");
				}
				else {
					addEditedTask(toEdit, editedTask);
				}
			}
			else if(editedComponent.toLowerCase().equals("markascompleted")) {
				editedTask.setCompleted(true);
			}
			else if(editedComponent.toLowerCase().equals("date")) {
				
			}
		}
		else {
			printErrorMessage("404");
		}
		
	}
	
	// NELSON
	// Prints the task that got edited
	public static void confirmEditTask(Task oldTask, Task newTask) {
		
		System.out.println("Your task \"" + newTask.getName() + "\" has been edited.");
		
	}
	
	// YUI WEI
	public static List<Task> findTask(String taskName) {
		List<Task> matches = new ArrayList<Task>();
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
		return null;
	}
	
	// YUI WEI
	public static Task doDeleteTask(Task taskToDelete) {
		totalTaskList.remove(taskToDelete);			
		return null;
	}
	
	// YUI WEI
	public static void confirmDeleteTask(Task deletedTaskToPrintToUser) {
				System.out.println(deletedTaskToPrintToUser.toString() + "has been deleted.");	
	}
	
	// VERNON
	public static void doShowAll() {
		
	}
	
	// VERNON
	public static void doShowToday() {
		
	}
	
	public static void main(String[] args) {
		FileIo.initializeTaskList(totalTaskList);
		UserInterface.welcomeUser();
	}
}
