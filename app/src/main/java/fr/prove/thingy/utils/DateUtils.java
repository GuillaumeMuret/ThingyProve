/**
 * @file DateUtils.java
 * @brief Usefull methods for parsing / formatting date objects
 *
 * @version 1.0
 * @date 12/05/16
 * @author François LEPAROUX
 * @collab Guillaume MURET
 * @copyright
 *	Copyright (c) 2016, PRØVE
 * 	All rights reserved.
 * 	Redistribution and use in source and binary forms, with or without
 * 	modification, are permitted provided that the following conditions are met:
 *
 * 	* Redistributions of source code must retain the above copyright
 * 	  notice, this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright
 * 	  notice, this list of conditions and the following disclaimer in the
 * 	  documentation and/or other materials provided with the distribution.
 * 	* Neither the name of PRØVE, Angers nor the
 * 	  names of its contributors may be used to endorse or promote products
 *   	derived from this software without specific prior written permission.
 *
 * 	THIS SOFTWARE IS PROVIDED BY PRØVE AND CONTRIBUTORS ``AS IS'' AND ANY
 * 	EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * 	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * 	DISCLAIMED. IN NO EVENT SHALL PRØVE AND CONTRIBUTORS BE LIABLE FOR ANY
 * 	DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * 	(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * 	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * 	ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * 	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * 	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package fr.prove.thingy.utils;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.R;

public class DateUtils {

    /**
     * SQL format of the date
     */
    private final static String DATE_PARSE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * French date format
     */
    private final static String FRENCH_DATE_FORMAT = "ccc dd MMM yyyy";

    /**
     * In-case-of decode date error
     */
    private final static String DATE_ERROR_TEXT = "Date Error";

    /**
     * Converts a String in format dd_MM_yyyy into an Android Date friendly object
     * @param rawDate the date as a string
     * @return the parsed Date object
     */
    public static Date parseStringToDate (String rawDate) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PARSE_FORMAT, Locale.FRANCE);
        Date date;
        try {
            date = format.parse(rawDate);
        } catch (ParseException e) {
            if (BuildConfig.DEBUG) Log.e("DateUtils", "Error : dateFormat not supported ! Got : " + rawDate);
            date = null;
        }
        return date;
    }

    /**
     * Converts a Date object into a french style date like "Lun. 12 Jun 2016"
     * @param date the Date object to format
     * @return the formatted String into french style date
     */
    public static String parseDateToFrenchString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FRENCH_DATE_FORMAT, Locale.FRANCE);
        return format.format(date);
    }

    /**
     * Converts a Date object into a french style date, and with "Today" or "Yesterday" values if the date corresponds to.
     * Award of the longest method name ever !
     * @param context the context of the current activity, to load strings
     * @param date the Date object to formatthe formatted String into french style date
     * @return the formatted String into french style date (or "Today", "yesterday" ...)
     */
    public static String parseDateToFriendlyFrenchString(Context context, Date date) {

        // init some calendars
        Calendar today = Calendar.getInstance(Locale.FRANCE);
        Calendar yesterday = Calendar.getInstance(Locale.FRANCE);
        yesterday.add(Calendar.DAY_OF_YEAR, -1); // remove 1 day from today => yesterday !

        // Check if the date is the same as today (day of year and year)
        if (isSameDay(date, today.getTime())) {
            return context.getResources().getString(R.string.date_today);
        } else // Check if the date is the same as yesterday
        if (isSameDay(date, yesterday.getTime())) {
            return context.getResources().getString(R.string.date_yesterday);
        } else {
            // Simply returns the french string from this date object
            return parseDateToFrenchString(date);
        }
    }

    /**
     * Checks if two dates are on the same day ignoring time.
     * @param date1  the first date, not altered, not null
     * @param date2  the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * Checks if two calendars represent the same day ignoring time
     * @param cal1  the first calendar, not altered, not null
     * @param cal2  the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
}
