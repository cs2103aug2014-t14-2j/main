package powerSearch;

import globalClasses.Date;
import globalClasses.Task;
import java.util.ArrayList;

public class ExactMatchSearcher {
	private String nameSearch;
	private String dateSearch;
	private Date lookForDate;

	public ExactMatchSearcher(String comm) {
		if(comm.charAt(2) == '/'){
			dateSearch = comm;
			dateSearch = (dateSearch.toLowerCase()).trim();
			int day = Integer.valueOf(comm.substring(0,2));
			int month = Integer.valueOf(comm.substring(3,5));
			int year = Integer.valueOf(comm.substring(6,10));
			lookForDate = new Date(day, month, year);
		}
		else{
			nameSearch = comm;
			nameSearch = (nameSearch.toLowerCase()).trim();
		}
	}

	public ArrayList<Task> searchName(String nameSearch){
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		int i;
		for(i=0; i<tasks.size(); i++){
			if(tasks.get(i).getName().toLowerCase().trim().equals(namesearch)){
				tasksedited.add(tasks.get(i));
			}
		}
		return tasksedited;
	}

	public ArrayList<Task> searchEndDate(ArrayList<Task> tasks){
		ArrayList<Task> tasksedited = new ArrayList<Task>();
		int i;
		for(i=0; i<tasks.size(); i++){
			if(lookfordate.isBefore(tasks.get(i).getEndDate())){
				tasksedited.add(tasks.get(i)); //supposed to show all the tasks that have an endDate after the date searched for
			}
		}
		return tasksedited;
	}
	
	public static boolean isExactMatch(Task taskToSearch) {	// Require this method for TaskEditor() method

		

	}
}