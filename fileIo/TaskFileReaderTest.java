package fileIo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fileIo.TaskFileReader.TASK_COMPONENT;

public class TaskFileReaderTest {

	@Test
	public void test() {
		String testString = new String("Test: hello");
		
		assertEquals("Test for extracting first word", "Test", 
				TaskFileReader.getFirstWord(testString));
		
		assertEquals("Test for component type Name", TASK_COMPONENT.NAME, 
				TaskFileReader.interpretTitle("Name"));
		assertEquals("Test for component type Category", TASK_COMPONENT.CATEGORY, 
				TaskFileReader.interpretTitle("CaTeGoRy"));
		assertEquals("Test for component type Start", TASK_COMPONENT.START, 
				TaskFileReader.interpretTitle("start"));
		assertEquals("Test for component type End", TASK_COMPONENT.END, 
				TaskFileReader.interpretTitle("end"));
		assertEquals("Test for component type LOCATION", TASK_COMPONENT.LOCATION, 
				TaskFileReader.interpretTitle("location"));
		assertEquals("Test for component type NOTE", TASK_COMPONENT.NOTE, 
				TaskFileReader.interpretTitle("note"));
		
		assertEquals("Test for component data", "hello", 
				TaskFileReader.getComponentData(testString));
	}

}
