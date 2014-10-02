package dataManipulation;

import java.util.List;

import globalClasses.Task;

public class TaskRemover {
	// YUI WEI
	public static Task doDeleteTask(Task taskToDelete, List<Task> totalTaskList) {
		totalTaskList.remove(taskToDelete);			
		return null;
	}
}
