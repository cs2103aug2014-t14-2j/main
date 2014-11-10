package dataEncapsulation;

/**
 * 
 * @author A0115696W 
 * 
 * Expects input that is: > 24hour: 0000-2359 OR 00:00-23:59 >
 *         12hour: 12:00AM - 11:59PM OR 12am-11pm
 */

public class Time {
	private int hours; // 24 hours
	private int minutes;

	// 24hr input 0000 - 2359
	// 12hr input 12:00am - 11:59pm

	public Time(int hrs, int mins) throws Exception {
		if (!isValidTime(hrs, mins)) {
			throw new Exception("");
		}
		hours = hrs;
		minutes = mins;
	}

	public Time() {
		hours = 0;
		minutes = 0;
	}

	private boolean isValidTime(int hrs, int mins) {
		boolean hoursValid;
		if (hrs >= 0 && hrs < 24) {
			hoursValid = true;
		} else {
			hoursValid = false;
		}

		boolean minsValid;
		if (mins >= 0 && mins < 60) {
			minsValid = true;
		} else {
			minsValid = false;
		}

		return hoursValid && minsValid;
	}

	public Time determineTime(String input) throws Exception {
		if (input == null) {
			return null;
		}
		
		String h, m, ap;
		int hrs, mins;
		input = input.toLowerCase().replaceAll(" ", "");
		if (input.contains("am")
				|| input.contains("pm")) {
			// 12 hour input
			if (input.length() == 4) {
				h = input.substring(0, 2);
				ap = input.substring(2);
				m = "00";
			} else if (input.length() == 3) {
				h = input.substring(0, 1);
				m = "00";
				ap = input.substring(1);
			} else {
				String timeStr[] = input.split(":");
				h = timeStr[0];
				m = timeStr[1].substring(0, 2);
				ap = timeStr[1].substring(2);
			}
			hrs = Integer.parseInt(h);
			mins = Integer.parseInt(m);
			if (hrs > 12) { throw new Exception("INVALID TIME INPUT (12 hour pls)"); }
			if (ap.equals("pm")) {
				if (hrs != 12) {
				hrs = hrs + 12;
				}
			} else if (ap.equals("am")) {
				if (hrs == 12) {
					hrs = 0;
				}
			}
		} else {
			// assume 24 hour!
			if (input.contains(":")) {
				input = cleanUp(input);
			}
			if (input.length() == 3) {
				h = input.substring(0, 1);
				m = input.substring(1);
			} else {
				h = input.substring(0, 2);
				m = input.substring(2);
			}
			hrs = Integer.parseInt(h);
			mins = Integer.parseInt(m);
		}
		return new Time(hrs, mins);
	}

	public String toString() {
		int h = this.getHours();
		int m = this.getMins();
		String ap;
		if (h >= 12) {
			ap = "pm";
			h = h - 12;
		} else {
			ap = "am";
		}
		if (h == 0) {
			h = 12;
		}
		String hrs = String.format("%02d", h);
		String mins = String.format("%02d", m);
		return hrs + ":" + mins + ap;
	}

	public void setHours(int hrs) {
		hours = hrs;
	}

	public int getHours() {
		return hours;
	}

	public void setMins(int mins) {
		minutes = mins;
	}

	public int getMins() {
		return minutes;
	}

	private String cleanUp(String in) {
		String colon = ":";
		String emptyString = "";
		if (in.contains(colon)) {
			in = in.replace(colon, emptyString);
		}
		return in;
	}

	public int compareTo(Time another) {
		if (this.getHours() > another.getHours()) {
			return 1;
		} else if (this.getHours() == another.getHours()) {
			if (this.getMins() > another.getMins()) {
				return 1;
			} else if (this.getMins() == another.getMins()) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

}
