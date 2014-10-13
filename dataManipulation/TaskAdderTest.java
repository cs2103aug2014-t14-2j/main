package dataManipulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import globalClasses.CommandComponent;
import globalClasses.Date;
import globalClasses.Task;
import globalClasses.ezC;

import java.util.ArrayList;

import org.junit.Test;

public class TaskAdderTest {
	
	String taskName = null;
	String taskCategory = null;
	String taskLocation = null;
	String taskNote = null;
	Date taskStart = null;
	Date taskEnd = null;
	
	@Test
	public void testBuildTask() {
		ArrayList<CommandComponent> listCC = new ArrayList<CommandComponent>();
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.NAME, "Buy Milk"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.CATEGORY, "Groceries"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.START, "01/01/2014"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.END, "02/01/2014"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.LOCATION, "Clementi"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.NOTE, "Meiji Chocolate Milk"));
		
		Task actual = TaskAdder.buildTask(listCC);
		Date startDate = globalClasses.ezC.determineDate("01/01/2014");
		Date endDate = globalClasses.ezC.determineDate("02/01/2014");
		Task expected = new Task("Buy Milk", "Groceries", "Clementi", "Meiji Chocolate Milk", startDate, endDate);
		
		assertEquals("Task built as expected", expected.getName(), actual.getName());
		assertEquals("Task built as expected", expected.getLocation(), actual.getLocation());
		assertEquals("Task built as expected", expected.getNote(), actual.getNote());
	}
	
	@Test
	public void testIsDuplicate() {
		Date startDate = globalClasses.ezC.determineDate("01/01/2014");
		Date endDate = globalClasses.ezC.determineDate("02/01/2014");
		Task expected = new Task("Buy Milk", "Groceries", "Clementi", "Meiji Chocolate Milk", startDate, endDate);
		ezC.totalTaskList.add(expected);
		
		assertTrue("Task is duplicate within list, isDuplicate() works", TaskAdder.isDuplicate(expected));
	}
	
	@Test
	public void testAdd() throws Exception {
		ArrayList<CommandComponent> listCC = new ArrayList<CommandComponent>();
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.NAME, "Buy Milk"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.CATEGORY, "Groceries"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.START, "01/01/2014"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.END, "02/01/2014"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.LOCATION, "Clementi"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.NOTE, "Meiji Chocolate Milk"));
		Date startDate = globalClasses.ezC.determineDate("01/01/2014");
		Date endDate = globalClasses.ezC.determineDate("02/01/2014");
		Task expected = new Task("Buy Milk", "Groceries", "Clementi", "Meiji Chocolate Milk", startDate, endDate);
		
		Task actual = TaskAdder.add(listCC);
		assertEquals("Task built as expected", expected.getName(), actual.getName());
		assertEquals("Task built as expected", expected.getLocation(), actual.getLocation());
		assertEquals("Task built as expected", expected.getNote(), actual.getNote());
	}
}
