package globalClasses;

import java.time.*;
import java.util.*;
public class Date {
	private int day, month, year;
	private Calendar cal = GregorianCalendar.getInstance();;
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