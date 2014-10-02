package dataManipulation;

import java.util.Collections;
import java.util.List;

import userInterface.ezCMessages;
import globalClasses.Date;
import globalClasses.Task;
import globalClasses.ezC;
import globalClasses.sortTask;

public class TaskEditor {
		// NELSON
		// Locate task to be edited within list of tasks
		// Need to edit Task Object AND actual file
		// Returns a copy of edited task (Task Object)
		// Should REJECT DUPLICATE entries
		public static void doEditTask(String editedContent) {
			
			// Not sure if taking in a list of command components... Will update later
			
			if(ezC.totalTaskList.contains(toEdit)) {
				
				String taskSplit[] = editedContent.split(" ", 2);	//	Splits the string of the content into 2 parts: Which part to edit and the actual new content
				String editedComponent = taskSplit[0];
				String actualEditedContent = taskSplit[1];
				
				Task editedTask = ezC.totalTaskList.get(ezC.totalTaskList.indexOf(toEdit));	// Gets the task that we want to edit from the task list
				
				if(editedComponent.toLowerCase().equals("name")) {
					editedTask.setName(actualEditedContent);
					if(isExactMatch(editedTask)) {	// Requires Kash's exact match method
						ezCMessages.printErrorMessage("duplicate task");	// Returns the error message string (Need to call the output method)
					}
					else {
						addEditedTask(toEdit, editedTask);
						ezCMessages.printConfirmEdited(editedTask.getName());
					}
				}
				else if(editedComponent.toLowerCase().equals("location")) {
					editedTask.setLocation(actualEditedContent);
					if(isExactMatch(editedTask)) {
						ezCMessages.printErrorMessage("duplicate task");
					}
					else {
						addEditedTask(toEdit, editedTask);
						ezCMessages.printConfirmEdited(editedTask.getName());
					}
				}
				else if(editedComponent.toLowerCase().equals("notes")) {
					editedTask.setNote(actualEditedContent);
					if(isExactMatch(editedTask)) {
						ezCMessages.printErrorMessage("duplicate task");
					}
					else {
						addEditedTask(toEdit, editedTask);
						ezCMessages.printConfirmEdited(editedTask.getName());
					}
				}
				else if(editedComponent.toLowerCase().equals("category")) {
					editedTask.setCategory(actualEditedContent);
					if(isExactMatch(editedTask)) {
						ezCMessages.printErrorMessage("duplicate task");
					}
					else {
						addEditedTask(toEdit, editedTask);
						ezCMessages.printConfirmEdited(editedTask.getName());
					}
				}
				else if(editedComponent.toLowerCase().equals("markascompleted")) {
					editedTask.setCompleted(true);
					ezCMessages.printConfirmEdited(editedTask.getName());
				}
				else if(editedComponent.toLowerCase().equals("date")) {
					Date newDate = ezC.determineDate(actualEditedContent);
					editedTask.setEndDate(newDate);
					if(isExactMatch(editedTask)) {
						ezCMessages.printErrorMessage("duplicate task");
					}
					else {
						addEditedTask(toEdit, editedTask);
						ezCMessages.printConfirmEdited(editedTask.getName());
					}
				}
			}
			else {
				ezCMessages.printErrorMessage("404");
			}
			
		}
		
		// Adds the edited task to the total task list
		public static void addEditedTask(Task oldTask, Task newTask) {
			ezC.totalTaskList.remove(oldTask);
			ezC.totalTaskList.add(newTask);
			Collections.sort(ezC.totalTaskList, new globalClasses.sortTask());
		}
		
}