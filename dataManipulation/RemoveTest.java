package dataManipulation;
//@author A0111014J

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import userInterface.CommandHandler;
import userInterface.ezCMessages;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.Add;
import dataManipulation.Command;
import dataManipulation.Subcommand;

public class RemoveTest {

	@Test
	public void testRemove() throws Exception {

		List<Subcommand> listCC = new ArrayList<Subcommand>();
		listCC.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Milk"));
		listCC.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Groceries"));
		listCC.add(new Subcommand(Subcommand.TYPE.START, "01/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.END, "02/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.LOCATION, "Clementi"));
		listCC.add(new Subcommand(Subcommand.TYPE.NOTE, "Meiji Chocolate Milk"));

		List<Subcommand> listCC2 = new ArrayList<Subcommand>();
		List<Subcommand> listCC2a = new ArrayList<Subcommand>();
		listCC2.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Chicken"));
		listCC2.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Groceries"));
		listCC2.add(new Subcommand(Subcommand.TYPE.START, "01/01/2014"));
		listCC2.add(new Subcommand(Subcommand.TYPE.END, "02/01/2014"));
		listCC2.add(new Subcommand(Subcommand.TYPE.LOCATION, "Clementi"));
		listCC2.add(new Subcommand(Subcommand.TYPE.NOTE,
				"Raw chicken breast YUM YUM"));

		List<Subcommand> listCC3 = new ArrayList<Subcommand>();
		listCC3.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Milk"));
		listCC3.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Groceries"));
		listCC3.add(new Subcommand(Subcommand.TYPE.START, "01/01/2014"));
		listCC3.add(new Subcommand(Subcommand.TYPE.END, "02/01/2014"));
		listCC3.add(new Subcommand(Subcommand.TYPE.LOCATION, "Pasir Ris"));
		listCC3.add(new Subcommand(Subcommand.TYPE.NOTE, "I love milk!"));

		Date startDate = Date.determineDate("01/01/2014");
		Date endDate = Date.determineDate("02/01/2014");

		Task taskOne = new Task("Buy Milk", "Groceries", "Clementi",
				"Meiji Chocolate Milk", startDate, endDate);

		/*
		 * Use case #1 - user wants to remove a task.
		 */
		Command addOne = new Add(listCC);
		CommandHandler.executeCommand(addOne);

		Command addTwo = new Add(listCC2);
		CommandHandler.executeCommand(addTwo); 

		Command removeTwo = new Remove(listCC2);
		CommandHandler.executeCommand(removeTwo);

		ArrayList<Task> atL = TotalTaskList.getInstance().getList();
		assertEquals(taskOne.toString(), ezCMessages.getInstance()
				.getStringOfTasks(atL));

		/*
		 * Use case #2 - user wants to delete the "Buy Milk" task in Pasir Ris
		 * and keep the one in Clementi.
		 */ 
		Command addThree = new Add(listCC3);
		CommandHandler.executeCommand(addThree);

		Command removeThree = new Remove(listCC2);
		CommandHandler.executeCommand(removeThree);

		ArrayList<Task> atL2 = TotalTaskList.getInstance().getList();
		assertEquals(taskOne.toString(), ezCMessages.getInstance()
				.getStringOfTasks(atL2)); 

	}

}
