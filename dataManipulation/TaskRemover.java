package dataManipulation;

import fileIo.FileIo;
import globalClasses.CommandComponent;

import java.util.*;

import dataEncapsulation.Task;
import dataEncapsulation.ezC;
import powerSearch.ExactMatchSearcher;

public class TaskRemover {
	// YUI WEI
	private static ArrayList<Task> tasksFound;
	private static Task taskToRemove;
	private static Task taskRemoved;
	
	public static Task remove(List<Subcommand> cc) throws Exception {
		assert(cc.size() == 1);
		tasksFound = ExactMatchSearcher.exactSearch(cc.get(0), TotalTaskList.getInstance());
		if (tasksFound.size() > 1) throw new Exception("More than one result from search.");
		taskToRemove = tasksFound.get(0) ;
		taskRemoved = taskToRemove;
		doDeleteTask(taskToRemove);
		return taskRemoved;
		
	}
	public static void doDeleteTask(Task toRemove) {
		TotalTaskList.getInstance().remove(toRemove);
		
		FileIo stream = FileIo.getInstance();
		TotalTaskList.getInstance().remove(toRemove);
		stream.rewriteFile();
	}
	
	//other things to maybe add:
	//	+ delete by category
	//	+ delete by keyword
}
