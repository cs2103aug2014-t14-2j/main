import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class TaskTest {
	@Test
	//testtoString
	public void testToString() {
		Date startdate = new Date(25, 9, 2015);
		Date enddate = new Date(25,9, 2015);
		Task cricketMatch = new Task("Cricket Match", "Sports", "Ceylon Sports Club", "Reporting Time is at 7.30am", startdate, enddate);
		System.out.println(cricketMatch.toString());
	}
	@Test
	//testSortTask
	public void testSortTask(){
		ArrayList<Task> tasks = new ArrayList<Task>();
		Date temp1 = new Date();
		tasks.add(new Task("Andrew", "A", temp1, new Date(25,9,2018)));
		tasks.add(new Task("Bravo", "B", temp1, new Date(26,9,2015)));
		tasks.add(new Task("Charlie", "C", temp1, new Date(25,12,2014)));
		//Collections.sort(tasks, new sortTaskByEndDate());
		Collections.sort(tasks, new sortTaskByName());
		System.out.println(tasks.toString());
	}
}
