//@ Kaushik A0108297X
package dataEncapsulation;

import java.time.*;
import java.util.*;
public class Date {
	private int day, month, year;
	private Calendar cal = GregorianCalendar.getInstance();
	
	private static boolean dmFormat = true;
	
	public Date(int userday, int usermonth, int useryear) {
		if(dateValid(userday, usermonth, useryear)){
			this.setDay(userday);
			this.setMonth(usermonth);
			this.setYear(useryear);
			cal.set(useryear, usermonth, userday);
		}
	}

	public Date(){
		cal = Calendar.getInstance();
		this.day = cal.get(Calendar.DATE);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.year = cal.get(Calendar.YEAR);
		cal.set(year, month, day);
	}

	public boolean dateValid(int userday, int usermonth, int useryear){
		boolean dateIsValid = true;
		try {
			LocalDate.of(useryear, usermonth, userday);
		} catch (DateTimeException e) {
			dateIsValid = false;
		}
		return dateIsValid;
	}

	public boolean isBefore(Date anotherdate){
		boolean answer = false;
		if(anotherdate instanceof Date && anotherdate.getDay()!=0){ //avoids the case whereby the enddate has not been stated by the user
			Calendar calcompare = anotherdate.getCal();
			if(cal.before(calcompare)){
				return true;
			}
		}
		return answer;
	}

	public boolean isEqual(Date anotherdate){
		boolean answer = false;
		if(anotherdate instanceof Date && anotherdate.getDay()!=0){ //avoids the case whereby the enddate has not been stated by the user
			Calendar calcompare = anotherdate.getCal();
			if(cal.equals(calcompare)){
				return true;
			}
		}
		return answer;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Calendar getCal(){
		return this.cal;
	}
	
	public static void changeFormatDm() {
		dmFormat = true;
	}
	
	public static void changeFormatMd() {
		dmFormat = false;
	}
	
	public static boolean isFormatDm() {
		return dmFormat;
	}

	public static Date determineDate(String dateString) {
		if (dateString == null) {
			return null;
		}

		String month, day, year;
		int mm, dd, yyyy;
		if (dateString.contains(",")) {
			int space = dateString.indexOf(' ');
			int comma = dateString.indexOf(',');
			month = dateString.substring(0, space);
			day = dateString.substring(space + 1, comma);
			year = dateString.substring(comma);
			year = cleanUpYear(year);
			mm = monthInteger(month);
		} else if (dateString.contains("/")) {
			int dayIndex = 0;
			int monthIndex = 1;
			if (!dmFormat) {
				dayIndex = 1;
				monthIndex = 0;
			}
			
			String dateStr[] = dateString.split("/", 3);
			day = dateStr[dayIndex];
			month = dateStr[monthIndex];
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

	private static String cleanUpYear(String year) {
		String space = " ";
		String comma = ",";
		String emptyString = new String();

		if (year.contains(space)) {
			year = year.replace(space, emptyString);
		}

		if (year.contains(comma)) {
			year = year.replace(comma, emptyString);
		}

		return year;
	}

	public static int monthInteger(String month1) {
		int monthNum;
		switch (month1.toLowerCase()) {
		case "january" :		
			monthNum = 1; 			
			break;
		case "february":		
			monthNum = 2;				
			break;
		case "march":			
			monthNum = 3;				
			break;
		case "april":			
			monthNum = 4;				
			break;
		case "may":				
			monthNum = 5;				
			break;
		case "june":			
			monthNum = 6;				
			break;
		case "july":			
			monthNum = 7;				
			break;
		case "august":			
			monthNum = 8;				
			break;
		case "september":		
			monthNum = 9;				
			break;
		case "october":			
			monthNum = 10;				
			break;
		case "november":		
			monthNum = 11;				
			break;
		case "december":		
			monthNum = 12;				
			break;
		default: 				
			monthNum = 0;				
			break;
		}
		return monthNum;
	}

	public String toString(){
		String answer = new String();
		switch(this.getMonth()){
		case 1:
			answer = "January";
			break;
		case 2:
			answer = "February";
			break;
		case 3:
			answer = "March";
			break;
		case 4:
			answer = "April";
			break;
		case 5:
			answer = "May";
			break;
		case 6:
			answer = "June";
			break;
		case 7:
			answer = "July";
			break;
		case 8:
			answer = "August";
			break;
		case 9:
			answer = "September";
			break;
		case 10:
			answer = "October";
			break;
		case 11:
			answer = "November";
			break;
		case 12:
			answer = "December";
			break;
		}
		return answer + " " + this.getDay() + ", " + this.getYear();
	}
}
