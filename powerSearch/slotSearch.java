package powerSearch;

import java.util.ArrayList;
import java.util.List;

import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataEncapsulation.Time;
import dataManipulation.Subcommand;
import dataManipulation.TotalTaskList;

public class slotSearch {
	TotalTaskList tl = TotalTaskList.getInstance();
	List<Time> freeSlots;
	
	public List<Slot> findFreeSlotsOn(Date d) throws Exception {
		List<Task> tasksOnD = tasksFor(d);
		List<Time> se = new ArrayList<Time>();
		List<Slot> freeslots = new ArrayList<Slot>();
		se.add(new Time(0,0));
		for (int i=0; i<tasksOnD.size(); i++) {
			Task t = tasksOnD.get(i);
			Time previous = se.get(i-1);
			Time start = t.getStartTime();
			Time end = t.getEndTime();
			if(start.compareTo(previous) <= 0) {
				se.remove(i-1);
				se.add(end);
			} else {
				se.add(start);
				se.add(end);
			}
		}
		for (int i=0; i<se.size(); i=i+2) {
			Time freeStart = se.get(i);
			Time freeEnd = se.get(i+1);
			Slot freeSlot = new Slot(freeStart, freeEnd);
			freeslots.add(freeSlot);
		}
		return freeslots;
	}
	
	public List<Task> tasksFor(Date d) throws BadSubcommandException, BadSubcommandArgException {
		List<Task> results = new ArrayList<Task>();
		Subcommand date = new Subcommand(Subcommand.TYPE.DATE, d.toString());
		List<Task> allTasks = TotalTaskList.getInstance().getList();
		for (Task t : allTasks) {
			Date end = t.getEndDate();
			Date start = t.getStartDate();
			boolean taskEndsToday = d.isEquals(end);
			boolean taskStartsToday = d.isEquals(start);
			if (taskEndsToday && taskStartsToday ) {
				results.add(t);
			}
		}
		return results;
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
} 
