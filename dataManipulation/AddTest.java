package dataManipulation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import userInterface.CommandHandler;
import dataEncapsulation.Date;
import dataEncapsulation.Task;

public class AddTest {

	String taskName = null;
	String taskCategory = null;
	String taskLocation = null;
	String taskNote = null;
	Date taskStart = null;
	Date taskEnd = null;
	
	@Test
	public void testBuildTask() {
		List<Subcommand> listCC = new ArrayList<Subcommand>();
		listCC.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Milk"));
		listCC.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Groceries"));
		listCC.add(new Subcommand(Subcommand.TYPE.START, "01/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.END, "02/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.LOCATION, "Clementi"));
		listCC.add(new Subcommand(Subcommand.TYPE.NOTE, "Meiji Chocolate Milk"));
		
		Task actual = new Add(listCC).buildTask(listCC);
		Date startDate = Date.determineDate("01/01/2014");
		Date endDate = Date.determineDate("02/01/2014");
		Task expected = new Task("Buy Milk", "Groceries", "Clementi", "Meiji Chocolate Milk", startDate, endDate);
		
		assertEquals("Task name matches expected", expected.getName(), actual.getName());
		assertEquals("Task location matches expected", expected.getLocation(), actual.getLocation());
		assertEquals("Task note matches as expected", expected.getNote(), actual.getNote());
		assertEquals("Task category matches as expected", expected.getCategory(), actual.getCategory());
		assertEquals("Task start date matches as expected", expected.getStartDate().toString(), actual.getStartDate().toString());
		assertEquals("Task end date matches as expected", expected.getEndDate().toString(), actual.getEndDate().toString());
	}
	
	@Test
	public void testAdd() throws Exception {
		List<Subcommand> listCC = new ArrayList<Subcommand>();
		listCC.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Milk"));
		listCC.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Groceries"));
		listCC.add(new Subcommand(Subcommand.TYPE.START, "01/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.END, "02/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.LOCATION, "Clementi"));
		listCC.add(new Subcommand(Subcommand.TYPE.NOTE, "Meiji Chocolate Milk"));
		Date startDate = Date.determineDate("01/01/2014");
		Date endDate = Date.determineDate("02/01/2014");
		Task expected = new Task("Buy Milk", "Groceries", "Clementi", "Meiji Chocolate Milk", startDate, endDate);
		
		Task actual = new Add(listCC).buildTask(listCC);
		Command addTask = new Add(listCC);
		CommandHandler.executeCommand(addTask);
		TotalTaskList taskList = TotalTaskList.getInstance();
		actual = taskList.getList().get(0);
		
		assertEquals("Task name matches expected", expected.getName(), actual.getName());
		assertEquals("Task location matches expected", expected.getLocation(), actual.getLocation());
		assertEquals("Task note matches as expected", expected.getNote(), actual.getNote());
		assertEquals("Task category matches as expected", expected.getCategory(), actual.getCategory());
		assertEquals("Task start date matches as expected", expected.getStartDate().toString(), actual.getStartDate().toString());
		assertEquals("Task end date matches as expected", expected.getEndDate().toString(), actual.getEndDate().toString());
	}
}
