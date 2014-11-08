	/**
	 * @author Kadayam Suresh Kaushik A0108297X
	 * 
	 * Task Class (Instant) containing Constructor and all methods. - Uses the Date Class
	 * @return format: Depends on which method is being used.
	 * 
	 * 
	 * name, category, location, note, startdate, enddate, starttime, endtime
	 * name, emptylist, null, null, today, null, null, null
	 */
package dataEncapsulation;

import java.util.*;

import dataEncapsulation.Date;
public class Task {
	
	private final String MESSAGE_NO_END = "No Specified End Date";
	private final String MESSAGE_NO_LOCATION = "No Specified Location";
	private final String MESSAGE_NO_NOTE = "No Specified Note";
	private final String NEW_LINE = System.getProperty("line.separator");
	
	private Date startdate, enddate;
	private String name, location, note, category;
	private Time starttime, endtime;
	
	private boolean hasDeadline = false;
	private boolean isComplete = false;
	private boolean hasNote = false;
	private boolean hasLocation = false; 
	private boolean hasStartTime = false;
	private boolean hasEndTime = false;
	
	private static ArrayList<String> categorylist = new ArrayList<String>();
	
	//has every field
	public Task(String name, String first_category, String location, String note, Date start, Date end, Time s, Time e) throws Exception {
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(s);
		this.setEndTime(e);
	}
	
	public Task(String name, String first_category, String location, Date start, Date end, Time s, Time e) throws Exception {
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(s);
		this.setEndTime(e);
	}
	
	//has every field except END TIME
	public Task(String name, String first_category, String location, String note, Date start, Date end, Time s) throws Exception {
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(s);
		Time autoEndTime = new Time(s.getHours() + 1, s.getMins());
		this.setEndTime(autoEndTime);
	}
	
	public Task(String name, String first_category, String location, Date start, Date end, Time s) throws Exception {
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(s);
		Time autoEndTime = new Time(s.getHours() + 1, s.getMins());
		this.setEndTime(autoEndTime);
	}
	
	
	
	//has every field except TIME
	public Task(String name, String first_category, String location, String note, Date start, Date end) throws Exception {
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
		
	}
	
	//no loc or note or no Time
	public Task(String name, String category, Date start, Date end) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setEndDate(end);
		this.setStartDate(start);
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
	}
	//no location or note but has time
	public Task(String name, String category, Date start, Date end, Time s, Time e) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setEndDate(end);
		this.setStartDate(start);
		this.setStartTime(s);
		this.setEndTime(e);
	}
	//no loc or note but has start time
	public Task(String name, String category, Date start, Date end, Time s) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setEndDate(end);
		this.setStartDate(start);
		this.setStartTime(s);
		Time autoEndTime = new Time(s.getHours() + 1, s.getMins());
		this.setEndTime(autoEndTime);
	}
	
	public Task(String name, Date start, Date end, Time s, Time e) throws Exception {
		this.setName(name);
		this.setEndDate(end);
		this.startdate = start;
		this.setStartTime(s);
		this.setEndTime(e);
		
	}
	//no start time, loc or note | | GIVEN ONLY ONE DATE = end date.
	public Task(String name, String category, Date end) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.startdate = new Date();
		enddate = end;
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
	}
	
	public Task(String name, Date date, Time time) throws Exception {
		this.setName(name);
		this.setStartDate(date);
		this.setEndDate(date);
		this.setStartTime(time);
		Time autoEndTime = new Time(time.getHours() + 1, time.getMins());
		this.setEndTime(autoEndTime);
	}
	//no extra details at all
	public Task(String name, String category) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.startdate = new Date();
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
	}
	
	public Task(String name, String first_category, String location) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setStartDate(new Date());
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
	}
	
	//no start or end time but has loc and note
	public Task(String name, String first_category, String location, String note) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setStartDate(new Date());
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
	}
	//No end date
	public Task(String name, String first_category, String location, String note, Date startdate) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setStartDate(startdate);
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
	}
	
	public Task(String name, String category, String location, Date start, Date end) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setStartDate(start);
		this.setEndDate(end);
		this.setLocation(location);
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
	}
	
	public Task(String name, String category, String location, Date startTime) throws Exception{
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setLocation(location);
		this.startdate = startTime;
		this.setStartTime(new Time());
		this.setEndTime(new Time(23, 59));
	}
	
	///////////////////////////////////////////////////////////GETTER SETTER
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
		hasNote = true;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
		hasLocation = true;
	}
	public Date getEndDate() {
		return enddate;
	}
	
	public Date getStartDate() {
		return startdate;
	}
	
	public void setStartDate(Date start) {
		startdate = start;
	}
	
	public void setEndDate(Date end) {
		this.enddate = end;
		if(end == null) {
			hasDeadline = false;
		} else {
			hasDeadline = true;
		}
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getHasDeadline() {
		return hasDeadline;
	}
	
	public boolean getHasNote() {
		return hasNote;
	}
	
	public boolean getHasLocation() {
		return hasLocation;
	}
	
	public boolean getIsComplete() {
		return isComplete;
	}
	
	public Time getStartTime() {
		setHasStartTime(true);
		return starttime;
	}
	
	public void setStartTime(Time s) throws Exception {
		if(s == null) {
			setHasStartTime(false);
		} else { 
			setHasStartTime(true);
		}
		starttime = s;
		
		if (!hasEndTime) {
			Time autoEndTime = new Time(s.getHours() + 1, s.getMins());
			this.setEndTime(autoEndTime);
		}
	}
	
	public Time getEndTime() {
		return endtime;
	}
	
	public void setEndTime(Time e) {
		if(e == null) {
			setHasEndTime(false);
		} else {
			setHasEndTime(true);
		}
		endtime = e;
	}
	
	
	public void setComplete(){
		this.isComplete = true;
	}
	
	public void setIncomplete() {	// Using this for the undo command
		this.isComplete = false;
	}
	
	///////////////////////////////////////////////////////////TOSTRING, TOPRINT, EQUALS
	
	public String toString(){
		String answer = new String();
		answer = answer + "Task: " + this.name + NEW_LINE;
		answer = answer + "Category: " + this.category + NEW_LINE;
		if(!hasDeadline){
		answer = answer + "Start: " + this.getStartDate().toString() + " @ " +
				this.getStartTime().toString() + NEW_LINE + "End: " + 
				MESSAGE_NO_END + NEW_LINE;
		}
		if(hasDeadline){
			answer = answer + "Start: " + this.getStartDate().toString() + 
					" @ " + this.getStartTime().toString() + NEW_LINE + 
					"End: " + this.getEndDate().toString() + " @ " + 
					this.getEndTime().toString() + NEW_LINE;
		}
		if(hasLocation){
			answer = answer + "Location: " + this.getLocation() + NEW_LINE;
		}
		if(!hasLocation){
			answer = answer + "Location: " + MESSAGE_NO_LOCATION + NEW_LINE;
		}
		if(hasNote){
			answer = answer + "Note: " + this.getNote() + NEW_LINE;
		}
		if(!hasNote){
			answer = answer + "Note: " + MESSAGE_NO_NOTE + NEW_LINE;
		}
		if(isComplete) {
			answer = answer + "Completed: Yes" + NEW_LINE;
		} else {
			answer = answer + "Completed: No" + NEW_LINE;
		}
		return answer;
	}
	
	public String toPrint(){
		String answer = new String();
		answer = answer + "Task: " + this.name + NEW_LINE;
		answer = answer + "Category: " + this.category + NEW_LINE;
		if(!hasDeadline){
			answer = answer + "Start: " + this.getStartDate().toString() + 
					" @ " + this.getStartTime().toString() + NEW_LINE + 
					"End: " + MESSAGE_NO_END + NEW_LINE;
		}
		if(hasDeadline){
			answer = answer + "Start: " + this.getStartDate().toString() + 
					" @ " + this.getStartTime().toString() + NEW_LINE + "End: "
					+ this.getEndDate().toString() + " @ " + 
					this.getEndTime().toString() + NEW_LINE;
		}
		if(hasLocation){
			answer = answer + "Location: " + this.location + NEW_LINE;
		}
		if(hasNote){
			answer = answer + "Note: " + this.note + NEW_LINE;
		}
		if(isComplete) {
			answer = answer + "Completed: Yes" + NEW_LINE;
		} else {
			answer = answer + "Completed: No" + NEW_LINE;
		}
		return answer;
	}
	
	public boolean isCompleted(){
		if (isComplete == true) {
			return isComplete;
		} else if (hasDeadline != true) {
			return isComplete;
		}
		Date today = new Date();
		if(enddate.isBefore(today) && !enddate.isEqual(today))
			isComplete = true;
		return isComplete;
	}
	
	public boolean equals(Task a){
		return this.name.toLowerCase().equals(a.getName().toLowerCase());
	}

	public boolean isHasStartTime() {
		return hasStartTime;
	}

	public void setHasStartTime(boolean hasStartTime) {
		this.hasStartTime = hasStartTime;
	}

	public boolean isHasEndTime() {
		return hasEndTime;
	}

	public void setHasEndTime(boolean hasEndTime) {
		this.hasEndTime = hasEndTime;
	}
	
	/*
	 * @author Yui Wei / A0115696W
	 */
	public boolean endsBefore(Task another) {
		boolean result;
		
		Date myED = this.getEndDate();
		Time myET = this.getEndTime();
		Date anED = another.getEndDate();
		Time anET = another.getEndTime();
		
		if (myED.isBefore(anED)) { //this.date before another.date
			return true;
		} else if (myED.isEquals(anED)) { //this.date same as another.date
			if (myET.compareTo(anET) < 0) { 
				result = true;	//this.time is before another.time
			} else { result = false; }
		} else { result = false; } //this.date after another.date
		
		return result;
	}
}
