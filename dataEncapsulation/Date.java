/**
 * @author Kadayam Suresh Kaushik A0108297X
 * Date Class (Instant) containing Constructor and all methods.
 * @return format: Depends on which method is being used.
 */
package dataEncapsulation;

import java.time.DateTimeException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date {
	private int day, month, year;
	private Calendar cal = GregorianCalendar.getInstance();

	private static boolean dmFormat = true;

	public Date(int userday, int usermonth, int useryear) throws Exception {
		int correctedYear = correctYear(useryear);
		if (dateValid(userday, usermonth, correctedYear)) {
			this.setDay(userday);
			this.setMonth(usermonth);
			this.setYear(correctedYear);
			cal.set(correctedYear, usermonth, userday);
		} else { throw new Exception("Invalid Date."); }
	}

	public Date() {
		cal = Calendar.getInstance();
		this.day = cal.get(Calendar.DATE);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.year = cal.get(Calendar.YEAR);
		getClass();
		cal.set(year, month, day);
	}

	//@author A0126720N
	public boolean dateValid(int userday, int usermonth, int useryear) {
		boolean dateIsValid = true;
		try {
			if (useryear < 2014) {
				dateIsValid = false;
			} else if (useryear > 9999) {
				dateIsValid = false;
			}
		} catch (DateTimeException e) {
			dateIsValid = false;
		}
		return dateIsValid;
	}

	//@author A0126720N
	public boolean isBefore(Date other) {
		boolean answer = false;
		if (other.year > year) {
			answer = true;
		} else if (other.year == year) {
			if (other.month > month) {
				answer = true;
			} else if (other.month == month) {
				if (other.day > day) {
					answer = true;
				}
			}
		}
		return answer;
	}

	public boolean isEqual(Date anotherdate) {
		if (anotherdate.getDay() == day && anotherdate.getMonth() == month
				&& anotherdate.getYear() == year) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author yuiwei
	 * @param another
	 * @return
	 */
	public boolean isEquals(Date another) {
		if (this.day == another.day && this.month == another.month
				&& this.year == another.year) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getFormat() {
		if (dmFormat) {
			return "d/m";
		} else {
			return "m/d";
		}
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

	public Calendar getCal() {
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

	/*
	 * Reads a String and outputs the correct Date object.
	 * Accepted formats:
	 * 		November 5, 2014
	 * 		5/11/2014
	 * 		2014-11-5
	 * @author Yui Wei / A0115696W
	 */
	public Date determineDate(String dateString) throws Exception {
		try {
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
			} else if (dateString.contains("-")) {
				int yearIndex = 0;
				int dayIndex = 2;
				int monthIndex = 1;
	
				String dateStr[] = dateString.split("-", 3);
				day = dateStr[dayIndex];
				month = dateStr[monthIndex];
				year = dateStr[yearIndex];
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
		} catch (Exception e) {
			throw new BadSubcommandArgException("invalid date");
		}
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
		case "january":
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

	public String toString() {
		String answer = new String();
		switch (this.getMonth()) {
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

	public String toPrint() {
		String answer = new String();
		String day = new String();
		switch (cal.get(Calendar.DAY_OF_WEEK_IN_MONTH)) {
		case Calendar.MONDAY:
			day = "Monday";
			break;
		case Calendar.TUESDAY:
			day = "Tuesday";
			break;
		case Calendar.WEDNESDAY:
			day = "Wednesday";
			break;
		case Calendar.THURSDAY:
			day = "Thursday";
			break;
		case Calendar.FRIDAY:
			day = "Friday";
			break;
		case Calendar.SATURDAY:
			day = "Saturday";
			break;
		case Calendar.SUNDAY:
			day = "Sunday";
			break;
		}
		switch (this.getMonth()) {
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
		return day + " " + answer + " " + this.getDay() + ", " + this.getYear();
	}
	
	//@author A0126720N
	/**
	 * corrects a 2-digit year to be a 4-digit year. Assumes that the 
	 * lifetime of this product will not extend past the 21st century
	 * @param useryear
	 * @return
	 */
	private int correctYear(int year) {
		if (year > 0 && year < 100) {
			return 2000 + year;
		}
		
		return year;
	}
}