package globalClasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Date;
// test for yui wei
import java.util.List;

import userInterface.UserInterface;
import userInterface.ezCMessages;
import fileIo.FileIo;
import fileIo.TaskFileReader;
import fileIo.TextIoStream;

public class ezC {
	
	public static List<Task> totalTaskList;
	public static FileIo IoStream = new FileIo();
	
	// YUI WEI
	// note from Nat: Could we put this in a separate class so other functions
	// can call it? Kinda like TaskFactory?
	public static Date determineDate(String dateString) {
		String month, day, year;
		int mm, dd, yyyy;
		if (dateString.contains(",")) {
			int space = dateString.indexOf(' ');
			int comma = dateString.indexOf(',');
			month = dateString.substring(0, space);
			day = dateString.substring(space + 1, comma);
			year = dateString.substring(comma);
			mm = monthInteger(month);
		} else if (dateString.contains("/")) {
			String dateStr[] = dateString.split("/", 3);
			day = dateStr[0];
			month = dateStr[1];
			year = dateStr[2];
			mm = Integer.parseInt(month);
		} else {
			String dateStr[] = dateString.split(" ", 3);
			day = dateStr[0];
			month = dateStr[1];
			year = dateStr[2];
			mm = Integer.parseInt(month);
		}
		dd = Integer.parseInt(day);
		yyyy = Integer.parseInt(year);
		
		Date userDate = new Date(dd, mm, yyyy);
		return userDate;
	}
	
	// this should probably be made into method in the Date class.
	// Nat: Month should also be an enum
	static int monthInteger(String month1) {
		int monthNum;
		switch (month1.toLowerCase()) {
			case "january" :		monthNum = 1; 				break;
			case "february":		monthNum = 2;				break;
			case "march":			monthNum = 3;				break;
			case "april":			monthNum = 4;				break;
			case "may":				monthNum = 5;				break;
			case "june":			monthNum = 6;				break;
			case "july":			monthNum = 7;				break;
			case "august":			monthNum = 8;				break;
			case "september":		monthNum = 9;				break;
			case "october":			monthNum = 10;				break;
			case "november":		monthNum = 11;				break;
			case "december":		monthNum = 12;				break;
			default: 				monthNum = 0;				break;
		}
		return monthNum;
	}
	
<<<<<<< HEAD
=======
	// VERNON
	public static Task extractTask(String userInput) {
		return null;
	}
	
>>>>>>> 1645f677f35786e7c0a0aed19e9dbf315505f302
	// YUI WEI
	public static List<Task> findTask(String taskName) {
		List<Task> matches = new ArrayList<Task>();
		for (int i=0; i<totalTaskList.size(); i++) {
			Task cur = totalTaskList.get(i);
			if (cur.getName().contains(taskName)) {
				matches.add(cur);
			}
		}
		if (matches.size() == 0) {
			System.out.println("None found."); //need a standard for this.
		}
		return matches;
	}
	
	public static void main(String[] args) {
		FileIo.initializeTaskList(totalTaskList);
		UserInterface.welcomeUser();
	}
}
