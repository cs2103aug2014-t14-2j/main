//@author Kaushik A0108297X
package powerSearch;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.Subcommand;

public class SearcherTest {

	@Test
	public void testSearch() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		Date temp1 = new Date();
		String location = new String("CSC");
		tasks.add(new Task("Andrew", "A", location, temp1, new Date(25,9,2018)));
		tasks.add(new Task("Bravo", "B", temp1, new Date(26,9,2015)));
		tasks.add(new Task("Charlie", "C", temp1, new Date(25,12,2014)));
		
		List<Subcommand> listCC = new ArrayList<Subcommand>();
		listCC.add(new Subcommand(Subcommand.TYPE.LOCATION, "CSC"));
		System.out.println(Searcher.search(listCC, tasks));
	}

	@Test
	public void testSearchTimeSlots() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		Date temp1 = new Date();
		Date end1 = new Date(3,11,2014,12,30,0);
		Date start2 = new Date(3, 11, 2014, 18, 45, 0);
		Date end2 = new Date(3, 11, 2014, 23, 45, 0);
		String location = new String("CSC");
		tasks.add(new Task("Andrew", "A", location, temp1, end1));
		tasks.add(new Task("Bravo", "B", location, start2, end2));
		//tasks.add(new Task("Bravo", "B", temp1, new Date(26,9,2015)));
		//tasks.add(new Task("Charlie", "C", temp1, new Date(25,12,2014)));
		
		System.out.println(Searcher.searchTimeSlot(tasks, temp1));
	}
}
