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
	
	private String initialText;
	private String lastText;
	private Autocomplete autocomplete = Autocomplete.getInstance();

	private int counter = 0;
	private List<String> completionList;
	
	public AutocompleteAction(JLabel stat, JTextField ent) {
		entry = ent;
		status = stat;
		
		initialText = entry.getText();
		completionList = autocomplete.complete(initialText);
		counter = 0;
		entry.setText(completionList.get(counter));
		lastText = entry.getText();
	}

	public void actionPerformed(ActionEvent ev) {
		status.setText(" ");
		if (entry.getText().equalsIgnoreCase(lastText)) {
			incrementCounter();
			entry.setText(completionList.get(counter));
			lastText = entry.getText();
		} else {
			counter = 0;
			initialText = entry.getText();
			completionList = autocomplete.complete(initialText);
			entry.setText(completionList.get(counter));
			lastText = entry.getText();
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