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
	
	/*
	 * Given a list of command components, to search for, returns a list
	 * of matches.
	 * 
	 * For a list of possible command components, see the header to the command
	 * class under "SEARCH"
	 */
	private static List<Task> search(List<CommandComponent> list) {
		return null;
	}
	
	/*
	 * Looks at every component in a task and checks whether or not the two are
	 * exactly the same. Is not case sensitive.
	 */
	public static boolean isTaskDuplicate(Task taskToCheck) {
		
		for(Task t : ezC.totalTaskList) {
			if(t.getName().toLowerCase().equals(taskToCheck.getName().toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
}