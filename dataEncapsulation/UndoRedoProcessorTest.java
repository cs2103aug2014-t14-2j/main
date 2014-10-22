package dataEncapsulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import userInterface.CommandHandler;
import dataManipulation.Add;
import dataManipulation.Command;
import dataManipulation.Edit;
import dataManipulation.Subcommand;

public class UndoRedoProcessorTest {

	@Test
	public void testUndo() throws Exception {
		
		List<Subcommand> listCC = new ArrayList<Subcommand>();
		listCC.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Milk"));
		listCC.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Groceries"));
		listCC.add(new Subcommand(Subcommand.TYPE.START, "01/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.END, "02/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.LOCATION, "Clementi"));
		listCC.add(new Subcommand(Subcommand.TYPE.NOTE, "Meiji Chocolate Milk"));
		
		Date startDate = Date.determineDate("01/01/2014");
		Date endDate = Date.determineDate("02/01/2014");
		Task addTask = new Task("Buy Milk", "Groceries", "Clementi", "Meiji Chocolate Milk", startDate, endDate);
		Task originalTask = new Task("Buy Milk", "Groceries", "Jurong East", "Meiji Chocolate Milk", startDate, endDate);
		
		Command addOriginal = new Add(listCC);
		CommandHandler.executeCommand(addOriginal);
		
	/*	List<Subcommand> listCC2 = Add.dismantleTask(originalTask);
		Command editOriginal = new Edit(listCC2);
		CommandHandler.executeCommand(editOriginal);
		*/
		
		UndoRedoProcessor.undo();
		assertEquals("Undo Edit works - Name is the same", addTask.getName());
		assertEquals("Undo Edit works - Category is the same", addTask.getCategory());
		assertEquals("Undo Edit works - Location is the same", addTask.getLocation());
		assertEquals("Undo Edit works - Notes are the same", addTask.getNote());
		assertEquals("Undo Edit works - Start date is the same", addTask.getStartDate());
		assertEquals("Undo Edit works - End date is the same", addTask.getEndDate());
		
	}

	@Test
	public void testRedo() {
		fail("Not yet implemented");
	}

}
