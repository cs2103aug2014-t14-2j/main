package dataManipulation;

import globalClasses.Task;
import globalClasses.ezC;

public class TaskRemover {
	// YUI WEI
	
	public static Task doDeleteTask(Task taskToDelete) {
		ezC.totalTaskList.remove(toDelete);
		IoStream.rewriteFile(totalTaskList);

		//search through list (call ExactSearch)
		//if found, remove
		//else call print error message function.
	}
	
	// YUI WEI
	public static void confirmDeleteTask(Task deletedTaskToPrintToUser) {
				System.out.println(deleted.toString + "has been deleted.");
	}

	//other things to maybe add:
	//	+ delete by category
	//	+ delete by keyword
}
