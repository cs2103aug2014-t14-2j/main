package dataManipulation;

import java.util.Collections;
import java.util.List;

import userInterface.ezCMessages;
import fileIo.FileIo;
import globalClasses.Date;
import globalClasses.Task;
import globalClasses.ezC;
import globalClasses.sortTaskByEndDate;

public class TaskEditor {
		// NELSON
		// Locate task to be edited within list of tasks
		// Need to edit Task Object AND actual file
		// Returns a copy of edited task (Task Object)
		// Should REJECT DUPLICATE entries
		public static void doEditTask(Task toEdit, FileIo IoStream) {
			
			// Exact Match Searcher should throw back a null object if no match found 
			
			if(toEdit != null) {	// Do a check if the task object is null, if it is => no such task to edit
				
				//	Need to determine what to edit again...
				
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