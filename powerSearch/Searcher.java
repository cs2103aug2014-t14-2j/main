package powerSearch;

import java.util.ArrayList;
import java.util.List;

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
				//if(answer.get(answer.size()-1)==null)
					answer.add(NearMatchSearcher.nearSearch(keylist.get(i), list));
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

	private static List<Task> mergeListsOR(List<List<Task>> answer){
		if(answer.isEmpty())
			return null;
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
			return null;
		else
			return list;
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
		if(answer.isEmpty())
			return null;
		List<Task> list = new ArrayList<Task>();
		int i, j;
		boolean contains = false;
		List<Task> comp = new ArrayList<Task>();
		List<Task> returnlist = new ArrayList<Task>();
		list = answer.get(0);
		for(i=0; i<list.size(); i++){
			Task check = list.get(i);
			for(j=1; j<answer.size(); j++){
				contains = false;
				comp = answer.get(j);
				if(comp.contains(check)){
					contains = true;
				}
				else{
					contains = false;
					break;
				}
			}
			if(contains)
				returnlist.add(check);
		}
		if(returnlist.isEmpty())
			return null;
		else
			return returnlist;
	}
}
