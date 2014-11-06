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
	public Task(String name, String first_category, String location, String note, Date start, Date end) {
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setEndDate(end);
		this.startdate = start;
		
	}
	
	//no loc or note or no Time
	public Task(String name, String category, Date start, Date end){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setEndDate(end);
		this.setStartDate(start);
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
	public Task(String name, String category, Date end){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.startdate = new Date();
		enddate = end;
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
	public Task(String name, String category){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.startdate = new Date();
	}
	
	public Task(String name, String first_category, String location){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setStartDate(new Date());
	}
	
	//no start or end time but has loc and note
	public Task(String name, String first_category, String location, String note){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setStartDate(new Date());
	}
	//No end date
	public Task(String name, String first_category, String location, String note, Date startdate){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setStartDate(startdate);
	}
	
	public Task(String name, String category, String location, Date start, Date end){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setStartDate(start);
		this.setEndDate(end);
		this.setLocation(location);
	}
	
	public Task(String name, String category, String location, Date startTime){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setLocation(location);
		this.startdate = startTime;
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
		answer = answer + "Task: " + this.name + '\n';
		answer = answer + "Category: " + this.category + '\n';
		if(!hasDeadline){
		answer = answer + "Start: " + this.getStartDate().toString() + '\n' + "End: " + MESSAGE_NO_END + '\n';
		}
		if(hasDeadline){
			answer = answer + "Start: " + this.getStartDate().toString() + '\n' + "End: " + this.getEndDate().toString() + '\n';
		}
		if(hasStartTime) {
			answer = answer + "From: " + this.getStartTime().toString()  + " to: " + this.getEndTime().toString() + '\n';
		}
		if(hasLocation){
			answer = answer + "Location: " + this.getLocation() + '\n';
		}
		if(!hasLocation){
			answer = answer + "Location: " + MESSAGE_NO_LOCATION + '\n';
		}
		if(hasNote){
			answer = answer + "Note: " + this.getNote() + '\n';
		}
		if(!hasNote){
			answer = answer + "Note: " + MESSAGE_NO_NOTE + '\n';
		}
		if(isComplete) {
			answer = answer + "Completed: Yes\n";
		} else {
			answer = answer + "Completed: No\n";
		}
		return answer;
	}
	
	public String toPrint(){
		String answer = new String();
		answer = answer + "Task: " + this.name + '\n';
		answer = answer + "Category: " + this.category + '\n';
		if(hasDeadline){
			answer = answer + "Start: " + this.startdate.toPrint() + '\n' + "End: " + this.enddate.toPrint() + '\n';
		}
		if(hasStartTime) {
			answer = answer + "From: " + this.getStartTime().toString() + " to: " + this.getEndTime().toString() + '\n';
		}
		if(hasLocation){
			answer = answer + "Location: " + this.location + '\n';
		}
		if(hasNote){
			answer = answer + "Note: " + this.note + '\n';
		}
		if(isComplete) {
			answer = answer + "Completed: Yes\n";
		} else {
			answer = answer + "Completed: No\n";
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
}
