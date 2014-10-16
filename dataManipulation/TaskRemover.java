package dataManipulation;

import fileIo.FileIo;
import globalClasses.CommandComponent;
import globalClasses.Task;
import globalClasses.ezC;
import java.util.*;

import java.util.List;

import powerSearch.ExactMatchSearcher;

public class TaskRemover {
	// YUI WEI
	private static ArrayList<Task> tasksFound;
	private static Task taskToRemove;
	private static Task taskRemoved;
	
	public static Task remove(List<CommandComponent> cc) throws Exception {
		assert(cc.size() == 1);
		tasksFound = ExactMatchSearcher.exactSearch(cc.get(0), ezC.totalTaskList);
		if (tasksFound.size() > 1) throw new Exception("More than one result from search.");
		taskToRemove = tasksFound.get(0) ;
		taskRemoved = taskToRemove;
		doDeleteTask(taskToRemove);
		return taskRemoved;
		
	}
	public static void doDeleteTask(Task toRemove) {
		ezC.totalTaskList.remove(toRemove);
		
		FileIo stream = new FileIo();
		ezC.totalTaskList.remove(toRemove);
		stream.rewriteFile(ezC.totalTaskList);
	}
	
	//other things to maybe add:
	//	+ delete by category
	//	+ delete by keyword
}
