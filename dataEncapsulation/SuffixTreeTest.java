package dataEncapsulation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

//@author A0126720N

public class SuffixTreeTest {
	SuffixTree tree;
	List<String> list;
	List<String> expected;
	
	final String emptyString = "";

	@Before
	public void setUp() {
		tree = new SuffixTree();
		expected = new ArrayList<String>();
	}

	@Test
	public void noElementTest() {
		setUp();
		expected.add(emptyString);
		list = tree.getMatchList(emptyString);
		assertEquals("Test no added elements", expected, list);
	}

	@Test
	public void singleElementTest() {
		setUp();
		tree.add("A");
		expected.add("a");
		list = tree.getMatchList(emptyString);
		assertEquals("Single alpha character", expected, list);
		
		setUp();
		tree.add(emptyString);
		list = tree.getMatchList(emptyString);
		expected.add(emptyString);
		assertEquals("Add preexisting empty string", expected, list);
		
		setUp();
		tree.add("&");
		expected.add("&");
		list = tree.getMatchList(emptyString);
		assertEquals("Single nonalpha character", expected, list);
	}
	
	@Test
	public void singleLengthElementsTest() {
		setUp();
		tree.add("A");
		tree.add("a");
		tree.add("a");
		expected.add("a");
		list = tree.getMatchList(emptyString);
		assertEquals("Two of same alpha character", expected, list);
		
		setUp();
		tree.add("c");
		tree.add("%");
		tree.add("8");
		expected.add("%");
		expected.add("8");
		expected.add("c");
		list = tree.getMatchList(emptyString);
		assertEquals("Three different single-characters", expected, list);
	}
	
	@Test
	public void multipleLengthElementsTest() {
		setUp();
		tree.add("And");
		tree.add("a");
		tree.add("atr");
		expected.add("a");
		expected.add("and");
		expected.add("atr");
		list = tree.getMatchList(emptyString);
		assertEquals("words with same start letter", expected, list);
		
		setUp();
		tree.add("And");
		tree.add("a");
		tree.add("atr");
		tree.add("atr");
		expected.add("a");
		expected.add("and");
		expected.add("atr");
		list = tree.getMatchList(emptyString);
		assertEquals("duplicate word", expected, list);
		
		tree.add("break it up");
		tree.add("digger through all");
		tree.add("creation it is");
		expected.add("break it up");
		expected.add("creation it is");
		expected.add("digger through all");
		list = tree.getMatchList(emptyString);
		assertEquals("sentences", expected, list);
		
		// symbols used to be treated differently
		tree.add("%^&$(");
		tree.add("575");
		expected.add("%^&$(");
		expected.add("575");
		expected.sort(null);
		list = tree.getMatchList(emptyString);
		assertEquals("symbols", expected, list);
	}
	
	@Test
	public void shorterListTest() {
		setUp();
		tree.add("complete");
		tree.add("completed");
		tree.add("completes");
		tree.add("compliment");
		tree.add("complimented");
		
		expected.add("complete");
		expected.add("completed");
		expected.add("completes");
		expected.add("compliment");
		expected.add("complimented");
		list = tree.getMatchList("comp");
		assertEquals("request from middle of word", expected, list);
		
		expected.clear();
		expected.add("compliment");
		expected.add("complimented");
		list = tree.getMatchList("compliment");
		assertEquals("request noninclusive list from middle", expected, list);
		
		expected.clear();
		expected.add("complimented");
		list = tree.getMatchList("complimented");
		assertEquals("request the entirety of a unique word", expected, list);
		
		list = tree.getMatchList("testing");
		assertEquals("no matches", true, list.isEmpty());
	}
	
	@Test
	public void deleteTest() {
		setUp();
		tree.add("c");
		tree.add("%");
		tree.add("8");
		tree.remove("%");
		expected.add("8");
		expected.add("c");
		list = tree.getMatchList(emptyString);
		assertEquals("Delete single branch", expected, list);
		
		setUp();
		tree.add("And");
		tree.add("a");
		tree.add("atr");
		tree.remove("aND");
		expected.add("a");
		expected.add("atr");
		list = tree.getMatchList(emptyString);
		assertEquals("remove more complex word", expected, list);
		
		setUp();
		tree.add("And");
		tree.add("a");
		tree.add("atr");
		tree.remove("aNDD");
		expected.add("a");
		expected.add("and");
		expected.add("atr");
		list = tree.getMatchList(emptyString);
		assertEquals("remove a nonexistant word", expected, list);
		
		setUp();
		tree.add("And");
		tree.add("a");
		tree.add("atr");
		tree.remove("an");
		expected.add("a");
		expected.add("and");
		expected.add("atr");
		list = tree.getMatchList(emptyString);
		assertEquals("remove a word that was part of another", expected, list);
	}
}
