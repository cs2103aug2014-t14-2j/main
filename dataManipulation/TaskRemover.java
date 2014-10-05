package dataManipulation;


import java.util.*;
import powerSearch.ExactMatchSearcher;
import globalClasses.*;
import userInterface.ezCMessages;

public class TaskRemover {
	// YUI WEI
	private static String nameToRemove; 
	private static Task taskToRemove;
	private static Task taskRemoved;
	
	public static Task remove(List<CommandComponent> cc) {
		assert(cc.size() == 1);
		nameToRemove = cc.get(0).getContents();
		taskToRemove = ExactMatchSearcher.find(nameToRemove);
		taskRemoved = taskToRemove;
		doDeleteTask(taskToRemove);
		return taskRemoved;
		
	}
	public static void doDeleteTask(Task toRemove) {
		ezC.totalTaskList.remove(toRemove);	
	}
	
	//other things to maybe add:
	//	+ delete by category
	//	+ delete by keyword
}
