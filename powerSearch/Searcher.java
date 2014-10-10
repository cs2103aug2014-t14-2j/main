package powerSearch;

import java.util.ArrayList;
import java.util.List;

import globalClasses.CommandComponent;
import globalClasses.Task;

public class Searcher {
	/**
	 * searches for exact/near matches to search key
	 * @param key
	 * @return a list of all matches for that key
	 * @throws Exception 
	 */
	public ArrayList<Task> search(ArrayList<CommandComponent> keylist, List<Task> list) throws Exception {
		int i;
		ArrayList<ArrayList<Task>> answer = new ArrayList<ArrayList<Task>>();
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
		if(command.equals("OR"))
			return mergeListsOR(answer);
		else if(command.equals("AND"))
			return mergeListsAND(answer);
		else
			return null;
	}
	
	private ArrayList<Task> mergeListsOR(ArrayList<ArrayList<Task>> answer){
		if(answer.isEmpty())
			return null;
		ArrayList<Task> list = new ArrayList<Task>();
		int i, j, k;
		ArrayList<Task> comp = new ArrayList<Task>();
		list = answer.get(0);
		for(i=1; i<answer.size(); i++){
			comp = answer.get(i);
			for(j=0; j< list.size(); j++){
				k = 0;
				while(k<=comp.size()){
					if(!list.get(j).equals(comp.get(k))){
						list.add(comp.get(k));
					}
					k++;
				}	
			}
		}
		if(list.isEmpty())
			return null;
		else
			return list;
	}
	
	private ArrayList<Task> mergeListsAND(ArrayList<ArrayList<Task>> answer){
		if(answer.isEmpty())
			return null;
		ArrayList<Task> list = new ArrayList<Task>();
		int i, j;
		boolean contains = false;
		ArrayList<Task> comp = new ArrayList<Task>();
		ArrayList<Task> returnlist = new ArrayList<Task>();
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
