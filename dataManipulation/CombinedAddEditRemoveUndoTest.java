package dataManipulation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import userInterface.CommandHandler;
import dataEncapsulation.Task;

public class CombinedAddEditRemoveUndoTest {

	// TEST CASE 1:
	// add "Buy milk"
	// edit "Buy milk" category "Groceries"
	// RESULT: Task: Buy milk, Category: Groceries
	
	@Test
	public void testCaseOne() throws Exception {
		
		// ADDING "Buy Milk"
		
		List<Subcommand> Subcommands = new ArrayList<Subcommand>();
		Subcommands.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Milk"));
		
		Command addTestCaseOne = new Add(Subcommands);
		CommandHandler.executeCommand(addTestCaseOne);
		
		// EDITING category "Groceries"
		
		Subcommands.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Groceries"));
		
		Command editTestCaseOne = new Edit(Subcommands);
		CommandHandler.executeCommand(editTestCaseOne);
		
		Task result = TotalTaskList.getInstance().getList().get(0);
		Task expected = new Task("Buy Milk", "Groceries");
		
		// CHECKING ADDING AND EDITING
		
		assertEquals("Task name matches expected", expected.getName(), result.getName());
		assertEquals("Task category matches as expected", expected.getCategory(), result.getCategory());
	
	}
	
	// TEST CASE 2:
	// add "Buy Chicken"
	// edit "Buy Chicken" category "Groceries"
	// edit "Buy Chicken" location "Wet Market"
	// edit "Buy Chicken" note "Diced chicken"
	
	// add "Buy Curry"
	// edit "Buy Curry" category "Spices"
	// edit "Buy Curry" location "Little India"
	// edit "Buy Curry" note "Say hi to Mr. Muthu"
	
	// remove "Buy Chicken"
	
	@Test
	public void testCaseTwo() {
		
		// ADDING "Buy Chicken"
		
		List<Subcommand> Subcommands = new ArrayList<Subcommand>();
		Subcommands.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Chicken"));
				
		Command addTestCaseTwo = new Add(Subcommands);
		CommandHandler.executeCommand(addTestCaseTwo);
				
		// EDITING category "Groceries"
				
		Subcommands.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Groceries"));
				
		Command editTestCaseTwo = new Edit(Subcommands);
		CommandHandler.executeCommand(editTestCaseTwo);
		
		// EDITING location "Wet Market"
		
		Subcommands.add(new Subcommand(Subcommand.TYPE.LOCATION, "Wet Market"));
		
		Command editTestCaseTwoOne = new Edit(Subcommands);
		CommandHandler.executeCommand(editTestCaseTwoOne);
		
		// EDITING note "Diced chicken"
		
		Subcommands.add(new Subcommand(Subcommand.TYPE.NOTE, "Diced chicken"));
		
		Command editTestCaseTwoTwo = new Edit(Subcommands);
		CommandHandler.executeCommand(editTestCaseTwoTwo);
		
		// ADDING "Buy Curry"
		
		List<Subcommand> subcommandsTwo = new ArrayList<Subcommand>();
		subcommandsTwo.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Curry"));

		Command addTestCaseTwoOne = new Add(subcommandsTwo);
		CommandHandler.executeCommand(addTestCaseTwoOne);

		// EDITING category "Spices"

		subcommandsTwo.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Spices"));

		Command editTestCaseTwoThree = new Edit(subcommandsTwo);
		CommandHandler.executeCommand(editTestCaseTwoThree);

		// EDITING location "Little India"

		subcommandsTwo.add(new Subcommand(Subcommand.TYPE.LOCATION, "Little India"));

		Command editTestCaseTwoFour = new Edit(subcommandsTwo);
		CommandHandler.executeCommand(editTestCaseTwoFour);

		// EDITING note "Say hi to Mr. Muthu"

		subcommandsTwo.add(new Subcommand(Subcommand.TYPE.NOTE, "Say hi to Mr. Muthu"));

		Command editTestCaseTwoFive = new Edit(subcommandsTwo);
		CommandHandler.executeCommand(editTestCaseTwoFive);
				
		Task resultOne = TotalTaskList.getInstance().getList().get(1);
		Task resultTwo = TotalTaskList.getInstance().getList().get(2);
		Task expectedOne = new Task("Buy Chicken", "Groceries", "Wet Market", "Diced chicken");
		Task expectedTwo = new Task("Buy Curry", "Spices", "Little India", "Say hi to Mr. Muthu");
				
		// CHECKING ADDING AND EDITING
		
		assertEquals("Task name matches expected", expectedOne.getName(), resultOne.getName());
		assertEquals("Task category matches as expected", expectedOne.getCategory(), resultOne.getCategory());
		assertEquals("Task location matches expected", expectedOne.getLocation(), resultOne.getLocation());
		assertEquals("Task note matches expected", expectedOne.getNote(), resultOne.getNote());
		
		assertEquals("Task name matches expected", expectedTwo.getName(), resultTwo.getName());
		assertEquals("Task category matches as expected", expectedTwo.getCategory(), resultTwo.getCategory());
		assertEquals("Task location matches expected", expectedTwo.getLocation(), resultTwo.getLocation());
		assertEquals("Task note matches expected", expectedTwo.getNote(), resultTwo.getNote());
		
		// REMOVING "Buy Chicken"
		
		List<Subcommand> removeChicken = new ArrayList<Subcommand>();
		removeChicken.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Chicken"));
		Command removeResultOne = new Remove(removeChicken);
		CommandHandler.executeCommand(removeResultOne);
		
		// CHECKING REMOVE
		
		assertEquals("TotalTaskList size is 2", 2, TotalTaskList.getInstance().getList().size());
		Task remainingTask = TotalTaskList.getInstance().getList().get(1);
		
		assertEquals("Task name matches expected", expectedTwo.getName(), remainingTask.getName());
		assertEquals("Task category matches as expected", expectedTwo.getCategory(), remainingTask.getCategory());
		assertEquals("Task location matches expected", expectedTwo.getLocation(), remainingTask.getLocation());
		assertEquals("Task note matches expected", expectedTwo.getNote(), remainingTask.getNote());
		
	}
	
	@Test
	public void testCaseThree() {
		
		// CLEARING TOTAL TASK LIST
		TotalTaskList.getInstance().getList().clear();
		
		// ADDING "Buy Curry"

		List<Subcommand> subcommand = new ArrayList<Subcommand>();
		subcommand.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Curry"));

		Command addTestCaseTwoOne = new Add(subcommand);
		CommandHandler.executeCommand(addTestCaseTwoOne);

		// EDITING category "Spices"

		subcommand.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Spices"));

		Command editTestCaseTwoThree = new Edit(subcommand);
		CommandHandler.executeCommand(editTestCaseTwoThree);

		// EDITING location "Little India"

		subcommand.add(new Subcommand(Subcommand.TYPE.LOCATION, "Little India"));

		Command editTestCaseTwoFour = new Edit(subcommand);
		CommandHandler.executeCommand(editTestCaseTwoFour);

		// EDITING note "Say hi to Mr. Muthu"
		/**
		 * Instead of making the subcommand and command like you are below
		 * 
		 * subcommand.add(new Subcommand(Subcommand.TYPE.NOTE, "Say hi to Mr. Muthu"));
		 * Command editTestCaseTwoFive = new Edit(subcommand);
		 * 
		 * You need to say
		 * 
		 * String editCommand = "edit /"Buy Curry/" note "Say hi to Mr. Muthu";
		 * Command editTestCaseTwoFive = CommandInterpreter.getInstance().formCommand(editCommand);
		 * 
		 * etc. Don't manually make the commands. Put them through Command Interpreter
		 * so we can get a full view of how well the program works.
		 * 
		 */

		subcommand.add(new Subcommand(Subcommand.TYPE.NOTE, "Say hi to Mr. Muthu"));
		
		Command editTestCaseTwoFive = new Edit(subcommand);
		CommandHandler.executeCommand(editTestCaseTwoFive);

		Task result = TotalTaskList.getInstance().getList().get(0);
		Task expected = new Task("Buy Curry", "Spices", "Little India", "Say hi to Mr. Muthu");
		
		// CHECKING ADDING AND EDITING
		
		assertEquals("Task name matches expected", expected.getName(), result.getName());
		assertEquals("Task category matches as expected", expected.getCategory(), result.getCategory());
		assertEquals("Task location matches expected", expected.getLocation(), result.getLocation());
		assertEquals("Task note matches expected", expected.getNote(), result.getNote());
		
		// UNDO-ING LAST COMMAND: EDITING note "Say hi to Mr. Muthu"
		
		List<Subcommand> blank = new ArrayList<Subcommand>();
		Command undoOnce = new Undo(blank);
		CommandHandler.executeCommand(undoOnce);
		
		// CHECKING FIRST UNDO CALL
		
		Task resultUndoOne = TotalTaskList.getInstance().getList().get(0);
		Task expectedUndoOne = new Task("Buy Curry", "Spices", "Little India");
		
		assertEquals("Task name matches expected", expectedUndoOne.getName(), resultUndoOne.getName());
		assertEquals("Task category matches as expected", expectedUndoOne.getCategory(), resultUndoOne.getCategory());
		assertEquals("Task location matches expected", expectedUndoOne.getLocation(), resultUndoOne.getLocation());
		assertEquals("Task note matches expected", expectedUndoOne.getNote(), resultUndoOne.getNote());
		
		// UNDO-ING SECOND LAST COMMAND: EDITING location "Little India"
		
		Command undoTwice = new Undo(blank);
		CommandHandler.executeCommand(undoTwice);
		
		// CHECKING SECOND UNDO CALL
		
		Task resultUndoTwo = TotalTaskList.getInstance().getList().get(0);
		Task expectedUndoTwo = new Task("Buy Curry", "Spices");

		assertEquals("Task name matches expected", expectedUndoTwo.getName(), resultUndoTwo.getName());
		assertEquals("Task category matches as expected", expectedUndoTwo.getCategory(), resultUndoTwo.getCategory());
		assertEquals("Task location matches expected", expectedUndoTwo.getLocation(), resultUndoTwo.getLocation());
		assertEquals("Task note matches expected", expectedUndoTwo.getNote(), resultUndoTwo.getNote());
		
		// UNDO-ING THIRD LAST COMMAND: EDITING category "Spices"
		
		Command undoThrice = new Undo(blank);
		CommandHandler.executeCommand(undoThrice);
		
		// CHECKING THIRD UNDO CALL
		
		Task resultUndoThree = TotalTaskList.getInstance().getList().get(0);
		Task expectedUndoThree = new Task("Buy Curry", "no category");

		assertEquals("Task name matches expected", expectedUndoThree.getName(), resultUndoThree.getName());
		assertEquals("Task category matches as expected", expectedUndoThree.getCategory(), resultUndoThree.getCategory());
		assertEquals("Task location matches expected", expectedUndoThree.getLocation(), resultUndoThree.getLocation());
		assertEquals("Task note matches expected", expectedUndoThree.getNote(), resultUndoThree.getNote());
		
		// UNDO-ING FOURTH LAST COMMAND: ADDING "Buy Curry"
		
		Command undoFinal = new Undo(blank);
		CommandHandler.executeCommand(undoFinal);
		
		// CHECKING THIRD UNDO CALL

		assertEquals("TotalTaskList size is 0", 0, TotalTaskList.getInstance().getList().size());		
	}
	
}
