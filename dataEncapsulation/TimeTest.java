package dataEncapsulation;

//@author A0115696W 

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeTest {

	@Test
	
	public void testTime() throws Exception {
	String input1 = "12pm";
	String input1a = "12am";
	String input2 = "11:59PM";
	String input3 = "2359";
	String input4 = "12:30";
	
	Time a = new Time().determineTime(input1);
	System.out.println("A!");
	Time b = new Time().determineTime(input2);
	Time c = new Time().determineTime(input3);
	Time d = new Time().determineTime(input4);
	Time e = new Time(12, 34);
	Time f = new Time().determineTime(input1a);
	
	System.out.println(a.toString());
	System.out.println(b.toString());
	System.out.println(c.toString());
	System.out.println(d.toString());
	System.out.println(e.toString());
	System.out.println(f.toString());
	
	assertEquals(a.compareTo(b), -1);
	assertEquals(b.compareTo(c) , 0);
	assertEquals(c.compareTo(d), 1);
	assertEquals(d.compareTo(e), -1);
	}
	

}
