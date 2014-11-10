package dataManipulation;

//@author A0115696W

import org.junit.Test;

import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataEncapsulation.Time;

public class RepeatTest {

	@Test
	public void test() throws Exception {
		Time myTime1 = new Time().determineTime("1pm");
		Date myDate1 = new Date().determineDate("10/10/2014");
		Task myTask1 = new Task("Buy milk", myDate1, myTime1);

		
	}

}
