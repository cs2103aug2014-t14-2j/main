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
		tasks.add(new Task("Andrew", "A", temp1, new Date(25,9,2018)));
		tasks.add(new Task("Bravo", "B", temp1, new Date(26,9,2015)));
		tasks.add(new Task("Charlie", "C", temp1, new Date(25,12,2014)));
		
		List<Subcommand> listCC = new ArrayList<Subcommand>();
		listCC.add(new Subcommand(Subcommand.TYPE.TITLE, "ndrew"));
		
		System.out.println(Searcher.search(listCC, tasks));
		
	}

}
