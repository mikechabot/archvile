package com.archvile.utils;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class TimeUtil {

	private static PeriodFormatter formatter = new PeriodFormatterBuilder()
			.appendHours().appendSuffix("h").appendSeparator(" ")
			.appendMinutes().appendSuffix("m").appendSeparator(" ")
			.appendSeconds().appendSuffix("s").toFormatter();

	public static String getDateFromLong(long date) {
		return date == 0 ? null : new DateTime(date)
				.toString("MM/dd/yyyy h:mm a");
	}

	public static String getDurationFromLongs(long start, long stop) {
		if ((stop-start) < 2000) {
			return "< 2s";
		}
		return formatter.print(new Period(start, stop));
	}

}
