package dataManipulation;

import globalClasses.Task;

import java.util.ArrayList;

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
	
	

}
