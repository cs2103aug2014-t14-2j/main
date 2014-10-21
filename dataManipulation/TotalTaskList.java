package dataManipulation;

import globalClasses.Task;
import globalClasses.ezC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TotalTaskList {
	private ArrayList<Task> list;
	private static TotalTaskList ttl;
	
	private TotalTaskList() {
		list = new ArrayList<Task>();
	}
	
	public static TotalTaskList getInstance() {
		if (ttl == null) {
			ttl= new TotalTaskList();
		}
		
		return ttl;
	}
	
	public void add(Task newTask) {
		list.add(newTask);
		Collections.sort(list, new globalClasses.sortTaskByEndDate());
	}
	
	public void remove(Task badTask) {
		list.remove(badTask);
	}
	
	public void addAll(Collection<Task> tasks) {
		list.addAll(tasks);
	}
	
	public ArrayList<Task> getList() {
		return list;
	}

}