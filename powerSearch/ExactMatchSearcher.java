	/**
	 * @author Kadayam Suresh Kaushik A0108297X
	 * Performs an Exact Search on a List<Task> based on Different Keys given
	 * @return format: List<Task>
	 */
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
			return simpleSearchName(key.getContents());
		case TITLE:
			return simpleSearchName(key.getContents());
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
		case STARTTIME:
			return simpleSearchStartTime(key.getContents());
		case ENDTIME:
			return simpleSearchEndTime(key.getContents());
		default :
			throw new 
			IllegalArgumentException("invalid subcommand for search");
		}
	}
	private static ArrayList<Task> simpleSearchStartTime(String key) throws Exception{
		ArrayList<Task> answer = new ArrayList<Task>();
		Time comp = new Time();
		comp = comp.determineTime(key);
		for (int i = 0; i < taskList.size(); ++i) {
			if (comp.compareTo(taskList.get(i).getStartTime())==0) {
				answer.add(taskList.get(i));
			}
		}
		return answer;
	}
	
	private static ArrayList<Task> simpleSearchEndTime(String key) throws Exception{
		ArrayList<Task> answer = new ArrayList<Task>();
		Time comp = new Time();
		comp = comp.determineTime(key);
		for (int i = 0; i < taskList.size(); ++i) {
			if (comp.compareTo(taskList.get(i).getEndTime())==0) {
				answer.add(taskList.get(i));
			}
		}
		return answer;
	}


	private static ArrayList<Task> simpleSearchName(String key){
		ArrayList<Task> answer = new ArrayList<Task>();
		CharSequence temp = key;
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().equals(taskList.get(i).getName().toLowerCase())) {
				answer.add(taskList.get(i));
			}
			else if(taskList.get(i).getName().toLowerCase().contains(temp)){
				answer.add(taskList.get(i));
			}
		}
		return answer;
	}

	private static ArrayList<Task> simpleSearchCategory(String key) {
		ArrayList<Task> answer = new ArrayList<Task>();
		CharSequence temp = key;
		for (int i = 0; i < taskList.size(); ++i) {
			if (key.toLowerCase().equals(taskList.get(i).getCategory().toLowerCase())) {
				answer.add(taskList.get(i));
			}
			else if(taskList.get(i).getCategory().toLowerCase().contains(temp)){
				answer.add(taskList.get(i));
			}
		}
		return answer;
	}

	private static ArrayList<Task> simpleSearchLocation(String key){
		ArrayList<Task> answer = new ArrayList<Task>();
		CharSequence temp = key;
		for (int i = 0; i < taskList.size(); ++i) {
			if(taskList.get(i).getHasLocation()){
				if (key.toLowerCase().equals(taskList.get(i).getLocation().toLowerCase())) {
					answer.add(taskList.get(i));
				}
				else if(taskList.get(i).getLocation().toLowerCase().contains(temp)){
					answer.add(taskList.get(i));
				}
			}
		}
		return answer;
	}

	private static ArrayList<Task> simpleSearchNote(String key) {
		ArrayList<Task> answer = new ArrayList<Task>();
		CharSequence temp = key;
		key = key.toLowerCase();
		for (int i = 0; i < taskList.size(); ++i) {
			if(taskList.get(i).getHasNote()){
				if (taskList.get(i).getNote().toLowerCase().contains(key.subSequence(0, key.length()))) {
					answer.add(taskList.get(i));
				}
				else if(taskList.get(i).getNote().toLowerCase().contains(temp)){
					answer.add(taskList.get(i));
				}
			}
		}
		return answer;
	}

	private static ArrayList<Task> simpleSearchDate(String comm) {
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		Date lookfordate = new Date(); //create the Date class which he is looking for
		lookfordate = lookfordate.determineDate(comm);
		int i;
		for(i=0; i<taskList.size(); i++){
			if(taskList.get(i).getHasDeadline()){
				if((lookfordate.isBefore(taskList.get(i).getEndDate()) && taskList.get(i).getStartDate().isBefore(lookfordate)) || lookfordate.isEquals(taskList.get(i).getEndDate()) || lookfordate.isEquals(taskList.get(i).getStartDate())){
					tasksedited.add(taskList.get(i)); //supposed to show all the tasks that have an endDate after the date searched for
				}
			}
			else if(lookfordate.isEquals(taskList.get(i).getStartDate())){
				tasksedited.add(taskList.get(i));
			}
		}
		return tasksedited;
	}

	private static ArrayList<Task> simpleSearchEndDate(String comm) {
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		Date lookfordate = new Date(); //create the Date class which he is looking for
		lookfordate = lookfordate.determineDate(comm);
		int i;
		for(i=0; i<taskList.size(); i++){
			if(lookfordate.isEqual(taskList.get(i).getEndDate())){
				tasksedited.add(taskList.get(i)); //supposed to show all the tasks that have an endDate after the date searched for
			}
		}
		return tasksedited;
	}
	
	public static ArrayList<Task> simpleSearchDate(Date lookfordate, List<Task> list) {
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		int i;
		for(i=0; i<list.size(); i++){
			if((lookfordate.isBefore(list.get(i).getEndDate()) && list.get(i).getStartDate().isBefore(lookfordate)) || lookfordate.isEquals(list.get(i).getEndDate()) || lookfordate.isEquals(list.get(i).getStartDate())){
				tasksedited.add(list.get(i)); //supposed to show all the tasks that have an endDate after the date searched for
			}
		}
		return tasksedited;
	}
	
	private static ArrayList<Task> simpleSearchStartDate(String comm) {
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		Date lookfordate = new Date(); //create the Date class which he is looking for
		lookfordate = lookfordate.determineDate(comm);
		int i;
		for(i=0; i<taskList.size(); i++){
			if(lookfordate.isEqual(taskList.get(i).getStartDate())){
				tasksedited.add(taskList.get(i)); //supposed to show all the tasks that have an endDate after the date searched for
			}
		}
		return tasksedited;
	}

	public static boolean isTaskDuplicate(Task taskToCheck) {
		
		List<List<Task>> categorizedList = new ArrayList<List<Task>>();
		categorizedList.add(TotalTaskList.getInstance().getList());
		categorizedList.add(TotalTaskList.getInstance().getCompleted());
		categorizedList.add(TotalTaskList.getInstance().getOverdue());
		
		for(List<Task> lt : categorizedList) {
		
			for(Task t : lt) {
				
				if(t.getName().toLowerCase().equals(taskToCheck.getName().toLowerCase())) {
					
					if(taskToCheck.getCategory() == null) {	// If task's category is null
						if(t.getCategory() != null) {		// Check if one of the task's category in the list is NOT null
							break;							// Break if they are different
						}
					}
						
					else if(t.getCategory() == null) {		// ELSE, this means that the task's category is not null and we check for one of the task's category in the list being null, i.e. different
						break;
					}
						
					else {									// ELSE, this means both are not null
						if(t.getCategory().toLowerCase().equals(taskToCheck.getCategory().toLowerCase())) { 	// Check for whether both categories are the same
						
							if(taskToCheck.getHasLocation() == false) {
								if(t.getHasLocation() == true) {
									break;
								}
							}
						
							else if(t.getHasLocation() == false) {
								break;
							}
							
							else {
								if(t.getLocation().toLowerCase().equals(taskToCheck.getLocation().toLowerCase())) {
								
									if(taskToCheck.getHasNote() == false) {
										if(t.getHasNote() == true) {
											break;
										}
									}
							
									else if(t.getHasNote() == false) {
										break;
									}
									
									else {
										if(t.getNote().toLowerCase().equals(taskToCheck.getNote().toLowerCase())) {
								
											if(t.getStartDate().toString().toLowerCase().equals(taskToCheck.getStartDate().toString().toLowerCase())) {
									
												if(taskToCheck.getEndDate().getDay() == 0) {
													if(t.getEndDate().getDay() != 0) {
														break;
													}
												}
												
												else if(t.getEndDate().getDay() == 0) {
													break;
												}
												
												else {
													if(t.getEndDate().isEqual(taskToCheck.getEndDate())) {
														return true;
													}
												}
											}
										}
									}
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
