package powerSearch;


import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.Subcommand;
import dataManipulation.TotalTaskList;

import java.util.ArrayList;
import java.util.List;

public class ExactMatchSearcher {
	public static List<Task> taskList;
	public static List<Task> exactSearch(Subcommand key, List<Task> list) throws Exception {
		taskList = list;
		ArrayList<Task> answer = new ArrayList<Task>();
		switch (key.getType()) {
		case NAME:
			answer.add(simpleSearchName(key.getContents()));
			return answer;
		case TITLE:
			answer.add(simpleSearchName(key.getContents()));
			return answer;
		case CATEGORY:
			return simpleSearchCategory(key.getContents());
		case LOCATION:
			return simpleSearchLocation(key.getContents());
		case NOTE:
			return simpleSearchNote(key.getContents()); //returns all Tasks that have the key as a substring within Notes
		case END:
			return simpleSearchEndDate(key.getContents()); 
		case START:
			return simpleSearchStartDate(key.getContents()); 
		case DATE:
			return simpleSearchDate(key.getContents());
		default :
			throw new 
			IllegalArgumentException("invalid subcommand for search");
		}
	}

	private static Task simpleSearchName(String key){
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().equals(taskList.get(i).getName().toLowerCase())) {
				return taskList.get(i);
			}
		}
		return null;
	}

	private static ArrayList<Task> simpleSearchCategory(String key) {
		ArrayList<Task> answer = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().equals(taskList.get(i).getCategory().toLowerCase())) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty()){
			return null;
		}
		else
			return answer;
	}

	private static ArrayList<Task> simpleSearchLocation(String key){
		ArrayList<Task> answer = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().equals(taskList.get(i).getLocation().toLowerCase())) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty())
			return null;

		else
			return answer;
	}

	private static ArrayList<Task> simpleSearchNote(String key) {
		ArrayList<Task> answer = new ArrayList<Task>();
		key = key.toLowerCase();
		for (int i = 0; i < taskList.size(); ++i) {
			if (taskList.get(i).getNote().toLowerCase().contains(key.subSequence(0, key.length()))) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty())
			return null;

		else
			return answer;
	}

	private static ArrayList<Task> simpleSearchDate(String comm) {
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		Date lookfordate = Date.determineDate(comm); //create the Date class which he is looking for

		int i;
		for(i=0; i<taskList.size(); i++){
			if(lookfordate.isEqual(taskList.get(i).getEndDate())){
				tasksedited.add(taskList.get(i));
			}
			if(lookfordate.isEqual(taskList.get(i).getStartDate())){
				tasksedited.add(taskList.get(i));
			}
		}
		if(tasksedited.isEmpty())
			return null;

		else
			return tasksedited;
	}


	private static ArrayList<Task> simpleSearchEndDate(String comm) {
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		Date lookfordate = Date.determineDate(comm); //create the Date class which he is looking for

		int i;
		for(i=0; i<taskList.size(); i++){
			if(lookfordate.isBefore(taskList.get(i).getEndDate())){
				tasksedited.add(taskList.get(i)); //supposed to show all the tasks that have an endDate after the date searched for
			}
		}
		if(tasksedited.isEmpty())
			return null;

		else
			return tasksedited;
	}

	private static ArrayList<Task> simpleSearchStartDate(String comm) {
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		Date lookfordate = Date.determineDate(comm); //create the Date class which he is looking for

		int i;
		for(i=0; i<taskList.size(); i++){
			if(lookfordate.isEqual(taskList.get(i).getStartDate())){
				tasksedited.add(taskList.get(i)); //supposed to show all the tasks that have an endDate after the date searched for
			}
		}
		if(tasksedited.isEmpty())
			return null;

		else
			return tasksedited;
	}

	public static boolean isTaskDuplicate(Task taskToCheck) {
		for(Task t : TotalTaskList.getInstance().getList()) {
			if(t.getName().toLowerCase().equals(taskToCheck.getName().toLowerCase())) {
				if(t.getCategory().toLowerCase().equals(taskToCheck.getCategory().toLowerCase())) {
					if(t.getLocation().toLowerCase().equals(taskToCheck.getLocation().toLowerCase())) {
						if(t.getNote().toLowerCase().equals(taskToCheck.getLocation().toLowerCase())) {
							if(t.getStartDate().toString().toLowerCase().equals(taskToCheck.getStartDate().toString().toLowerCase())) {
								if(t.getEndDate().toString().toLowerCase().equals(taskToCheck.getEndDate().toString().toLowerCase())) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}
