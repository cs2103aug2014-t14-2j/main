package dataManipulation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.CommandType.COMMAND_TYPE;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.Subcommand.FREQUENCY;

public class Repeat extends Command {

	private String freq;
	private String name;
	private String start;
	private String end;
	private int hrs;
	private int mins;
	private Task t;
	
	private static List<Task> taskList = TotalTaskList.getInstance().getList();
	
	public Repeat(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.REPEAT, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		assembleCCs(subcommands);
		getTask();
		
		if (freq.equals(FREQUENCY.DAILY.toString())) {
			List<LocalDate> daysHappening = allDays();
			for (LocalDate ld : daysHappening) {
				Date d = Date.determineDate(ld.toString());
				//how do i make a duplicate task
				//how do i edit a duplicate task (edit requires adding a duplicate into ttl - not allowed)
				//how
			}
		} else if (freq.equals(FREQUENCY.WEEKLY.toString())) {
			
		} else if (freq.equals(FREQUENCY.MONTHLY.toString())) {
			
		} else if (freq.equals(FREQUENCY.ANNUALLY.toString())) {
			
		} else if (freq.equals(FREQUENCY.ONCE.toString())) {
			
		} else {
			//THROW ERROR OR STRING
		}
		String unimplemented = "This command has not been finished. :)";
		return unimplemented;
	}
	
	private void assembleCCs(List<Subcommand> ccs) {
		for (Subcommand cc : ccs ) {
			switch (cc.getType()) {
			case FREQUENCY :
				freq = cc.getContents();
				break;
			case NAME :
				name = cc.getContents();
				break;
			case START : 
				start = ldParse( cc.getContents() );
				
				break;
			case END : 
				end = ldParse( cc.getContents() );
				break;
			default:
				System.out.println("SHOULD PROBABLY THROW EXCEPTION HERE INSTEAD!!!");
			}
		}
	}

	private String ldParse(String inDateFormat) {
		Date d = Date.determineDate(inDateFormat);
		int dd = d.getDay();
		int mm = d.getMonth();
		int yyyy = d.getYear();
		String inLocalDateFormat = "" + yyyy + "-" + mm + "-" + dd;
		return inLocalDateFormat;
	}
	

	
	private List<LocalDate> allDays() {
		LocalDate s = LocalDate.parse(start);
		LocalDate e = LocalDate.parse(end);
		List<LocalDate> totalDates = new ArrayList<>();
		while (!s.isAfter(e)){
		    totalDates.add(s);
		    System.out.println(" "+s);
		    s=s.plusDays(1);
		}
		return totalDates;
	}
	
private void getTask() throws Exception {
		
		List<Task> tasks = ExactMatchSearcher.exactSearch(subcommands.get(0), taskList);
		if(tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(taskList, ActionException.ErrorLocation.EDIT, subcommands);
			throw moreThanOne;
		}
		else {
			t = tasks.get(0);
		}
	}
	
	@Override
	protected void checkValidity() throws BadSubcommandException {
		try {
			checkForNoDuplicateSubcommands();
			checkRepeatTwoComponents();
			checkFrequencyIsNotOnly();
		} catch (IllegalArgumentException e) {
			checkRepeatFourComponents();
			checkFrequencyIsOnly();
		}
		
	}
	protected void checkRepeatFourComponents() throws BadSubcommandException {
		checkForComponentAmount(4);
		
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			switch (component.getType()) {
				case FREQUENCY :	 // valid
					break;
				case NAME :
					break;
				case START :
					break;
				case END :
					break;
				default :
					throw new BadSubcommandException("invalid subcommand");
			}
		}
	}

	protected void checkRepeatTwoComponents() throws BadSubcommandException {
		checkForComponentAmount(2);
		
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			switch (component.getType()) {
				case FREQUENCY :	 // valid
					break;
				case NAME :
					break;
				default :
					throw new BadSubcommandException("invalid subcommand");
			}
		}
	}

	protected void checkFrequencyIsNotOnly() throws BadSubcommandException {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			if (component.getType() == Subcommand.TYPE.FREQUENCY) {
				if (component.getContents().equalsIgnoreCase(FREQUENCY.ONCE.toString())) {
					throw new BadSubcommandException("too few subcommands");
				}
			}
		}
	}

	protected void checkFrequencyIsOnly() throws BadSubcommandException {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);
			
			if (component.getType() == Subcommand.TYPE.FREQUENCY) {
				if (!component.getContents().equalsIgnoreCase(FREQUENCY.ONCE.toString())) {
					throw new BadSubcommandException("too many subcommands");
				}
			}
		}
	}
}
