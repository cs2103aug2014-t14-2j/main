package powerSearch;

import globalClasses.Task;

import java.util.List;

public class ExactMatchSearcher extends Searcher {

	public static Task find(String name) {
		Task foundInTotalTaskList = new Task("name", "date"); //TOTALLY WRONG PLS FIX
		return foundInTotalTaskList;
	}
	
	public static boolean isExactMatch(Task taskToSearch) {	// Require this method for TaskEditor() method
		
		return true;
		
	}
}
