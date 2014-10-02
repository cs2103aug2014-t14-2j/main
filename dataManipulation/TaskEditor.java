package dataManipulation;

import java.util.List;

import globalClasses.Task;

public class TaskEditor {
	// NELSON
	// Locate task to be edited within list of tasks
	// Replace specified parts (How to determine what to edit?)
	// Need to edit Task Object AND actual file
	// Returns a copy of edited task (Task Object)
	// Should REJECT DUPLICATE entries
	public static Task doEditTask(Task toEdit, String editedContent, List<Task> totalTaskList) {

		if(totalTaskList.contains(toEdit)) {
			Task editedTask = totalTaskList.get(totalTaskList.indexOf(toEdit));	// Gets the task that we want to edit from the task list
			// What am I editing here?
		}
		else {
			System.out.println("The task \"" + toEdit.getName() + "\" that you are trying to edit does not exist in the task list.");	// Should this be in a separate method too?
		}

		return null;
	}
}
