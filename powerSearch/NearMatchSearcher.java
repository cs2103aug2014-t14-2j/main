package powerSearch;

import dataEncapsulation.Task;
import dataManipulation.Subcommand;
import dataManipulation.TotalTaskList;

import java.util.ArrayList;
import java.util.List;

public class NearMatchSearcher{
	public static List<Task> taskList;
	public static List<Task> nearSearch(Subcommand key, List<Task> list) throws Exception {
		taskList = list;
		List<Task> answer = new ArrayList<Task>();
		switch (key.getType()){
		case NAME :
			answer.add(nearSearchName(key.getContents()));
			return answer;
		case CATEGORY:
			return nearSearchCategory(key.getContents());
		case LOCATION:
			return nearSearchLocation(key.getContents());
		default:
			break;
		}
		return answer;
	}
	
	private static Task nearSearchName(String key) throws Exception {
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().compareTo(taskList.get(i).getName().toLowerCase()) <= 25) {
				return taskList.get(i);
			}
		}
		throw new Exception("no matches found");
	}
	
	private static List<Task> nearSearchCategory(String key) throws Exception {
		List<Task> answer = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().compareTo(taskList.get(i).getCategory().toLowerCase()) <= 25) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty()){
		throw new Exception("no matches found");
		}
		else
			return answer;
	}
	
	private static List<Task> nearSearchLocation(String key) throws Exception {
		List<Task> answer = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().compareTo(taskList.get(i).getLocation().toLowerCase()) <= 25) {
				answer.add(taskList.get(i));
			}
		}
		if(answer.isEmpty())
			throw new Exception("no matches found");
		
		else
			return answer;
	}
	
	public static boolean isTaskDuplicate(Task taskToCheck) {
		for(Task t : TotalTaskList.getInstance().getList()) {
			if(t.getName().toLowerCase().equals(taskToCheck.getName().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
}
