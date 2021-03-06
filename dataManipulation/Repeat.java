package dataManipulation;

//@author A0115696W

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
	private String start;
	@SuppressWarnings("unused")
	private String name;
	private String end;
	private boolean hasFour;

	private LocalDate s;
	private LocalDate e;

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
	List<Command> adds; 

	private static List<Task> taskList = TotalTaskList.getInstance().getList();

	public Repeat(List<Subcommand> commandComponents)
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.REPEAT, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		hasFour = false;
		repeatedTasks = new ArrayList<Task>();
		adds = new ArrayList<Command>();
		assembleCCs(subcommands);
		getTask();
		initializerepeatSubcommands();
		getTaskDuration();
		getSubcommands();
		if (hasFour) {
			checkStartEnd();
		}
		
		
		if (freq.equals(FREQUENCY.DAILY.toString())) {
			List<LocalDate> daysHappening_daily = repeatStartDates_daily(start,
					end);
			for (LocalDate ld : daysHappening_daily) { // each ld is a new start
														// date
				makeRepeat(ld);
			}
		} else if (freq.equals(FREQUENCY.WEEKLY.toString())) {
			int givenStartToActualStart = findDayOfWeek(t.getStartDate())
					.getValue() - s.getDayOfWeek().getValue();
			if (givenStartToActualStart < 0) {
				givenStartToActualStart = givenStartToActualStart + 7;
			}
			LocalDate nStart = s.plusDays(givenStartToActualStart);
			LocalDate nEnd = LocalDate.parse(ldParse(end));
			List<LocalDate> daysHappening_weekly = repeatStartDates_weekly(
					nStart, nEnd);
			for (LocalDate ld : daysHappening_weekly) {
				makeRepeat(ld);
			}

		} else if (freq.equals(FREQUENCY.MONTHLY.toString())) {
			List<LocalDate> daysHappening_monthly = repeatStartDates_monthly(
					start, end);
			for (LocalDate ld : daysHappening_monthly) {
				makeRepeat(ld);
			}
		} else if (freq.equals(FREQUENCY.ANNUALLY.toString())) {
			List<LocalDate> daysHappening_yearly = repeatStartDates_yearly(
					start, end);
			for (LocalDate ld : daysHappening_yearly) {
				makeRepeat(ld);
			}
		} else if (freq.equals(FREQUENCY.ONCE.toString())) {
			makeRepeat(s);
		} else {
			// THROW ERROR OR STRING
		}
		String x = ezCMessages.getInstance().getStringOfTasks(repeatedTasks);
		return x;
	}

	private void initializerepeatSubcommands() throws BadSubcommandException,
			BadSubcommandArgException {

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
			case ON:
				start = ldParse(cc.getContents());
				s = LocalDate.parse(start);
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
		// boolean added = new Add(repeatSubcommands).addTaskToList(repeatTask);

		repeatedTasks.add(repeatTask);
		Command cmd = new Add(lsc);
		adds.add(cmd);
		cmd.execute();
	}

	private String ldParse(String inDateFormat) throws Exception {
		Date d = new Date().determineDate(inDateFormat);
		int ddd = d.getDay();
		int mmm = d.getMonth();
		int yyyy = d.getYear();
		String dd = String.format("%02d", ddd);
		String mm = String.format("%02d", mmm);
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
			ActionException moreThanOne = new ActionException(tasks,
					ActionException.ErrorLocation.REPEAT, subcommands);
			throw moreThanOne;
		} else {
			t = tasks.get(0);
		}
	}

	private void getSubcommands() throws BadSubcommandException,
			BadSubcommandArgException, BadCommandException {
		sc = Add.dismantleTask(t);

	}

	private List<Subcommand> modifyDate(String newstart, String newend)
			throws BadSubcommandException, BadSubcommandArgException {
		ArrayList<Subcommand> ccs = new ArrayList<Subcommand>();
		boolean hasEnd = false;
		for (int i = 0; i < sc.size(); i++) {
			Subcommand s = sc.get(i);

			switch (s.getType()) {
			case START:
				Subcommand nS = new Subcommand(Subcommand.TYPE.START, newstart);
				ccs.add(nS);
				break;
			case END:
				hasEnd = true;
				Subcommand nE = new Subcommand(Subcommand.TYPE.END, newend);
				ccs.add(nE);
				break;
			default:
				ccs.add(s);
				break;
			}
		}
		if (!hasEnd) {
			Subcommand nE = new Subcommand(Subcommand.TYPE.END, newend);
			ccs.add(nE);
		}

		return ccs;
	}

	private void getTaskDuration() throws Exception {
		LocalDate st = LocalDate.parse(ldParse(t.getStartDate().toString()));
		LocalDate en = LocalDate.parse(ldParse(t.getEndDate().toString()));
		Period p = Period.between(st, en);
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
			checkFrequencyIsNotOnce();
		} catch (Exception e) {
			checkRepeatThreeComponents();
			checkFrequencyIsOnce();
		}

	}

	protected void checkRepeatFourComponents() throws BadSubcommandException {
		checkForComponentAmount(4);
		hasFour = true;
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

	protected void checkRepeatThreeComponents() throws BadSubcommandException {
		checkForComponentAmount(3);

		for (int i = 0; i < subcommands.size(); ++i) {
			Subcommand component = subcommands.get(i);

			switch (component.getType()) {
			case FREQUENCY: // valid
				if (!component.getContents().equalsIgnoreCase(
						FREQUENCY.ONCE.toString())) {
					throw new BadSubcommandException("can only leave off end "
							+ "date for \"once\"");
				}
				break;
			case NAME:
				break;
			case ON:
				break;
			default:
				throw new BadSubcommandException("invalid subcommand");
			}
		}
	}

	protected void checkFrequencyIsNotOnce() throws BadSubcommandException {
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

	protected void checkFrequencyIsOnce() throws BadSubcommandException {
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
		for (Command cmd : adds) {
			cmd.undo();
		}
		ezCMessages messages = ezCMessages.getInstance();
		String x = messages.getUndoRepeatMessage(t);
		return x;
	}

	public String furtherRepeat(Task ta, List<Subcommand> sco) throws Exception {
		repeatedTasks = new ArrayList<Task>();
		t = ta;
		hasFour = false;
		assembleCCs(sco);
		initializerepeatSubcommands();
		getTaskDuration();
		getSubcommands();
		if (hasFour) {
			checkStartEnd();
		}

		if (freq.equals(FREQUENCY.DAILY.toString())) {
			List<LocalDate> daysHappening_daily = repeatStartDates_daily(start,
					end);
			for (LocalDate ld : daysHappening_daily) { // each ld is a new start
														// date
				makeRepeat(ld);
			}
		} else if (freq.equals(FREQUENCY.WEEKLY.toString())) {
			int givenStartToActualStart = findDayOfWeek(t.getStartDate())
					.getValue() - s.getDayOfWeek().getValue();
			if (givenStartToActualStart < 0) {
				givenStartToActualStart = givenStartToActualStart + 7;
			}
			LocalDate nStart = s.plusDays(givenStartToActualStart);
			LocalDate nEnd = LocalDate.parse(ldParse(end));
			List<LocalDate> daysHappening_weekly = repeatStartDates_weekly(
					nStart, nEnd);
			for (LocalDate ld : daysHappening_weekly) {
				makeRepeat(ld);
			}

		} else if (freq.equals(FREQUENCY.MONTHLY.toString())) {
			List<LocalDate> daysHappening_monthly = repeatStartDates_monthly(
					start, end);
			for (LocalDate ld : daysHappening_monthly) {
				makeRepeat(ld);
			}
		} else if (freq.equals(FREQUENCY.ANNUALLY.toString())) {
			List<LocalDate> daysHappening_yearly = repeatStartDates_yearly(
					start, end);
			for (LocalDate ld : daysHappening_yearly) {
				makeRepeat(ld);
			}
		} else if (freq.equals(FREQUENCY.ONCE.toString())) {
			makeRepeat(s);
		} else {
			// THROW ERROR OR STRING
		}
		String x = ezCMessages.getInstance().getStringOfTasks(repeatedTasks);
		return x;
	}
}
