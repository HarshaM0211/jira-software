package com.mandark.jira.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Dates {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dates.class);


    // Constructors
    // ------------------------------------------------------------------------

    private Dates() {
        super();
        // Private Constructor
    }


    // Parse Utilities
    // ------------------------------------------------------------------------

    /**
     * Parses the dates string to {@link Date} as per the passed format String.
     * 
     * @param dateStr actual date in string form
     * @param dateFormatStr date format
     * 
     * @return {@link Date} object
     */
    public static Date parse(final String dateStr, final String dateFormatStr) {
        // Sanity checks
        if (Objects.isNull(dateFormatStr) || dateFormatStr.isBlank()) {
            throw new IllegalArgumentException("Dates::parse, Date format String is BLANK");
        }

        if (Objects.isNull(dateStr) || dateStr.isBlank()) {
            return null;
        }

        try {
            return new SimpleDateFormat(dateFormatStr).parse(dateStr);
        } catch (ParseException e) {
            String errMsg = String.format("Failed to parse the date string : %s to date", dateStr);
            LOGGER.error(errMsg, e);
        }

        return null;
    }


    // Conversion
    // ------------------------------------------------------------------------

    public static String toDateString(final Date date, final String dateFormatStr) {
        // Sanity checks
        if (Objects.isNull(date)) {
            return null;
        }

        return new SimpleDateFormat(dateFormatStr).format(date);
    }

    public static long toTimestamp(final String dateStr, final String dateFormatStr) {
        // Sanity checks
        if (Objects.isNull(dateStr) || dateStr.isBlank()) {
            throw new IllegalArgumentException("Dates::toLong, Date string is BLANK");
        }

        final Date date = Dates.parse(dateStr, dateFormatStr);
        return date.getTime();
    }


    // Date Manipulation
    // ------------------------------------------------------------------------

    /**
     * Add a given no of minutes to the passed timestamp.
     * 
     * @param date
     * @param mins
     * 
     * @return
     */
    public static Date addMinutesToTimestamp(final Date date, final int mins) {
        // Sanity checks
        if (Objects.isNull(date)) {
            throw new IllegalArgumentException("Dates::addMinutesToTimestamp :: timestamp is NULL");
        }

        final long inMillis = date.getTime();
        final long newMillis = inMillis + ((long) mins * 60000);

        final Date newDate = new Date(newMillis);
        return newDate;
    }


    /**
     * Add a given no of hours to the passed timestamp.
     * 
     * @param date
     * @param hours
     * 
     * @return
     */
    public static Date addHoursToTimestamp(Date date, int hours) {
        return addMinutesToTimestamp(date, hours * 60);
    }


    /**
     * Add a given no of days to the passed timestamp.
     * 
     * @param date
     * @param hours
     * 
     * @return
     */
    public static Date addDaysToTimestamp(Date date, Integer days) {
        return addHoursToTimestamp(date, days * 24);
    }

    /**
     * Add a given no of days to the current timestamp.
     * 
     * @param date
     * @param hours
     * 
     * @return
     */
    public static Date addDaysToToday(int days) {
        return addDaysToTimestamp(new Date(), days);
    }

    /**
     * Add a given no of hours to the current timestamp.
     * 
     * @param date
     * @param hours
     * 
     * @return
     */
    public static Date addHoursToNow(int hours) {
        return addHoursToTimestamp(new Date(), hours);
    }

    /**
     * Add a given no of minutes to the current timestamp.
     * 
     * @param date
     * @param hours
     * 
     * @return
     */
    public static Date addMinutesToNow(int minutes) {
        return addMinutesToTimestamp(new Date(), minutes);
    }

    /**
     * Get only date of the time stamp by setting the hours, minutes and seconds of the date to zero.
     * 
     * @param inDate
     * 
     * @return
     */
    public static Date getDateOnly(Date inDate) {
        // Calendar
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final Date dateOnly = calendar.getTime();
        return dateOnly;
    }

    /**
     * Adds days to input timestamp
     * 
     * @param timestamp input timestamp in milliseconds
     * @param numberOfDays No. of Days to be added to the input timestamp
     * 
     * @return the updated timestamp in milliseconds
     */
    public static Long addDaysToTimestamp(final long timestamp, final int numberOfDays) {
        return timestamp + TimeUnit.DAYS.toMillis(numberOfDays);
    }


    // Difference
    // ------------------------------------------------------------------------

    /**
     * Adds days to the given Date
     * 
     * @param date
     * @param days
     * 
     * @return
     */
    public static Date addDaysToDate(final Date date, final int days) {
        // Sanity checks
        if (Objects.isNull(date)) {
            return null;
        }

        // Calendar
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days); // add days to current day

        return cal.getTime();
    }


    /**
     * Get difference in Hours between given timestamps.
     * 
     * @param fromTimeStamp
     * @param toTimeStamp
     * 
     * @return no. of hours between given timestamps.
     */
    public static Integer getDiffernceInHours(final long fromTimeStamp, final long toTimeStamp) {
        final Long differnce = fromTimeStamp - toTimeStamp;
        final float hours = (float) (differnce / 3600000.0);
        return (int) Math.floor(hours);
    }


}
