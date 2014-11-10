package powerSearch;
/**
 * 
 * @author A0115696W
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataEncapsulation.Time;
import dataEncapsulation.StartComparator;
import dataManipulation.TotalTaskList;

public class SlotSearch {
	
	TotalTaskList tl = TotalTaskList.getInstance();
	List<Time> freeSlots;
	static List<Task> longTermTasks;
	
	public static String execute(Date d) throws Exception {
		String ans ="";
		longTermTasks = new ArrayList<Task>();
		List<Slot> slots = findFreeSlotsOn(d);
		for (int i=slots.size()-1; i>=0; i--) {
			ans = ans + "\n" + slots.get(i).toString();
		}
		ans = ans + "\n\nRunning Tasks: \n\n" + runningTasks(longTermTasks, d);
		return ans;
	}
	
	public static List<Slot> findFreeSlotsOn(Date d) throws Exception { 
		List<Task> tasksOnD = tasksFor(d);
		Stack<Time> se = new Stack<Time>();
		List<Slot> freeslots = new ArrayList<Slot>();
		se.push(new Time(0,0));
		for (int i=0; i<tasksOnD.size(); i++) {
			Task t = tasksOnD.get(i);
			Time previous = se.peek();
			Time start = t.getStartTime();
			Time end = t.getEndTime();
			if(start.compareTo(previous) <= 0) {
				se.pop();
				se.push(end);
			} else {
				se.push(start);
				se.push(end);
			}
		}
		se.push(new Time(23,59));
		/*
		for (int i=0; i<se.size(); i=i+2) {
			Time freeStart = se.get(i);
			Time freeEnd = se.get(i+1);
			Slot freeSlot = new Slot(freeStart, freeEnd);
			freeslots.add(freeSlot);
		}*/
		while (!se.empty()) {
			Time freeEnd = se.pop();
			Time freeStart = se.pop();
			Slot freeSlot = new Slot(freeStart, freeEnd);
			freeslots.add(freeSlot);
		}
		return freeslots;
	}
	
	public static List<Task> tasksFor(Date d) throws BadSubcommandException, BadSubcommandArgException {
		List<Task> results = new ArrayList<Task>();
		List<Task> allTasks = TotalTaskList.getInstance().getList();
		for (Task t : allTasks) {
			Date end = t.getEndDate();
			Date start = t.getStartDate();
			boolean taskEndsToday = d.isEquals(end);
			boolean taskStartsToday = d.isEquals(start);
			boolean taskEndsAfterToday = d.isBefore(end) || taskEndsToday;
			boolean taskStartsBeforeToday = start.isBefore(d) || taskStartsToday;
			if (taskEndsToday && taskStartsToday ) { //single day task
				results.add(t);
			} else if (taskEndsAfterToday && taskStartsBeforeToday) {
				longTermTasks.add(t);
			}
		}
		Collections.sort(results, new StartComparator());
		return results;
	}	
	
	private static String runningTasks(List<Task> ltt, Date today) {
		String res = "";
		Collections.sort(ltt, new StartComparator());
		for (int i=0; i<ltt.size(); i++) {
			Task t= ltt.get(i);
			Date startDate = t.getStartDate();
			Date endDate = t.getEndDate();
			Time endTime = t.getEndTime();
			Time startTime = t.getStartTime();
			if (today.isEquals(endDate)) {
				res = res + t.getName() + " ends on this day at " + endTime.toString() + "\n";
			} else if (today.isEquals(startDate)) {
				res = res + t.getName() + " starts on this day at " + startTime.toString() + "\n";
			} else {
				res = res + t.getName() + " started " + startDate.toPrint() +
						" and ends " + endDate.toPrint() ;
			}
		}
		return res;
	}
}

class Slot {
	private Time slotStart;
	private Time slotEnd;
	
	public Slot(Time start, Time end) {
		slotStart = start;
		slotEnd = end;
	}
	
	public Time getStart() {
		return slotStart;
	}
	public Time getEnd() {
		return slotEnd;
	}
	
	public String toString() {
		return "From " + getStart().toString() + " to " + getEnd().toString() ;
	}
} 
