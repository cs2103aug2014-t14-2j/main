//@ Kaushik A0108297X
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
	 * @param key
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
			//System.out.println(keylist.get(i));
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
				//System.out.println(keylist.get(i));
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
		return results;
	}

	private static List<Task> mergeListsOR(List<List<Task>> answer) throws Exception{
		if(answer.isEmpty()) {
			return null;
		} else {
		List<Task> list = new ArrayList<Task>();
		list = answer.get(0);
		for(int i = 1; i < answer.size(); i++){
			list = ListUtils.union(list, answer.get(i));
		}
		if(list.size()>=2){
			list = removeDuplicates(list);
			return list;
		}
		else if(list.isEmpty())
			return null;
		else
			return list;
		}
	}
	
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
					if(t1.equals(list.get(i)) && i!=k){
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

	private static List<Task> mergeListsAND(List<List<Task>> answer){
		if(answer.isEmpty()) {
			return null;
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
			return null;
		else
			return list;
		}
	}
	
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
				Date edtemp = search.get(i).getEndDate();
				Date sdtemp = search.get(i).getStartDate();

				int sdhours = sdtemp.getHours();
				int sdmins = sdtemp.getMinutes();
				int sdseconds = sdtemp.getSeconds();
				int totalsdseconds = sdseconds + sdhours*60*60 + sdmins*60;

				int edhours = edtemp.getHours();
				int edmins = edtemp.getMinutes();
				int edseconds = edtemp.getSeconds();
				int totaledseconds = edseconds + edhours*60*60 + edmins*60;

				for(int j=totalsdseconds; j<=totaledseconds; j++){
					seconds[j]++;
				}
			}
			else if(search.get(i).getStartDate().isEquals(dt)){ //the task starts on the mentioned date
				Date sdtemp = search.get(i).getStartDate();
				int sdhours = sdtemp.getHours();
				int sdmins = sdtemp.getMinutes();
				int sdseconds = sdtemp.getSeconds();
				int totalsdseconds = sdseconds + sdhours*60*60 + sdmins*60;

				for(int j=totalsdseconds; j<=seconds.length; j++){
					seconds[j]++;
				}
			}
			else if(search.get(i).getEndDate().isEquals(dt)){ //the task ends on the mentioned date
				Date edtemp = search.get(i).getEndDate();

				int edhours = edtemp.getHours();
				int edmins = edtemp.getMinutes();
				int edseconds = edtemp.getSeconds();
				int totaledseconds = edseconds + edhours*60*60 + edmins*60;

				for(int j=1; j<=totaledseconds; j++){
					seconds[j]++;
				}
			}
			else if(dt.isBefore(search.get(i).getEndDate())){
				answer = "None of the slots on " + dt.toString() + " are free to be scheduled.\n";
				return answer;
			}
		}
		int start, end;
		start = 0;
		end = 0;
		for(int i=0; i<seconds.length; i++){
			if(seconds[i]==0 && i==0){
				start = i;
			}
			if(i>=1 && i<86400){
				if(seconds[i]==0 && seconds[i-1]>0){
					start = i;
				}
				if(seconds[i]==0 && seconds[i+1]>0){
					end = i;
					break;
				}
			}
		}
		if(end==0 && start==0){
			answer = "None of the slots on " + dt.toString() + " are free to be scheduled.\n";
			return answer;
		}
		int starthour, startmin, startsec, endhour, endmin, endsec;
		
		starthour = start/3600;
		startmin = start/60 - starthour*60;
		startsec = start - startmin*60 - starthour*3600;
		
		endhour = end/3600;
		endmin = end/60 - endhour*60;
		endsec = end - endmin*60 - endhour*3600;
		
		answer = "Free Slot Found on " + dt.toString() + '\n';
		answer = answer + starthour + ":" + startmin + ":" + startsec + " to " + endhour + ":" + endmin + ":" + endsec + '\n';

		return answer;
	}
}
