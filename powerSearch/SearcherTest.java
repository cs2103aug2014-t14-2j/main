	/**
	 * @author Kadayam Suresh Kaushik A0108297X
	 * Tests the different searches within Searcher Class
	 * @return format: String
	 */
package powerSearch;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataEncapsulation.Time;
import dataManipulation.Subcommand;

public class SearcherTest {

	@Test
	public void testSearch() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		Date temp1 = new Date();
		Date temp2 = new Date(9,12,2014);
		String location = new String("CSC");
		tasks.add(new Task("Andrew", "A", location, temp1, new Date(25,9,2018)));
		tasks.add(new Task("Bravo", "B", temp2, new Date(26,9,2015)));
		tasks.add(new Task("Charlie", "C", temp2, new Date(11,12,2014)));
		tasks.add(new Task("Lunch with bobby green", "C", "raj restaurant", temp2, new Date(25,12,2014)));
		//System.out.println(tasks);
		List<Subcommand> listCC = new ArrayList<Subcommand>();
		listCC.add(new Subcommand(Subcommand.TYPE.TITLE, "bra"));
		listCC.add(new Subcommand(Subcommand.TYPE.OR, "and"));
		listCC.add(new Subcommand(Subcommand.TYPE.LOCATION, "rest"));
		System.out.println(Searcher.search(listCC, tasks));
	}

	@Test
	public void testSearchTimeSlots() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		Date start1 = new Date(7, 11, 2014);
		Date end1 = new Date(16,11,2014);
		Date start2 = new Date(2, 11, 2014);
		Date end2 = new Date(7, 11, 2014);
		Time time1 = new Time(7, 0);
		Time time2 = new Time(11, 45);
		Time time3 = new Time(22, 50);
		Time time4 = new Time(23, 59);
		String location = new String("CSC");
		tasks.add(new Task("Bobby Green", "A", location, start1, end1, time3, time4));
		tasks.add(new Task("Bravo", "B", location, start2, end2, time1));
		
		System.out.println(Searcher.searchTimeSlot(tasks, new Date(7,11,2014)));
	}
}
