package powerSearch;

import java.util.Iterator;
import java.util.List;

import globalClasses.CommandComponent;
import globalClasses.Task;

public class Searcher {
	public List<Task> list;
	
	/**
	 * This may need to be a pointer. I'm under the impression that
	 * java will not create shallow copies unless you force it to,
	 * but I could be wrong. We want every component to be able to edit this
	 * @param initialList
	 */
	public Searcher(List<Task> initialList) {
		list = initialList;
	}
	
	/**
	 * searches for exact matches to search key
	 * @param key
	 * @return a list of all matches for that key
	 */
	public List<Task> search(CommandComponent key) {
		return null;
	}
	
	/**
	 * Searches for an exact match to a given task.
	 * Assumes that only one of that task exists (returns forst match)
	 * Iterator returned for easy deletion/replacement of task
	 * @param key
	 * @return
	 */
	public Iterator<Task> search(Task key) {
		return null;
	}
}
