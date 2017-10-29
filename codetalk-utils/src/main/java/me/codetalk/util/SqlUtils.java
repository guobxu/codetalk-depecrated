package me.codetalk.util;

import java.util.Calendar;

/**
 * 
 * @author guobxu
 *
 */
public final class SqlUtils {

	public static Long weekStartOfNow() {
		Calendar calendar = getCalendarForNow();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTimeInMillis();
	}
	
	public static Long weekEndOfNow() {
		Calendar calendar = getCalendarForNow();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTimeInMillis();
	}
	
	public static Long nextWeekStartOfNow() {
		Calendar calendar = getCalendarForNow();
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    return calendar.getTimeInMillis();
	}
	
	public static Long nextWeekEndOfNow() {
		Calendar calendar = getCalendarForNow();
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    return calendar.getTimeInMillis();
	}
	
	public static Long monthStartOfNow() {
		Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    return calendar.getTimeInMillis();
	}

	public static Long monthEndOfNow() {
		Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    return calendar.getTimeInMillis();
	}
	
	public static Long nextMonthStartOfNow() {
		Calendar calendar = getCalendarForNow();
		calendar.add(Calendar.MONTH, 1);
		
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    return calendar.getTimeInMillis();
	}

	public static Long nextMonthEndOfNow() {
		Calendar calendar = getCalendarForNow();
		calendar.add(Calendar.MONTH, 1);
		
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    return calendar.getTimeInMillis();
	}
	
	private static Calendar getCalendarForNow() {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(System.currentTimeMillis());
	    
	    return calendar;
	}
	
}
