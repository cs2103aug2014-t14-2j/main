package dataManipulation;

import fileIo.FileIo;
import globalClasses.CommandComponent;
import globalClasses.Task;
import globalClasses.ezC;

import java.util.List;

import powerSearch.ExactMatchSearcher;

public class TaskRemover {
	// YUI WEI
	private static Task taskToRemove;
	private static Task taskRemoved;
	
	public static Task remove(List<CommandComponent> cc) throws Exception{
		assert(cc.size() == 1);
		taskToRemove = ExactMatchSearcher.simpleSearch(cc.get(0), ezC.totalTaskList);
		taskRemoved = taskToRemove;
		doDeleteTask(taskToRemove);
		return taskRemoved;
		
	}
	public static void doDeleteTask(Task toRemove) {
		FileIo stream = new FileIo();
		ezC.totalTaskList.remove(toRemove);
		stream.rewriteFile(ezC.totalTaskList);
	}
	
	//other things to maybe add:
	//	+ delete by category
	//	+ delete by keyword
}
