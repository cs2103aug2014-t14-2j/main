package powerSearch;

import globalClasses.CommandComponent;
import globalClasses.Task;
import globalClasses.Date;
import java.util.ArrayList;
import java.util.List;

public class ExactMatchSearcher {
	public static List<Task> taskList;
	public static ArrayList<Task> exactSearch(CommandComponent key, List<Task> list) throws Exception {
		taskList = list;
		ArrayList<Task> answer = new ArrayList<Task>();
		switch (key.getType()) {
			case NAME :
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
			default :
				throw new 
				IllegalArgumentException("invalid subcommand for search");
		}
	}
	
	private static Task simpleSearchName(String key) throws Exception {
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().equals(taskList.get(i).getName().toLowerCase())) {
				return taskList.get(i);
			}
		}
		throw new Exception("no matches found");
	}
	
	private static ArrayList<Task> simpleSearchCategory(String key) throws Exception {
		ArrayList<Task> answer = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().equals(taskList.get(i).getCategory().toLowerCase())) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty()){
		throw new Exception("no matches found");
		}
		else
			return answer;
	}
	
	private static ArrayList<Task> simpleSearchLocation(String key) throws Exception {
		ArrayList<Task> answer = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().equals(taskList.get(i).getLocation().toLowerCase())) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty())
			throw new Exception("no matches found");
		
		else
			return answer;
	}
	
	private static ArrayList<Task> simpleSearchNote(String key) throws Exception {
		ArrayList<Task> answer = new ArrayList<Task>();
		key = key.toLowerCase();
		for (int i = 0; i < taskList.size(); ++i) {
			if (taskList.get(i).getNote().toLowerCase().contains(key.subSequence(0, key.length()))) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty())
			throw new Exception("no matches found");
		
		else
			return answer;
	}
	
	private static ArrayList<Task> simpleSearchEndDate(String comm) throws Exception {
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		String datesearch = comm;
		
		datesearch = (datesearch.toLowerCase()).trim();
		int day = Integer.valueOf(comm.substring(0,2));
		int month = Integer.valueOf(comm.substring(3,5));
		int year = Integer.valueOf(comm.substring(6,10));
		Date lookfordate = new Date(day, month, year); //create the Date class which he is looking for
		
		int i;
		for(i=0; i<taskList.size(); i++){
			if(lookfordate.isEqual(taskList.get(i).getEndDate())){
				tasksedited.add(taskList.get(i)); //supposed to show all the tasks that have an endDate after the date searched for
			}
		}
		if(tasksedited.isEmpty())
			throw new Exception("no matches found");
		
		else
			return tasksedited;
	}
	
	private static ArrayList<Task> simpleSearchStartDate(String comm) throws Exception {
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		String datesearch = comm;
		
		datesearch = (datesearch.toLowerCase()).trim();
		int day = Integer.valueOf(comm.substring(0,2));
		int month = Integer.valueOf(comm.substring(3,5));
		int year = Integer.valueOf(comm.substring(6,10));
		Date lookfordate = new Date(day, month, year); //create the Date class which he is looking for
		
		int i;
		for(i=0; i<taskList.size(); i++){
			if(lookfordate.isEqual(taskList.get(i).getStartDate())){
				tasksedited.add(taskList.get(i)); //supposed to show all the tasks that have an endDate after the date searched for
			}
		}
		if(tasksedited.isEmpty())
			throw new Exception("no matches found");
		
		else
			return tasksedited;
	}
	
}
