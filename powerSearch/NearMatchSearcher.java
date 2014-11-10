	/**
	 * Performs an Near Search on a List<Task> based on different Keys given
	 * @return format: List<Task>
	 */

//@author A0108297X

package powerSearch;

import java.util.ArrayList;
import java.util.List;

import dataEncapsulation.Task;
import dataManipulation.Subcommand;
import dataManipulation.TotalTaskList;

public class NearMatchSearcher{
	public static List<Task> taskList;
	public static List<Task> nearSearch(Subcommand key, List<Task> list) throws Exception {
		taskList = list;
		List<Task> answer = new ArrayList<Task>();
		switch (key.getType()){
		case TITLE:
			answer.add(nearSearchName(key.getContents()));
			return answer;
		case CATEGORY:
			return nearSearchCategory(key.getContents());
		case LOCATION:
			return nearSearchLocation(key.getContents());
		case NOTE:
			return nearSearchNote(key.getContents());
		default:
			break;
		}
		return answer;
	}
	
	private static Task nearSearchName(String key){
		int length1, length2, diff;
		length2 = key.length();
		Task temp = null;
		for (int i = 0; i < taskList.size(); ++i) {
			length1 = taskList.get(i).getName().length();
			diff = Math.abs(length2 - length1);
			if (diff <= 2) {
				temp = taskList.get(i);
				return temp;
			}
		}
		return temp;
	}
	
	private static List<Task> nearSearchCategory(String key){
		List<Task> answer = new ArrayList<Task>();
		int length1, length2, diff;
		length2 = key.length();
		for (int i = 0; i < taskList.size(); ++i) {
			length1 = taskList.get(i).getCategory().length();
			diff = Math.abs(length2 - length1);
			if (diff <= 2) {
				answer.add(taskList.get(i));
			}
		}
		return answer;
	}
	
	private static List<Task> nearSearchNote(String key){
		List<Task> answer = new ArrayList<Task>();
		int length1, length2, diff;
		length2 = key.length();
		for (int i = 0; i < taskList.size(); ++i) {
			length1 = taskList.get(i).getNote().length();
			diff = Math.abs(length2 - length1);
			if (diff <= 2) {
				answer.add(taskList.get(i));
			}
		}
		return answer;
	}
	
	private static List<Task> nearSearchLocation(String key){
		List<Task> answer = new ArrayList<Task>();
		int length1, length2, diff;
		length2 = key.length();
		for (int i = 0; i < taskList.size(); ++i) {
			length1 = taskList.get(i).getLocation().length();
			diff = Math.abs(length2 - length1);
			if (diff <= 2) {
				answer.add(taskList.get(i));
			}
		}
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
