package dataManipulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import dataEncapsulation.Task;
import dataEncapsulation.sortTaskByEndDate;

public class TotalTaskList {
	private List<Task> overdue;	// List of overdue tasks
	private List<Task> completed;	// List of completed tasks
	private List<Task> notCompleted;	// List of uncompleted, not yet overdue tasks
	private static TotalTaskList ttl;
	
	private TotalTaskList() {
		notCompleted = new ArrayList<Task>();
		overdue = new ArrayList<Task>();
		completed = new ArrayList<Task>();
	}
	
	public static TotalTaskList getInstance() {
		if (ttl == null) {
			ttl= new TotalTaskList();
		}
		
		return ttl;
	}
	
	public void add(Task newTask) {
		notCompleted.add(newTask);
		Collections.sort(notCompleted, new sortTaskByEndDate());
	}
	
	public void addCompleted(Task doneTask) {
		completed.add(doneTask);
		Collections.sort(completed, new sortTaskByEndDate());
	}
	
	public void addOverdue(Task smellyTask) {
		overdue.add(smellyTask);
		Collections.sort(overdue, new sortTaskByEndDate());
	}
	
	public void remove(Task badTask) {
		notCompleted.remove(badTask);
	}
	
	public void removeCompleted(Task badDoneTask) {
		completed.remove(badDoneTask);
	}
	
	public void removeOverdue(Task badSmellyTask) {
		overdue.remove(badSmellyTask);
	}
	
	public void addAll(Collection<Task> tasks) {
		notCompleted.addAll(tasks);
	}
	
	public List<Task> getList() {
		return notCompleted;
	}
	
	public List<Task> getCompleted() {
		return completed;
	}
	
	public List<Task> getOverdue() {
		return overdue;
	}
	
	public List<Task> getAllTasks() {
		List<Task> totalList = overdue;
		totalList.addAll(notCompleted);
		totalList.addAll(completed);
		return totalList;
	}

}
