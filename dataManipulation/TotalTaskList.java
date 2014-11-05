package dataManipulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import dataEncapsulation.Task;
import dataEncapsulation.sortTaskByEndDate;

public class TotalTaskList {
	private ArrayList<Task> overdue;	// List of overdue tasks
	private ArrayList<Task> completed;	// List of completed tasks
	private ArrayList<Task> list;	// List of uncompleted, not yet overdue tasks
	private static TotalTaskList ttl;
	
	private TotalTaskList() {
		list = new ArrayList<Task>();
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
		list.add(newTask);
		Collections.sort(list, new sortTaskByEndDate());
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
		list.remove(badTask);
	}
	
	public void removeCompleted(Task badDoneTask) {
		completed.remove(badDoneTask);
	}
	
	public void removeOverdue(Task badSmellyTask) {
		overdue.remove(badSmellyTask);
	}
	
	public void addAll(Collection<Task> tasks) {
		list.addAll(tasks);
	}
	
	public ArrayList<Task> getList() {
		return list;
	}
	
	public ArrayList<Task> getCompleted() {
		return completed;
	}
	
	public ArrayList<Task> getOverdue() {
		return overdue;
	}

}
