//@author A0108297X

/**
	 * Takes a list and searches for various details
	 * @return format: String or List of Tasks
	 */
package powerSearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;

import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.Subcommand;

public class Searcher {
	/**
	 * searches for exact/near matches to search key
	 * @param keylist and a list of tasks
	 * @return a list of all matches for that key
	 * @throws Exception 
	 */
	public static List<Task> search(List<Subcommand> keylist, List<Task> list) throws Exception {
		int i;
		List<List<Task>> answer = new ArrayList<List<Task>>();
		assert(keylist!=null);
		assert(list!=null);
		String command = new String();
		for(i=0; i<keylist.size(); i++){
			switch(keylist.get(i).getType()){
			case AND:
				command = "AND";
				break;
			case OR:
				command = "OR";
				break;
			default:
				answer.add(ExactMatchSearcher.exactSearch(keylist.get(i), list));
			}
		}
		if(answer.get(0).isEmpty()){
			for(i=0; i<keylist.size(); i++){
				switch(keylist.get(i).getType()){
				case AND:
					command = "AND";
					break;
				case OR:
					command = "OR";
					break;
				default:
					answer.add(NearMatchSearcher.nearSearch(keylist.get(i), list));
				}
			}
		}
		List<Task> results = new ArrayList<Task>();
		switch(command){
		case "OR":
			results = mergeListsOR(answer);
			break;
		case "AND":
			results = mergeListsAND(answer);
			break;
		default:
			results = mergeListsOR(answer);
		}
		return Searcher.removeDuplicates(results);
	}
	//Parameters: List of Lists which have Tasks
	//It merges the lists returned according to the OR Command
	private static List<Task> mergeListsOR(List<List<Task>> answer) throws Exception{
		if(answer.isEmpty()) {
			return new ArrayList<Task>();
		} else {
		List<Task> list = new ArrayList<Task>();
		list = answer.get(0);
		for(int i = 1; i < answer.size(); i++){
			list.addAll(answer.get(i));
		}
		if(list.size()>=2){
			list = removeDuplicates(list);
			return list;
		}
		else if(list.isEmpty())
			return new ArrayList<Task>();
		else
			return list;
		}
	}
	//Parameters: List which has Tasks
	//It removes any duplicates in the final answer
	private static List<Task> removeDuplicates(List<Task> list){
		int k=0;
		Task t1;
		while(k<=list.size()-1){
			if(list.get(k)!=null)
				t1 = list.get(k);
			else
				t1 = list.get(++k);
			for(int i = 0; i < list.size(); i++){
				if(list.get(i)!=null){
					if(t1.isEqualTask(list.get(i)) && i!=k){
						list.remove(i);
					}
				}
				else
					list.remove(i);
			}
			k++;
		}
		return list;
	}
	//Parameters: List of Lists which have Tasks
	//It merges the lists returned according to the AND Command
	@SuppressWarnings("unchecked")
	private static List<Task> mergeListsAND(List<List<Task>> answer){
		if(answer.isEmpty()) {
			return new ArrayList<Task>();
		} else {
		List<Task> list = new ArrayList<Task>();
		list = answer.get(0);
		for(int i = 1; i < answer.size(); i++){
			list = ListUtils.intersection(list, answer.get(i));
		}
		if(list.size()>=2){
			list = removeDuplicates(list);
			return list;
		}
		else if(list.isEmpty())
			return new ArrayList<Task>();
		else
			return list;
		}
	}
	//Parameters: List of Tasks and the Date which is to be searched
	//Searches for the next available free slot on a specified date.
	public static String searchTimeSlot(List<Task> list, Date dt){
		String answer = new String();
		List<Task> search = new ArrayList<Task>();
		int[] seconds = new int[86401];
		for(int k=1; k<=86400; k++){
			seconds[k] = 0;
		}
		search = ExactMatchSearcher.simpleSearchDate(dt, list);
		if(search.isEmpty()){
			answer = "All slots on " + dt.toString() + " are free to be scheduled.\n";
			return answer;
		}
		for(int i=0; i<search.size(); i++){
			if(search.get(i).getEndDate().isEquals(dt) && search.get(i).getStartDate().isEquals(dt)){ //the task starts and ends on same day
				Task sdtemp = search.get(i);
				int sdhours, sdmins, sdseconds, edhours, edmins, edseconds;
				if(sdtemp.hasStartTime()){
					sdhours = sdtemp.getStartTime().getHours();
					sdmins = sdtemp.getStartTime().getMins();
					sdseconds = 0;
				}
				else{
					sdhours = 0;
					sdmins = 0;
					sdseconds = 0;
				}
				int totalsdseconds = sdseconds + sdhours*60*60 + sdmins*60;
				if(sdtemp.hasEndTime()){
					edhours = sdtemp.getEndTime().getHours();
					edmins = sdtemp.getEndTime().getMins();
					edseconds = 0;
				}
				else{
					edhours = 23;
					edmins = 59;
					edseconds = 59;
				}
				int totaledseconds = edseconds + edhours*60*60 + edmins*60;
				for(int j=totalsdseconds; j<=totaledseconds; j++){
					seconds[j]++;
				}
			}
			else if(search.get(i).getStartDate().isEquals(dt)){ //the task starts on the mentioned date
				Task sdtemp = search.get(i);
				int sdhours, sdmins, sdseconds;
				if(sdtemp.hasStartTime()){
					sdhours = sdtemp.getStartTime().getHours();
					sdmins = sdtemp.getStartTime().getMins();
					sdseconds = 0;
				}
				else{
					sdhours = 0;
					sdmins = 0;
					sdseconds = 0;
				}
				int totalsdseconds = sdseconds + sdhours*60*60 + sdmins*60;

				for(int j=totalsdseconds; j<seconds.length; j++){
					seconds[j]++;
				}
			}
			else if(search.get(i).getEndDate().isEquals(dt)){ //the task ends on the mentioned date
				Task edtemp = search.get(i);
				int edhours, edmins, edseconds;
				if(edtemp.hasEndTime()){
					edhours = edtemp.getEndTime().getHours();
					edmins = edtemp.getEndTime().getMins();
					edseconds = 0;
				}
				else{
					edhours = 23;
					edmins = 59;
					edseconds = 59;
				}
				int totaledseconds = edseconds + edhours*60*60 + edmins*60;
				for(int j=1; j<=totaledseconds; j++){
					seconds[j]++;
				}
			}
			else if(dt.isBefore(search.get(i).getEndDate()) && search.get(i).getStartDate().isBefore(dt)){
				answer = "None of the slots on " + dt.toString() + " are free to be scheduled.\n";
				return answer;
			}
		}
		int start, end;
		start = 86402;
		end = 86402;
		for(int i=1; i<seconds.length; i++){
			if(i>0 && i<86400){
				if(seconds[i]==0 && seconds[i-1]>0){
					start = i;
				}
				else if(seconds[i]==0 && seconds[i+1]>0){
					end = i+1;
					break;
				}
			}
		}
		if(end==86402 && start==86402){
			answer = "None of the slots on " + dt.toString() + " are free to be scheduled.\n";
			return answer;
		}
		if(Math.abs(end - start)<60){
			answer = "None of the slots on " + dt.toString() + " are free to be scheduled.\n";
			return answer;
		}
		if(start == 86402){
			start = 0;
		}
		if(end == 86402){
			end = 0;
		}
		int starthour, startmin, endhour, endmin;
		starthour = start/3600;
		startmin = start/60 - starthour*60;
		
		endhour = end/3600;
		endmin = end/60 - endhour*60;
		
		String startTime = getTimeString(starthour, startmin);
		String endTime = getTimeString(endhour, endmin);
		
		answer = "Free Slot Found on " + dt.toString() + '\n';
		if(endTime.equals("00:00")){
			answer = answer + startTime + " to " + endTime + "(Next Day)" + '\n';
		}
		else
			answer = answer + startTime + " to " + endTime + '\n';
		return answer;
	}

	/**
	 * Takes time in hours and minutes and returns the string of that time
	 * @return format: "hour:min" (ex. "10:04")
	 */
	//@author A0126720N
	private static String getTimeString(int hour, int min) {
		String hourString = String.valueOf(hour);
		String minuteString = String.valueOf(min);
		
		if (min < 10) {
			minuteString = "0" + minuteString;
		}
		
		return hourString + ":" + minuteString;
	}
}
