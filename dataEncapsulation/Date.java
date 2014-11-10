/**
 * @author Kadayam Suresh Kaushik A0108297X
 * Date Class (Instant) containing Constructor and all methods.
 * @return format: Depends on which method is being used.
 */
package dataEncapsulation;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date {
	private int day, month, year;
	private Calendar cal = GregorianCalendar.getInstance();

	private static boolean dmFormat = true;

	public Date(int userday, int usermonth, int useryear) throws Exception {
		int correctedYear = correctYear(useryear);
		cal.setLenient(false);
		if (dateValid(userday, usermonth, correctedYear)) {
			this.setDay(userday);
			this.setMonth(usermonth);
			this.setYear(correctedYear);
			cal.set(correctedYear, usermonth, userday);
		} else { throw new Exception("Invalid Date"); }
	}

	public Date() {
		cal = Calendar.getInstance();
		cal.setLenient(false);
		this.day = cal.get(Calendar.DATE);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.year = cal.get(Calendar.YEAR);
		getClass();
		cal.set(year, month, day);
	}

	//@author A0126720N
	public boolean dateValid(int userday, int usermonth, int useryear) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
	    sdf.setLenient(false);
	    Calendar cal = Calendar.getInstance();  
	    try{
	    	cal.setTime(sdf.parse(usermonth + "/" + userday + "/" + useryear));
	    } catch(Exception e) {
	    	return false;
	    }
		
		boolean dateIsValid = true;
		try {
			if (useryear < 2014 || useryear > 9999) {
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

	/**
	 * @author A0115696W
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
	
	//@author A0115696W
	/**
	 * Reads a String and outputs the correct Date object.
	 * Accepted formats:
	 * 		November 5, 2014
	 * 		5/11/2014
	 * 		2014-11-5
	 * Yui Wei
	 */
	public Date determineDate(String dateString) throws Exception {
		Date tdy = new Date();
		try {
			if (dateString == null) {
				return null;
			} 
			switch (dateString.toLowerCase()) {
			case "today" :
				return tdy;
			case "tomorrow" : case "tmr" :
				int day1 = tdy.getDay();
				tdy.setDay(day1 + 1);
				return tdy;
			case "monday": case "mon" : 
				Date mon = getNext(DayOfWeek.MONDAY);
				return mon;
			case "tuesday" : case "tues" : case "tue" :
				Date tue = getNext(DayOfWeek.TUESDAY);
				return tue;
			case "wednesday" : case "wed":
				Date wed = getNext(DayOfWeek.WEDNESDAY);
				return wed;
			case "thursday" : case "thur" : case "thu" :
				Date thu = getNext(DayOfWeek.THURSDAY);
				return thu;
			case "friday": case "fri" :
				Date fri = getNext(DayOfWeek.FRIDAY);
				return fri;
			case "saturday" : case "sat" :
				Date sat = getNext(DayOfWeek.SATURDAY);
				return sat;
			case "sunday" : case "sun" :
				Date sun = getNext(DayOfWeek.SUNDAY);
				return sun;
			default: 
				break;
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
			throw new BadSubcommandArgException("You are entering an invalid date. Please enter dates from the year 2014 onwards.");
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
		/*
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
		}*/
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
		return this.getDayOfWeek() + ", " + answer + " " + this.getDay() + ", " + this.getYear();
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
	
	//@author A0115696W
	/**
	 * @author yuiwei / A0115696W
	 * @param inDateFormat
	 * @return
	 * @throws Exception
	 */
	private String ldParse(Date d) throws Exception {
		int ddd = d.getDay();
		int mmm = d.getMonth();
		int yyyy = d.getYear();
		String dd = String.format("%02d", ddd);
		String mm = String.format("%02d", mmm);
		String inLocalDateFormat = "" + yyyy + "-" + mm + "-" + dd;
		return inLocalDateFormat;
	}
	/**
	 * @author yuiwei/ A0115696W
	 * @param dw - the Java DayOfWeek object
	 * @return the Date object referring to the nearest specified DayOfWeek.
	 * i.e. getNext(DayOfWeek.MONDAY) will get the nearest Monday.
	 * @throws Exception
	 */
	private Date getNext(DayOfWeek dw) throws Exception {
		Date tdy = new Date();
		LocalDate ld = LocalDate.parse(ldParse(tdy));
		int toda = ld.getDayOfWeek().getValue();
		int nowTo = dw.getValue() - toda;
		if (nowTo < 0) {
			nowTo = nowTo + 7;
		}
		day = tdy.getDay();
		tdy.setDay(day + nowTo);
		return tdy;
	}
	/**
	 * @author Yui Wei A011596W
	 * @return
	 * @throws Exception
	 */
	private String getDayOfWeek()  {
		LocalDate date;
		try {
			date = LocalDate.parse(ldParse(this));
			String result = date.getDayOfWeek().toString().toLowerCase();
			return result.substring(0, 1).toUpperCase() + result.substring(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "There was a problem with your date.";
		}
		
	}
}