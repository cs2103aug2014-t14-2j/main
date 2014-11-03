package userInterface;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import userInterface.CommandType.COMMAND_TYPE;

public class AutocompleteTest {
	Autocomplete autocomplete;
	List<String> key;
	
	@Before
	public void setUp() {
		autocomplete = Autocomplete.getInstance();
		key = new ArrayList<String>();
	}
	
	@Test
	public void getCommandTest() {
		for (COMMAND_TYPE type : COMMAND_TYPE.values()) {
			key.add(type.toString());
		}
		
		key.sort(null);
		
		String command = "";
		assertEquals("No given command", key, autocomplete.complete(command));
		
		key.clear();
		key.add("add");
		key.add("all");
		command = "a";
		assertEquals("beginning of a command", key, autocomplete.complete(command));
		
		key.clear();
		command = "bak";
		key.add("bak");
		assertEquals("beginning of an incommand", key, autocomplete.complete(command));
	}
	
	@Test
	public void getSubcommandTest() {
		key.clear();
		CommandType records = CommandType.getInstance();
		List<String> list = records.getSubcommands(COMMAND_TYPE.SEARCH);
			
		for (int i = 0; i < list.size(); ++i) {
			key.add("search " + list.get(i));
		}
		key.sort(null);
		
		String command = "search ";
		assertEquals("all search subcommands", key, autocomplete.complete(command));

		key.clear();
		key.add("search location");
		command = "search l";
		assertEquals("one add subcommands", key, autocomplete.complete(command));
	}
	
	@Test
	public void getSubcommandArgTest() {
		key.clear();
		autocomplete.addCategory("my cat");
		autocomplete.addCategory("my dog");
		autocomplete.addCategory("their cat");
		key.add("add \"new task\" category \"my cat\"");
		key.add("add \"new task\" category \"my dog\"");
		
		String command = "add \"new task\" category \"m";
		assertEquals("complete a category request with first quote", key, autocomplete.complete(command));
		
		// with first quote and without treated differently
		key.add("add \"new task\" category \"their cat\"");
		command = "add \"new task\" category ";
		assertEquals("complete a category request without first quote", key, autocomplete.complete(command));
		
		key.clear();
		autocomplete.addTitle("Go shopping");
		autocomplete.addTitle("Study for quiz");
		autocomplete.addTitle("Study for exam");
		key.add("add \"study for quiz\"");
		key.add("add \"study for exam\"");
		command = "add \"s";
		key.sort(null);
		assertEquals("complete a category request without first quote", key, autocomplete.complete(command));
	}

}
