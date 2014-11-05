/**
 * A suffix tree which allows one entry to be inserted at a time.
 * It is not case sensitive.
 * It will search for a match to the prefix given and return all strings
 * beginning with that prefix, including that prefix.
 * If no matches are found, it returns and empty list.
 * 
 */

//@author A0126720N

package dataEncapsulation;

import java.util.ArrayList;
import java.util.List;

public class SuffixTree {
	private List<SuffixTree> tree;

	private String match;

	private static final String EMPTY_STRING = "";

	public SuffixTree() {
		match = EMPTY_STRING;
		tree = new ArrayList<SuffixTree>();
	}

	public void add(String string) {
		if (tree.isEmpty()) {
			initializeTree(string);
			return;
		}

		String newChar = getFirstCharString(string);

		int index = 0;
		for (; index < tree.size(); ++index) {
			String currentChar = tree.get(index).match;

			int comparison = newChar.compareTo(currentChar);
			int equal = 0;

			if (comparison > equal ) {
				// do nothing, keep going
			} else if (comparison < equal) {
				insertBefore(string, index);
				return;
			} else if (comparison == equal && !newChar.isEmpty()) {
				tree.get(index).add(getAfterFirstChar(string));
				return;
			}
		}

		// made it out of the loop, insert at end
		if (!newChar.isEmpty()) {
			insertAtEnd(string);
		}
	}

	public void remove(String string) {
		string = string.toLowerCase();
		
		if (string.isEmpty() && tree.isEmpty()) {
			return;
		}
		
		if (tree.size() == 1) {
			deleteFinalBranch(string);
			deleteIfSubtreeIsEmpty(0);
			return;
		}
		
		String firstChar = getFirstCharString(string);
		
		for (int i = 0; i < tree.size(); ++i) {
			SuffixTree subTree = tree.get(i);
			
			if (firstChar.equalsIgnoreCase(subTree.match)) {
				String stringMinusFirstChar = getAfterFirstChar(string);
				subTree.remove(stringMinusFirstChar);
				
				// if calling remove deleted everything in the subtree, 
				//delete subtree
				deleteIfSubtreeIsEmpty(i);
			}
		}
		
		return;
	}

	public List<String> getMatchList(String key) {
		return getMatchList(key, EMPTY_STRING);
	}
	
	private void deleteIfSubtreeIsEmpty(int i) {
		if (tree.size() <= i) {
			return;
		}
		
		SuffixTree subTree = tree.get(i);
		
		if (subTree.tree.isEmpty()) {
			tree.remove(i);
		}
	}
	
	private boolean deleteFinalBranch(String string) {
		boolean shouldDelete = true;
		
		if (tree.size() != 1) {
			remove(string);	// tree splits further down, don't delete path
			return false;
		} else if (tree.get(0).tree.isEmpty() && string.isEmpty()) {
			return true;	// complete match
		} else if (string.isEmpty()) {
			return false;	// string terminated early, not a match
		} else if (tree.get(0).tree.isEmpty() && !string.isEmpty()) {
			return false;	// string did not terminate at end of tree
		}
		
		SuffixTree nextTree = tree.get(0);
		String withoutFirst = getAfterFirstChar(string);
		shouldDelete = nextTree.deleteFinalBranch(withoutFirst);
		
		if (shouldDelete) {
			tree.remove(0);
			return true;
		} else {
			return false;
		}
	}

	private List<String> getMatchList(String key, String masterKey) {
		List<String> list = new ArrayList<String>();

		if (key.isEmpty()) {
			if (!masterKey.isEmpty()) {
				// prevent duplication of last letter
				masterKey = masterKey.substring(0,masterKey.length() - 1);
			}
			
			list = getTree(masterKey);
			return list;
		}
		
		String firstChar = getFirstCharString(key);

		for (int i = 0; i < tree.size(); ++i) {
			SuffixTree subTree = tree.get(i);
			if (firstChar.equalsIgnoreCase(subTree.match)) {
				String keyMinusFirstChar = getAfterFirstChar(key);
				list.addAll(subTree.getMatchList(keyMinusFirstChar, masterKey + firstChar));
			}
		}

		return list;
	}

	private List<String> getTree(String prefix) {
		List<String> list = new ArrayList<String>();

		if (tree.isEmpty()) {
			list.add(prefix);
		}

		for (int i = 0; i < tree.size(); ++i) {
			list.addAll(tree.get(i).getTree(prefix + match));
		}

		return list;
	}

	private SuffixTree(String string) {
		match = getFirstCharString(string).toLowerCase();

		if (string.isEmpty()) {
			tree = new ArrayList<SuffixTree>();
		} else {
			initializeTree(getAfterFirstChar(string));
		}
	}
	
	private String getAfterFirstChar(String string) {
		if (string.isEmpty()) {
			return EMPTY_STRING;
		}
		
		return string.substring(1);
	}
	
	private String getFirstCharString(String string) {
		if (string.isEmpty()) {
			return EMPTY_STRING;
		}
		
		return string.substring(0, 1);
	}

	private void initializeTree(String string) {
		SuffixTree subTree = new SuffixTree(string);
		tree = new ArrayList<SuffixTree>();
		tree.add(subTree);
	}

	private void insertAtEnd(String string) {
		SuffixTree newTree = new SuffixTree(string);
		tree.add(newTree);
	}

	private void insertBefore(String string, int index) {
		SuffixTree newTree = new SuffixTree(string);
		tree.add(index, newTree);
	}
}
