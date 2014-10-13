package globalClasses;

import globalClasses.Date;

import java.util.*;
public class Task {
	private Date startdate, enddate;
	private boolean hasNoDeadline = true;
	private boolean isComplete = false;
	private boolean hasNote = false;
	private boolean hasLocation = false;
	private String name, location, note, category;
	private static ArrayList<String> categorylist = new ArrayList<String>();
	public Task(String name, String first_category, String location, String note, Date startTime, Date endTime) {
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.setEndDate(endTime);
		if(!hasNoDeadline && hasNote && hasLocation && hasNote)
			isComplete = true;
		this.startdate = startTime;
	}
	//no loc or note
	public Task(String name, String category, Date startTime, Date endTime){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setEndDate(endTime);
		this.startdate = startTime;
	}
	//no end time, loc or note
	public Task(String name, String category, Date startTime){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.startdate = startTime;
		enddate = new Date(0,0,0);
	}
	//no extra details at all
	public Task(String name, String category){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.startdate = new Date();
		enddate = new Date(0,0,0);
	}
	
	public Task(String name, String first_category, String location){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.startdate = new Date();
		enddate = new Date(0,0,0);
	}
	
	//no start or end time but has loc and note
	public Task(String name, String first_category, String location, String note){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.startdate = new Date();
		enddate = new Date(0,0,0);
	}
	//No end time
	public Task(String name, String first_category, String location, String note, Date startdate){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(first_category);
		this.setCategory(first_category);
		this.setLocation(location);
		this.setNote(note);
		this.startdate = startdate;
		enddate = new Date(0,0,0);
	}
	
	public Task(String name, String category, String location, Date startTime, Date endTime){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setEndDate(endTime);
		this.setLocation(location);
		this.startdate = startTime;
	}
	
	public Task(String name, String category, String location, Date startTime){
		this.setName(name);
		categorylist = new ArrayList<String>();
		categorylist.add(category);
		this.setCategory(category);
		this.setLocation(location);
		this.startdate = startTime;
		enddate = new Date(0,0,0);
	}
	
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
	public void setEndDate(Date enddate) {
		this.enddate = enddate;
		hasNoDeadline = false;
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
	
	public String toString(){
		String answer = new String();
		answer = answer + "Task: " + this.name + '\n';
		answer = answer + "Category: " + this.category + '\n';
		if(hasNoDeadline){
		answer = answer + "Start: " + this.startdate.toString() + '\n' + "End: " + "No Specified End Date" + '\n';
		}
		if(!hasNoDeadline){
			answer = answer + "Start: " + this.startdate.toString() + '\n' + "End: " + this.enddate.toString() + '\n';
		}
		if(hasLocation){
			answer = answer + "Location: " + this.location + '\n';
		}
		if(!hasLocation){
			answer = answer + "Location: No Specified Location" + '\n';
		}
		if(hasNote){
			answer = answer + "Note: " + this.note + '\n';
		}
		if(!hasNote){
			answer = answer + "Note: No Specified Note" + '\n';
		}
		return answer;
	}
	
	public boolean isCompleted(){
		Calendar cal = GregorianCalendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Date today = new Date(day, month, year);
		if(enddate.isBefore(today))
			isComplete = true;
		return isComplete;
	}
	
	public boolean equals(Task a){
		return this.name.toLowerCase().equals(a.getName().toLowerCase());
	}
	
	public Date getStartDate() {
		return startdate;
	}
	
	public void setComplete(){
		this.isComplete = true;
	}
}
