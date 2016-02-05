package com.jpmorgan.stocks.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Class containing functionality for dates.
 * 
 * @author Nilesh Mehta
 *
 */
public class DateUtility {

	/**
	 * Constructor of the class.
	 */
	public DateUtility() {

	}

	/**
	 * Accept minutes and returns the Date object New Date time = Current time +
	 * minutes
	 * 
	 * @param minutes
	 * @return Date
	 * 
	 */
	public Date getTradeTimeInMinutes(int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}
}
