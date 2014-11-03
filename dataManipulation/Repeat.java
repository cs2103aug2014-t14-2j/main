package dataManipulation;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.CommandType.COMMAND_TYPE;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
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
	private int durationOfTaskInDays;
	private int durationOfTaskInHours;
	private int durationOfTaskInMins;

	private Task t;
	private List<Subcommand> sc;

	private static List<Task> taskList = TotalTaskList.getInstance().getList();

	public Repeat(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.REPEAT, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		assembleCCs(subcommands);
		getTask();
		getTaskDuration();
		getSubcommands();
		
		if (freq.equals(FREQUENCY.DAILY.toString())) {
			List<LocalDate> daysHappening = durationInDays(start, end);
			for (LocalDate ld : daysHappening) {
				Date d = Date.determineDate(ld.toString());
				
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
		for (Subcommand cc : ccs) {
			switch (cc.getType()) {
			case FREQUENCY:
				freq = cc.getContents();
				break;
			case NAME:
				name = cc.getContents();
				break;
			case START:
				start = ldParse(cc.getContents());
				break;
			case END:
				end = ldParse(cc.getContents());
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

	private List<LocalDate> durationInDays(String st, String en) {
		LocalDate s = LocalDate.parse(st);
		LocalDate e = LocalDate.parse(en);
		List<LocalDate> totalDates = new ArrayList<>();
		while (!s.isAfter(e)) {
			totalDates.add(s);
			System.out.println(" " + s);
			s = s.plusDays(1);
		}
		return totalDates;
	}

	private void getTask() throws Exception {

		List<Task> tasks = ExactMatchSearcher.exactSearch(subcommands.get(0),
				taskList);
		if (tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(taskList,
					ActionException.ErrorLocation.EDIT, subcommands);
			throw moreThanOne;
		} else {
			t = tasks.get(0);
		}
	}

	private void getSubcommands() throws BadSubcommandException, BadSubcommandArgException, BadCommandException {
		sc = new Add(subcommands).dismantleTask(t);
	}
	
	private List<Subcommand> modifyDate(String newstart, String newend) throws BadSubcommandException, BadSubcommandArgException {
		ArrayList<Subcommand> ccs = new ArrayList<Subcommand>(sc);
		for (Subcommand s : ccs) {
			switch(s.getType()) {
			case START : 
				Subcommand nS = new Subcommand(Subcommand.TYPE.START, newstart);
				ccs.remove(s);
				ccs.add(nS);
				break;
			case END :
				Subcommand nE = new Subcommand(Subcommand.TYPE.START, newend);
				ccs.remove(s);
				ccs.add(nE);
				break;
			default : 
				break;
			}
		}
		return ccs;
	}
	
	private void getTaskDuration() {
		LocalDate st = LocalDate.parse(ldParse(t.getStartDate().toString()));
		LocalDate en = LocalDate.parse(ldParse(t.getEndDate().toString()));
		LocalDate ex = en.plusDays(1);
		Period p = Period.between(st, ex);
		durationOfTaskInDays = p.getDays();
		
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
			case FREQUENCY: // valid
				break;
			case NAME:
				break;
			case START:
				break;
			case END:
				break;
			default:
				throw new BadSubcommandException("invalid subcommand");
			}
		}
	}

	protected void checkRepeatTwoComponents() throws BadSubcommandException {
		checkForComponentAmount(2);

		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);

			switch (component.getType()) {
			case FREQUENCY: // valid
				break;
			case NAME:
				break;
			default:
				throw new BadSubcommandException("invalid subcommand");
			}
		}
	}

	protected void checkFrequencyIsNotOnly() throws BadSubcommandException {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);

			if (component.getType() == Subcommand.TYPE.FREQUENCY) {
				if (component.getContents().equalsIgnoreCase(
						FREQUENCY.ONCE.toString())) {
					throw new BadSubcommandException("too few subcommands");
				}
			}
		}
	}

	protected void checkFrequencyIsOnly() throws BadSubcommandException {
		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);

			if (component.getType() == Subcommand.TYPE.FREQUENCY) {
				if (!component.getContents().equalsIgnoreCase(
						FREQUENCY.ONCE.toString())) {
					throw new BadSubcommandException("too many subcommands");
				}
			}
		}
	}
}
