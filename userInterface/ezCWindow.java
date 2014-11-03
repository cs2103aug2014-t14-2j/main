package userInterface;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dataManipulation.Command;
import dataManipulation.TotalTaskList;
import dataManipulation.UndoRedoList;
import fileIo.FileIo;


@SuppressWarnings("serial")
public class ezCWindow extends JFrame
	implements DocumentListener {

	private JTextField entry;
	private JLabel jLabel1;
	private JScrollPane jScrollPane1;
	private JLabel status;
	private JTextArea textArea;

	final static String CANCEL_ACTION = "cancel-entry";
	final static String ENTER_ACTION = "enter-command";
	final static String TAB_ACTION = "toggle-autocomplete";

	private UserInterface ui = UserInterface.getInstance();
	private TotalTaskList totalTaskList = TotalTaskList.getInstance();
	private FileIo fileIo = FileIo.getInstance();
	private ezCMessages messages = ezCMessages.getInstance();
	private CommandInterpreter interpreter = CommandInterpreter.getInstance();

	public ezCWindow() {
		initComponents();

		entry.getDocument().addDocumentListener(this);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 */

	private void initComponents() {
		initializeStaticMembers();

		initializeLayout();
		
		initializeActions();
		
		fileIo.initializeTaskList(totalTaskList.getList());
		
		pack();
	}

	private void initializeActions() {
		
		InputMap im = entry.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		entry.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
		ActionMap am = entry.getActionMap();
		
		im.put(KeyStroke.getKeyStroke("ENTER"), ENTER_ACTION);
		am.put(ENTER_ACTION, new EnterAction());
		
		im.put(KeyStroke.getKeyStroke("TAB"), TAB_ACTION);
		am.put(TAB_ACTION, new TabAction());
		
		im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
		am.put(CANCEL_ACTION, new CancelAction());
	}

	private void initializeLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		initializeHorizontalGroup(layout);

		initializeVerticalGroup(layout);
	}

	private void initializeStaticMembers() {
		entry = new JTextField();
		textArea = new JTextArea(messages.getUserHelpMessage());
		status = new JLabel();
		jLabel1 = new JLabel();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("ezC");

		textArea.setColumns(20);
		textArea.setLineWrap(true);
		textArea.setRows(5);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		jScrollPane1 = new JScrollPane(textArea);
	}

	private void initializeVerticalGroup(GroupLayout layout) {
		//Create a parallel group for the vertical axis
		ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		//Create a sequential group v1
		SequentialGroup v1 = layout.createSequentialGroup();
		//Add a container gap to the sequential group v1
		v1.addContainerGap();
		//Create a parallel group v2
		ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		v2.addComponent(jLabel1);
		v2.addComponent(entry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		//Add the group v2 tp the group v1
		v1.addGroup(v2);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
		v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
		v1.addComponent(status);
		v1.addContainerGap();

		//Add the group v1 to the group vGroup
		vGroup.addGroup(v1);
		//Create the vertical group
		layout.setVerticalGroup(vGroup);
	}

	private void initializeHorizontalGroup(GroupLayout layout) {
		//Create a parallel group for the horizontal axis
		ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

		//Create a sequential and a parallel groups
		SequentialGroup h1 = layout.createSequentialGroup();
		ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

		//Add a container gap to the sequential group h1
		h1.addContainerGap();

		//Add a scroll pane and a label to the parallel group h2
		h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
		h2.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);

		//Create a sequential group h3
		SequentialGroup h3 = layout.createSequentialGroup();
		h3.addComponent(jLabel1);
		h3.addComponent(entry, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);

		//Add the group h3 to the group h2
		h2.addGroup(h3);
		//Add the group h2 to the group h1
		h1.addGroup(h2);

		h1.addContainerGap();

		//Add the group h1 to the hGroup
		hGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
		//Create the horizontal group
		layout.setHorizontalGroup(hGroup);
	}

	void message(String msg) {
		status.setText(msg);
	}

	// DocumentListener methods

	public void insertUpdate(DocumentEvent ev) {

	}

	public void removeUpdate(DocumentEvent ev) {

	}

	public void changedUpdate(DocumentEvent ev) {
	}

	class CancelAction extends AbstractAction {
		public void actionPerformed(ActionEvent ev) {
			entry.setText("");
		}
	}
	
	class TabAction extends AbstractAction {
		
		final static String EXIT_ACTION = "exit-entry";
		
		private String initialText;
		private Autocomplete autocomplete = Autocomplete.getInstance();
		
		private int counter = 0;
		private List<String> completionList;
		
		private void initializeActions() {
			InputMap im = entry.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			ActionMap am = entry.getActionMap();
			
			im.put(KeyStroke.getKeyStroke("ENTER"), ENTER_ACTION);
			am.put(ENTER_ACTION, new AcceptAction());
			
			im.put(KeyStroke.getKeyStroke("TAB"), TAB_ACTION);
			am.put(TAB_ACTION, new ContinueAction());
			
			im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
			am.put(CANCEL_ACTION, new ExitAction());
		}
		
		private void endDependencies() {
			InputMap im = entry.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			ActionMap am = entry.getActionMap();
			
			im.put(KeyStroke.getKeyStroke("ENTER"), ENTER_ACTION);
			am.put(ENTER_ACTION, new EnterAction());
			
			im.put(KeyStroke.getKeyStroke("TAB"), TAB_ACTION);
			am.put(TAB_ACTION, new TabAction());
			
			im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
			am.put(CANCEL_ACTION, new CancelAction());
		}
		
		public void actionPerformed(ActionEvent ev) {
			status.setText("autocomplete mode");
			initialText = entry.getText();
			initializeActions();
			completionList = autocomplete.complete(initialText);
			entry.setText(completionList.get(counter));
		}
		
		class AcceptAction extends AbstractAction {
			public void actionPerformed(ActionEvent ev)  {
				status.setText("accepted");
				endDependencies();
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
				endDependencies();
			}
		}
		
	}
	
	class EnterAction extends AbstractAction {
		public void actionPerformed(ActionEvent ev)  {
			try {
				String input = entry.getText();
				Command command = interpreter.formCommand(input);
				String feedback = CommandHandler.executeCommand(command);
				UndoRedoList.getInstance().pushUndoCommand(command);	// Adds the command to the undo command stack
				textArea.setText(feedback);
				entry.setText("");
			} catch (Exception e) {
				status.setText(e.getMessage());
			}
		}
	}


	public static void main(String args[]) {
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new ezCWindow().setVisible(true);
			}
		});
	}

}