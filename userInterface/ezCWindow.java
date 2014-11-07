package userInterface;

//@author A0126720N

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import dataManipulation.TotalTaskList;
import fileIo.FileIo;

@SuppressWarnings("serial")
public class ezCWindow extends JFrame {

	private JTextField userInput;
	private JLabel headerLabel;
	private JScrollPane scroller;
	private JLabel status;
	private JTextArea display;

	final static String CANCEL_ACTION = "cancel-userInput";
	final static String ENTER_ACTION = "enter-command";
	final static String TAB_ACTION = "toggle-autocomplete";

	private TotalTaskList totalTaskList = TotalTaskList.getInstance();
	private FileIo fileIo = FileIo.getInstance();
	private ezCMessages messages = ezCMessages.getInstance();

	private static ezCWindow window;

	private ezCWindow() {
		initComponents();
	}

	public static ezCWindow getInstance() {
		if (window == null) {
			window = new ezCWindow();
		}

		return window;
	}

	private void initComponents() {
		initializeStaticMembers();

		initializeLayout();

		initializeActions();

		try {
			fileIo.initializeTaskList(totalTaskList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		pack();
	}

	private void initializeActions() {
		userInput.setFocusTraversalKeys(
				KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, 
				Collections.emptySet());
		
		ActionToggler enterToggle = new ActionToggler();
		ActionToggler tabToggle = new ActionToggler();
		ActionToggler escapeToggle = new ActionToggler();

		AutocompleteAction tabAction = new AutocompleteAction(status, userInput);
		CommandHandlingAction enterAction = new CommandHandlingAction(status, 
				display, userInput, enterToggle);
		CancelAction escAction = new CancelAction();
		
		enterToggle.initializeMaster(userInput, "ENTER", ENTER_ACTION, 
				enterAction);
		tabToggle.initializeMaster(userInput, "TAB", TAB_ACTION, tabAction);
		escapeToggle.initializeMaster(userInput, "ESCAPE", CANCEL_ACTION, 
				escAction);
		
		enterToggle.setMaster();
		tabToggle.setMaster();
		escapeToggle.setMaster();
	}

	private void initializeLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		initializeHorizontalGroup(layout);

		initializeVerticalGroup(layout);
		
		status.setText(" ");
	}

	private void initializeStaticMembers() {
		userInput = new JTextField();
		display = new JTextArea(messages.getUserHelpMessage());
		status = new JLabel();
		headerLabel = new JLabel();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("ezC");

		display.setColumns(20);
		display.setLineWrap(true);
		display.setRows(5);
		display.setWrapStyleWord(true);
		display.setEditable(false);
		scroller = new JScrollPane(display);
	}

	//@author Oracle, with minor edits
	private void initializeVerticalGroup(GroupLayout layout) {
		//Create a parallel group for the vertical axis
		ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		//Create a sequential group v1
		SequentialGroup v1 = layout.createSequentialGroup();
		//Add a container gap to the sequential group v1
		v1.addContainerGap();
		//Create a parallel group v2
		ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		v2.addComponent(headerLabel);
		v2.addComponent(userInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		//Add the group v2 tp the group v1
		v1.addGroup(v2);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
		v1.addComponent(status);
		v1.addComponent(scroller, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
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
		h2.addComponent(scroller, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
		h2.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);

		//Create a sequential group h3
		SequentialGroup h3 = layout.createSequentialGroup();
		h3.addComponent(headerLabel);
		h3.addComponent(userInput, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);

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

	//@author A0126720N
	class CancelAction extends AbstractAction {
		public void actionPerformed(ActionEvent ev) {
			userInput.setText("");
			status.setText(" ");
		}
	}
	
	//@author Oracle
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