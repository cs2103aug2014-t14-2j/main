package dataEncapsulation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dataManipulation.TaskAdder;
import dataManipulation.TaskEditor;

public class UndoRedoProcessorTest {

	@Test
	public void testUndo() throws Exception {
		
		ArrayList<CommandComponent> listCC = new ArrayList<CommandComponent>();
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.NAME, "Buy Milk"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.CATEGORY, "Groceries"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.START, "01/01/2014"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.END, "02/01/2014"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.LOCATION, "Clementi"));
		listCC.add(new CommandComponent(CommandComponent.COMPONENT_TYPE.NOTE, "Meiji Chocolate Milk"));
		
		Date startDate = dataEncapsulation.ezC.determineDate("01/01/2014");
		Date endDate = dataEncapsulation.ezC.determineDate("02/01/2014");
		Task addTask = new Task("Buy Milk", "Groceries", "Clementi", "Meiji Chocolate Milk", startDate, endDate);
		Task originalTask = new Task("Buy Milk", "Groceries", "Jurong East", "Meiji Chocolate Milk", startDate, endDate);
		TaskAdder.add(listCC);
		List<CommandComponent> listCC2 = TaskAdder.dismantleTask(originalTask);
		TaskEditor.edit(listCC2);
		
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
