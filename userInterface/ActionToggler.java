package userInterface;

//@author A0126720N

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class ActionToggler {
	private JTextField textField;
	
	private String actionKey;
	private String label;
	
	private AbstractAction masterAction;
	private AbstractAction lesserAction;
	
	private boolean hasLesser = false;
	private boolean hasMaster = false;
	
	public ActionToggler() {}
	
	public ActionToggler(JTextField field, String key, String lab, 
			AbstractAction action) {
		textField = field;
		actionKey = key;
		label = lab;
		masterAction = action;
		
		hasMaster = true;
	}
	
	public void initializeLesser(AbstractAction action) {
		lesserAction = action;
		hasLesser = true;
	}
	
	public void initializeMaster(JTextField field, String key, String lab, 
			AbstractAction action) {
		textField = field;
		actionKey = key;
		label = lab;
		masterAction = action;
		
		hasMaster = true;
	}
	
	public void setMaster() {
		if (!hasMaster) {
			return;
		}
		
		InputMap im = textField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = textField.getActionMap();
		im.put(KeyStroke.getKeyStroke(actionKey), label);
		am.put(label, masterAction);
	}
	
	public void setLesser() {
		if (!hasLesser) {
			return;
		}
		
		InputMap im = textField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = textField.getActionMap();
		im.put(KeyStroke.getKeyStroke(actionKey), label);
		am.put(label, lesserAction);
	}
}
