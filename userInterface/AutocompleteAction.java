package userInterface;

//@author A0126720N

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
class AutocompleteAction extends AbstractAction {	
	final static String EXIT_ACTION = "exit-entry";
	
	private JTextField entry;
	private JLabel status;
	
	private String lastText;
	private Autocomplete autocomplete = Autocomplete.getInstance();

	private int counter = 0;
	private List<String> completionList;
	
	public AutocompleteAction(JLabel stat, JTextField ent) {
		entry = ent;
		status = stat;
		
		lastText = entry.getText();
		completionList = autocomplete.complete(lastText);
		counter = 0;
	}

	public void actionPerformed(ActionEvent ev) {
		status.setText(" ");
		String current = removeLeadingSpace(entry.getText());
		if (current.equalsIgnoreCase(lastText)) {
			entry.setText(completionList.get(counter));
			incrementCounter();
			lastText = entry.getText();
		} else {
			counter = 0;
			lastText = entry.getText();
			completionList = autocomplete.complete(lastText);
			entry.setText(completionList.get(counter));
			lastText = entry.getText();
		}
	}

	private String removeLeadingSpace(String text) {
		String whitespace = "\\s+";
		if (text.startsWith(" ")) {
			return text.replaceFirst(whitespace, "");
		} else {
			return text;
		}
	}

	private void incrementCounter() {
		if (counter >= completionList.size() - 1) {
			counter = 0;
		} else {
			++counter;
		}
	}

}