package dataManipulation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dataEncapsulation.Date;
import dataEncapsulation.Task;

public class EditTest {

	@Test
	public void testEdit() throws Exception {
		
		List<Subcommand> listCC = new ArrayList<Subcommand>();
		listCC.add(new Subcommand(Subcommand.TYPE.NAME, "Buy Milk"));
		listCC.add(new Subcommand(Subcommand.TYPE.TITLE, "Buy Chicken"));
		listCC.add(new Subcommand(Subcommand.TYPE.CATEGORY, "Groceries"));
		listCC.add(new Subcommand(Subcommand.TYPE.START, "01/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.END, "02/01/2014"));
		listCC.add(new Subcommand(Subcommand.TYPE.LOCATION, "Clementi"));
		listCC.add(new Subcommand(Subcommand.TYPE.NOTE, "Meiji Chocolate Milk"));
		
		Date startDate = new Date().determineDate("01/01/2014");
		Date endDate = new Date().determineDate("02/01/2014");
		Task editTaskExceptName = new Task("Buy Milk", "Groceries", "Clementi", "Meiji Chocolate Milk", startDate, endDate);
		Task actual = setTaskAttributes(editTaskExceptName, listCC);
		
		Task expected = new Task("Buy Chicken", "Groceries", "Clementi", "Meiji Chocolate Milk", startDate, endDate);
		
		assertEquals("Task name matches expected", expected.getName(), actual.getName());
		assertEquals("Task location matches expected", expected.getLocation(), actual.getLocation());
		assertEquals("Task note matches as expected", expected.getNote(), actual.getNote());
		assertEquals("Task category matches as expected", expected.getCategory(), actual.getCategory());
		assertEquals("Task start date matches as expected", expected.getStartDate().toString(), actual.getStartDate().toString());
		assertEquals("Task end date matches as expected", expected.getEndDate().toString(), actual.getEndDate().toString());
		
	}
	
	private Task setTaskAttributes(Task toEdit, List<Subcommand> taskAttributes) throws Exception {
		
		for(Subcommand cc : taskAttributes) {

			switch (cc.getType()) {

			case TITLE:	toEdit.setName(cc.getContents());
						break;

			case CATEGORY:	toEdit.setCategory(cc.getContents());
							break;

			case LOCATION:	toEdit.setLocation(cc.getContents());
							break;

			case END:	toEdit.setEndDate(new Date().determineDate(cc.getContents()));
						break;

			case NOTE:	toEdit.setNote(cc.getContents());
						break;

			default:
				break;
			}
		}
			
			return toEdit;
	}

}
