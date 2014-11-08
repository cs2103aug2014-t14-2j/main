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
	
	public void addNotCompleted(Task newTask) {
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
	
	public void removeNotCompleted(Task badTask) {
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
	
	public List<Task> getNotCompleted() {
		List<Task> totalNotComplete = new ArrayList<Task>();
		totalNotComplete.addAll(notCompleted);
		totalNotComplete.addAll(overdue);
		return totalNotComplete;
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
		List<Task> totalList = new ArrayList<Task>();
		totalList.addAll(overdue);
		totalList.addAll(notCompleted);
		totalList.addAll(completed);
		return totalList;
	}

	public void update() throws Exception {
		for (int i = 0; i < notCompleted.size(); ++i) {
			if (notCompleted.get(i).isOverdue()) {
				overdue.add(notCompleted.get(i));
				notCompleted.remove(i);
			}
		}
	}

}
