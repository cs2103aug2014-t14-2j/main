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
	
	private ActionToggler tabToggle;
	private ActionToggler enterToggle;
	private ActionToggler escapeToggle;
	
	private JTextField entry;
	private JLabel status;
	
	public AutocompleteAction(ActionToggler tab, ActionToggler enter, 
			ActionToggler escape, JTextField ent, JLabel stat) {
		tabToggle = tab;
		enterToggle = enter;
		escapeToggle = escape;
		
		initializeToggles();
		
		entry = ent;
		status = stat;
	}

	private String initialText;
	private Autocomplete autocomplete = Autocomplete.getInstance();

	private int counter = 0;
	private List<String> completionList;

	public void actionPerformed(ActionEvent ev) {
		status.setText("autocomplete mode");
		initialText = entry.getText();
		toggleOn();
		completionList = autocomplete.complete(initialText);
		entry.setText(completionList.get(counter));
	}

	class AcceptAction extends AbstractAction {
		public void actionPerformed(ActionEvent ev)  {
			status.setText("accepted");
			toggleOff();
		}
	}

	private void incrementCounter() {
		if (counter >= completionList.size() - 1) {
			counter = 0;
		} else {
			++counter;
		}
	}

	class ContinueAction extends AbstractAction {
		public void actionPerformed(ActionEvent ev)  {
			incrementCounter();
			entry.setText(completionList.get(counter));
		}
	}

	class ExitAction extends AbstractAction {
		public void actionPerformed(ActionEvent ev)  {
			status.setText("exiting");
			entry.setText(initialText);
			toggleOff();
		}
	}
	
	private void initializeToggles() {
		tabToggle.initializeLesser(new ContinueAction());
		enterToggle.initializeLesser(new AcceptAction());
		escapeToggle.initializeLesser(new ExitAction());
	}
	
	private void toggleOn() {
		tabToggle.setLesser();
		enterToggle.setLesser();
		escapeToggle.setLesser();
	}
	
	private void toggleOff() {
		tabToggle.setMaster();
		enterToggle.setMaster();
		escapeToggle.setMaster();
	}

}