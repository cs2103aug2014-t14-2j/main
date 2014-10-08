package powerSearch;

import globalClasses.CommandComponent;
import globalClasses.Task;
import globalClasses.ezC;

import java.util.List;

public class ExactMatchSearcher {
	public static List<Task> taskList;
	
	public static Task simpleSearch(CommandComponent key, List<Task> list) throws Exception {
		taskList = list;
		
		switch (key.getType()) {
			case NAME :
				return simpleSearchName(key.getContents());
			default :
				throw new 
				IllegalArgumentException("invalid subcommand for search");
		}
	}
	
	private static Task simpleSearchName(String key) throws Exception {
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.equals(taskList.get(i).getName())) {
				return taskList.get(i);
			}
		}
		
		throw new Exception("no matches found");
	}
	
	public static boolean isExactMatch(Task taskToCheck) {
		
		for(Task t : ezC.totalTaskList) {
			
			if(t.equals(taskToCheck)) {
				return true;
			}
		}
		
		return false;
	}
}