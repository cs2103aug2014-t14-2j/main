package dataManipulation;

/**
 * Notes - 
 * 		TIME is probably going to be wrong, since it is under the DATE attribute of the Task, 
 * 		which I replace. 
 * 
 * 		Repeat ONCE has not been implemented. > How do START and END work with a repeat ONCE?
 * 
 * @author tyuiwei
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataEncapsulation.Time;
import dataManipulation.CommandType.COMMAND_TYPE;
import dataManipulation.Subcommand.FREQUENCY;
import dataManipulation.Subcommand.TYPE;

public class Repeat extends Command {

	private String freq;
	private String name;
	private String start;
	private String end;
	
	private LocalDate s;
	private LocalDate e;
	
	private int hrs;
	private int mins;
	private int durationOfTaskInDays;

	private Task t;
	private List<Subcommand> sc;
	private List<Task> repeatedTasks;
	
	// elements of copied task
	String repeatName;
	String repeatCategory;
	String repeatLocation;
	String repeatNote;
	Time repeatStart;
	Time repeatEnd;
	List<Subcommand> repeatSubcommands;

	private static List<Task> taskList = TotalTaskList.getInstance().getList();

	public Repeat(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.REPEAT, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		repeatedTasks = new ArrayList<Task>();
		assembleCCs(subcommands);
		getTask();
		initializerepeatSubcommands();
		getTaskDuration();
		getSubcommands();		
		checkStartEnd();
		
		if (freq.equals(FREQUENCY.DAILY.toString())) {
			List<LocalDate> daysHappening_daily = repeatStartDates_daily(start, end);
			for (LocalDate ld : daysHappening_daily) { //each ld is a new start date
				makeRepeat(ld);
			}
		} else if (freq.equals(FREQUENCY.WEEKLY.toString())) {
			int givenStartToActualStart = findDayOfWeek(t.getStartDate()).getValue() - s.getDayOfWeek().getValue();
			System.out.println("test 1");
			if (givenStartToActualStart < 0) {
				givenStartToActualStart = givenStartToActualStart + 7;
			}
			LocalDate nStart = s.plusDays(givenStartToActualStart);
			LocalDate nEnd = LocalDate.parse(ldParse(end));
			System.out.println("test 2");
			List<LocalDate> daysHappening_weekly = repeatStartDates_weekly(nStart, nEnd);
			System.out.println("test 3");
			for (LocalDate ld : daysHappening_weekly) {
				makeRepeat(ld);
			}

		} else if (freq.equals(FREQUENCY.MONTHLY.toString())) {
			List<LocalDate> daysHappening_monthly = repeatStartDates_monthly(start, end);
			for (LocalDate ld : daysHappening_monthly) {
				makeRepeat(ld);
			}
		} else if (freq.equals(FREQUENCY.ANNUALLY.toString())) {
			List<LocalDate> daysHappening_yearly = repeatStartDates_yearly(start, end);
			for (LocalDate ld : daysHappening_yearly) {
				makeRepeat(ld);
			}
		} else if (freq.equals(FREQUENCY.ONCE.toString())) {
			;
		} else {
			//THROW ERROR OR STRING
		}
		String x = ezCMessages.getInstance().getStringOfTasks(repeatedTasks);
		String unimplemented = "This command has not been finished. :)";
		return x;
	}

	private void initializerepeatSubcommands() throws BadSubcommandException, BadSubcommandArgException {
		repeatSubcommands = new ArrayList<Subcommand>();
		repeatName = t.getName();
		repeatCategory = t.getCategory();
		repeatLocation = t.getLocation();
		repeatNote = t.getNote();
		repeatStart = t.getStartTime();
		repeatEnd = t.getEndTime();
		
		Subcommand sc = new Subcommand(TYPE.NAME, repeatName);
		repeatSubcommands.add(sc);
		sc = new Subcommand(TYPE.CATEGORY, repeatCategory);
		repeatSubcommands.add(sc);
		sc = new Subcommand(TYPE.LOCATION, repeatLocation);
		repeatSubcommands.add(sc);
		sc = new Subcommand(TYPE.NOTE, repeatNote);
		repeatSubcommands.add(sc);		
		sc = new Subcommand(TYPE.STARTTIME, repeatStart.toString());
		repeatSubcommands.add(sc);
		sc = new Subcommand(TYPE.ENDTIME, repeatEnd.toString());
		repeatSubcommands.add(sc);

	}

	private void assembleCCs(List<Subcommand> ccs) throws Exception {
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
				s = LocalDate.parse(start);
				break;
			case END:
				end = ldParse(cc.getContents());
				e = LocalDate.parse(end);
				break;
			default:
				throw new BadSubcommandException("invalid repeat subcommand");
			}
		}
	}
	
	private void makeRepeat(LocalDate ld) throws Exception {
		String sd = ld.toString();
		String ed = ld.plusDays(durationOfTaskInDays).toString();
		List<Subcommand> lsc = modifyDate(sd, ed);
		Task repeatTask = new Add(repeatSubcommands).buildTask(lsc);
		new Add(repeatSubcommands).addTaskToList(repeatTask);
		repeatedTasks.add(repeatTask);
	}

	private String ldParse(String inDateFormat) throws Exception {
		Date d = new Date().determineDate(inDateFormat);
		int ddd = d.getDay();
		int mm = d.getMonth();
		int yyyy = d.getYear();
		String dd = String.format("%02d", ddd);
		String inLocalDateFormat = "" + yyyy + "-" + mm + "-" + dd;
		return inLocalDateFormat;
	}

	private List<LocalDate> repeatStartDates_daily(String st, String en) {
		LocalDate s = LocalDate.parse(st);
		LocalDate e = LocalDate.parse(en);
		List<LocalDate> totalDates = new ArrayList<>();
		while (!s.isAfter(e)) {
			totalDates.add(s);
			s = s.plusDays(1);
		}
		return totalDates;
	}
	
	private List<LocalDate> repeatStartDates_weekly(LocalDate st, LocalDate en) {
		List<LocalDate> totalDates = new ArrayList<>();
		while (!s.isAfter(e)) {
			totalDates.add(s);
			s = s.plusWeeks(1);
		}
		return totalDates;
	}
	
	private List<LocalDate> repeatStartDates_monthly(String st, String en) {
		LocalDate s = LocalDate.parse(st);
		LocalDate e = LocalDate.parse(en);
		List<LocalDate> totalDates = new ArrayList<>();
		while (!s.isAfter(e)) {
			totalDates.add(s);
			s = s.plusMonths(1);
		}
		return totalDates;
	}

	private List<LocalDate> repeatStartDates_yearly(String st, String en) {
		LocalDate s = LocalDate.parse(st);
		LocalDate e = LocalDate.parse(en);
		List<LocalDate> totalDates = new ArrayList<>();
		while (!s.isAfter(e)) {
			totalDates.add(s);
			s = s.plusYears(1);
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
		sc = new Add(repeatSubcommands).dismantleTask(t);
	}
	
	private List<Subcommand> modifyDate(String newstart, String newend) throws BadSubcommandException, BadSubcommandArgException {
		ArrayList<Subcommand> ccs = new ArrayList<Subcommand>(sc);
		for (int i=0; i<ccs.size(); i++) {
			Subcommand s = ccs.get(i);
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
	
	private void getTaskDuration() throws Exception {
		LocalDate st = LocalDate.parse(ldParse(t.getStartDate().toString()));
		LocalDate en = LocalDate.parse(ldParse(t.getEndDate().toString()));
		LocalDate ex = en.plusDays(1);
		Period p = Period.between(st, ex);
		durationOfTaskInDays = p.getDays();
	}
	
	private DayOfWeek findDayOfWeek(Date d) throws Exception {
		LocalDate date = LocalDate.parse(ldParse(d.toString()));
		return date.getDayOfWeek();
	}
	
	
	
	@Override
	protected void checkValidity() throws BadSubcommandException {
		try {
			checkForNoDuplicateSubcommands();
			checkRepeatFourComponents();
			checkFrequencyIsNotOnly();
		} catch (Exception e) {
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
/*
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
	} */

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
	
	private void checkStartEnd() throws Exception {
		if (s.isAfter(e)) {
			throw new Exception("Invalid dates - start is after end.");
		}
	}

	@Override
	public String undo() throws Exception {
		return null;
	}
}
