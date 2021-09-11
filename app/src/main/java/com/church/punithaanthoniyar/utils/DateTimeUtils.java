package com.church.punithaanthoniyar.utils;

import android.text.format.Time;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {

    public static final int DATE_TIME = 2;
    public static final int DATE_GLOBAL = 4;
    public static final int DATE_TIME_ID_MILLIS = 5;
    public static final int DATE_TIME_NEW = 6;
    public static final int GMT_DATE_TIME = 7;
    public static final int DATE_GLOBAL_PLAIN = 8;
    public static final int DATE_GLOBAL_HYPHEN = 9;
    public static final int DATE_DOB_FORMAT_PLAIN = 10;
    public static final int SERVER_DEFINED_FORMAT = 13;
    public static final int DATE_GLOBAL_YESTERDAY = 14; // It's used to Previous day Interval
    public static final int TIME = 0;
    public static final int DATE = 1;
    public static final int TIME_HOUR_MINS = 11;
    public static final int DATE_TIME_BY_YEAR = 12;
    public static final String defaultDateFormat = "MM/dd/yyyy";
    private static final String serverDateFormat = "yyyy/MM/dd";
    public static int DATE_TIME_ID = 3;
    private static final String FORMAT = "%02d:%02d:%02d";

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    @StringDef({DateFormats.SERVER_DATE_FORMAT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DateFormats {
        String SERVER_DATE_FORMAT = "yyyy/MM/dd";
    }

    private DateTimeUtils() {

    }


    /**
     * Return current Day. For Eg : Monday
     *
     * @return
     */
    public static String today() {
        Calendar cal = Calendar.getInstance();
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "Monday";
        }

    }

    /**
     * Return date as String in a given Format.
     *
     * @param dateFormat
     * @return
     */
    public static String now(int dateFormat) {
        Calendar cal = Calendar.getInstance();

        if (TIME == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (DATE == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (DATE_TIME == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (DATE_TIME_NEW == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (DATE_GLOBAL == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (DATE_GLOBAL_YESTERDAY ==  dateFormat){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            cal.add(Calendar.DATE,-1);
            return sdf.format(cal.getTime());
        } else if (DATE_TIME_ID_MILLIS == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmssSSS", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (GMT_DATE_TIME == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.format(cal.getTime());
        } else if (DATE_GLOBAL_PLAIN == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (DATE_GLOBAL_HYPHEN == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (DATE_DOB_FORMAT_PLAIN == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (TIME_HOUR_MINS == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } else if (DATE_TIME_BY_YEAR == dateFormat) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmss", Locale.ENGLISH);
            return sdf.format(cal.getTime());
        }

    }

    /**
     *
     * @param count given count value added with current date
     *              + positive value : get future date
     *              - negative value : get previous date (YEAR,MONTH,DATE)
     * @param getType DATE - 5 , MONTH - 2 , YEAR - 1 //Constant value it should be fixed
     * @return
     */
    public static String getRequestedDateByGetType(int count, int getType) {
        Calendar cal = Calendar.getInstance();

        switch (getType) {

            case Calendar.DATE:
                cal.add(Calendar.DATE, count);
                break;
            case Calendar.MONTH:
                cal.add(Calendar.MONTH, count);
                break;
            case Calendar.YEAR:
                cal.add(Calendar.YEAR, count); // to get previous year add -1
                break;
        }
        // convert calendar to date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }




    /**
     * @param firstDate
     * @param secondDate
     * @return
     * @see {@link #getDateCount(String, String, String)}
     * an int < 0 if second Date is greater than the first Date, 0 if they are
     * equal, and an int > 0 if this Date is greater.
     * @deprecated
     */
    public static int compareDate(String firstDate, String secondDate,
                                  String format) {
        int result = 0;
        SimpleDateFormat sf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            if (firstDate != null && secondDate != null)
                result = sf.parse(firstDate).compareTo(sf.parse(secondDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;

    }

    //Get First Day of the month
    public static String getFirstDayOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,
                Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",
                Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }


    public static String[] getMontInterval(String date) {
        String[] interval = new String[2];
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        try {
            Date convertedDate = sdf.parse(date);
            start.setTime(convertedDate);
            start.set(Calendar.DAY_OF_MONTH, start.getActualMinimum(Calendar.DAY_OF_MONTH));
            end.setTime(convertedDate);
            end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        interval[0] = sdf.format(start.getTime());
        interval[1] = sdf.format(end.getTime());
        return interval;
    }

    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * Add days to the Date provided
     *
     * @param dateInput Given Date
     * @param noofDays  No of Days to be added
     * @return date
     */
    public static Date addDaystoDate(Date dateInput, int noofDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateInput);
        // manipulate date
        c.add(Calendar.DATE, noofDays);
        // convert calendar to date
        return c.getTime();
    }

    /**
     * getDateCount between fromDate and toDate
     *
     * @param fromDate starting date
     * @param toDate   ending date
     * @param format   which format of date
     * @return dateCount
     */
    public static int getDateCount(String fromDate, String toDate, String format) {
        Date d1, d2;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            d1 = dateFormat.parse(fromDate);
            d2 = dateFormat.parse(toDate);
            return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            return -1;
        }

    }

    /**
     * convert date object to user requested format.
     *
     * @param dateInput     date in Object
     * @param outDateFormat expected output date format
     * @return date string in requested format
     */
    public static String convertDateObjectToRequestedFormat(Date dateInput, String outDateFormat) {
        String outDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(outDateFormat,
                    Locale.getDefault());
            outDate = sdf.format(dateInput);
        } catch (Exception e) {
            SimpleDateFormat sdf = new SimpleDateFormat(defaultDateFormat,
                    Locale.getDefault());
            outDate = sdf.format(dateInput);
            Commons.printException("convertDateObjectToRequestedFormat" + e);
        }
        return outDate;
    }

    /**
     * convert date from server format(yyyy/MM/dd) to user requested format.
     * This will be used to display the date in screens.
     * <p>
     * If input outDateFormat is incorrect, then this methiod will return in MM/dd/yyyy
     *
     * @param dateInput     date in String
     * @param outDateFormat expected output date format
     * @return date String in requested format
     */
    public static String convertFromServerDateToRequestedFormat(String dateInput, String outDateFormat) {
        String outDate;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(serverDateFormat,
                    Locale.ENGLISH);
            if (outDateFormat.equals(serverDateFormat)) {
                return dateInput;
            } else {
                Date date = sdf.parse(dateInput);
                sdf = new SimpleDateFormat(outDateFormat, Locale.ENGLISH);
                outDate = sdf.format(date);
            }
        } catch (Exception e) {
            Commons.printException(e + "");
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(defaultDateFormat,Locale.ENGLISH);
                Date date = sdf.parse(dateInput);
                sdf = new SimpleDateFormat(outDateFormat, Locale.ENGLISH);
                outDate = sdf.format(date);
                return outDate;
            } catch (Exception e1) {
                Commons.printException("convertFromServerDateToRequestedFormat" + e1);
                return dateInput;
            }
        }
        return outDate;
    }

    /**
     * Convert date from userspecific format to server format (yyyy/MM/dd).
     * While saving the date in DB, this method will be useful.
     *
     * @param dateInput       date in String
     * @param dateInputFormat input date format
     * @return date string in server format
     */
    public static String convertToServerDateFormat(String dateInput,
                                                   String dateInputFormat) {
        String outDate;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(dateInputFormat,
                    Locale.ENGLISH);
            if (dateInputFormat.equals(serverDateFormat)) {
                return dateInput;
            } else {
                Date date = sdf.parse(dateInput);
                sdf = new SimpleDateFormat(serverDateFormat, Locale.ENGLISH);
                outDate = sdf.format(date);
            }
        } catch (Exception e) {
            Commons.printException(e + "");
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(defaultDateFormat,Locale.ENGLISH);
                Date date = sdf.parse(dateInput);
                sdf = new SimpleDateFormat(serverDateFormat, Locale.ENGLISH);
                outDate = sdf.format(date);
                return outDate;
            } catch (Exception e1) {
                Commons.printException("convertToServerDateFormat" + e1);
                return dateInput;
            }
        }
        return outDate;
    }

    /**
     * convert date (yyyy/MM/dd) from server format to user requested format.
     * This method is use full to apply validations.
     *
     * @param dateInput       date in String
     * @param dateInputFormat input date format
     * @return date object
     */
    public static Date convertStringToDateObject(String dateInput,
                                                 String dateInputFormat) {
        Date outDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateInputFormat,
                    Locale.ENGLISH);
            outDate = sdf.parse(dateInput);
        } catch (Exception e) {
            Commons.printException("convertStringToDateObject" + e);
            return null;
        }
        return outDate;
    }

    /**
     * @param format input date format
     * @return SimpleDateFormat
     */

    @NonNull
    public static SimpleDateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format, Locale.US);
    }

    public static String getTimeZone() {
        try {
            return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT,
                    Locale.ENGLISH);
        } catch (Exception e) {

        }
        return "UTC";
    }

    public static String convertDateTimeObjectToRequestedFormat(String inputText, String inputDateFormat, String outDateFormat) {
        String outDate = "";
        try {
            DateFormat outputFormat = new SimpleDateFormat(outDateFormat, Locale.ENGLISH);
            DateFormat inputFormat = new SimpleDateFormat(inputDateFormat, Locale.ENGLISH);
            Date date = inputFormat.parse(inputText);
            outDate = outputFormat.format(date);
        } catch (Exception e) {

            Commons.printException("convertDateObjectToRequestedFormat" + e);
        }
        return outDate;
    }

    public static boolean isFutureDate(Calendar endCalendar, Calendar startCalendar) {
        return endCalendar.after(startCalendar);
    }

    public static boolean isPastDate(Calendar endCalendar, Calendar startCalendar) {
        return endCalendar.before(startCalendar);
    }

    public static Calendar getCalendarOfDate(Date date) {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static int getHour(Date date) {
        return getCalendarOfDate(date).get(Calendar.HOUR);
    }

    public static int getHourOfDay(Date date) {
        return getCalendarOfDate(date).get(Calendar.HOUR);
    }

    public static int getHour(Date date, boolean isAmPm) {
        if (isAmPm) {
            return getHourOfDay(date);
        } else {
            return getHour(date);
        }
    }

    public static int getMinuteOf(Date date) {
        return getCalendarOfDate(date).get(Calendar.MINUTE);
    }

    public static Date todayDate() {
        return Calendar.getInstance(Locale.getDefault()).getTime();
    }

    public static int getMonth(Date date) {
        return getCalendarOfDate(date).get(Calendar.MONTH);
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    public static String convertDayName(String day, String inputFormat, String outputFormat) {
        try {
            return new SimpleDateFormat(outputFormat, Locale.US)
                    .format(new SimpleDateFormat(inputFormat, Locale.US).parse(day));
        } catch (Exception e) {
            Commons.printException(e);
            return day;
        }
    }

    public static Date getDate(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date dateVal = new SimpleDateFormat(format, Locale.US).parse(date);
            calendar.setTime(dateVal);
        } catch (Exception e) {
            Commons.printException(e);
        }

        return calendar.getTime();

    }


    /**
     * This Method Use to check whether the Time slot is between Two Time
     *
     * @param fromTime
     * @param toTime
     * @param compareTime
     * @param isFromTime  - Whether to check with From Time or To Time
     * @return
     */
    public static boolean isBetweenTime(String fromTime, String toTime, String compareTime, boolean isFromTime) {

        try {
            Date time1 = new SimpleDateFormat("HH:mm", Locale.US).parse(fromTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = new SimpleDateFormat("HH:mm", Locale.US).parse(toTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            Date d = new SimpleDateFormat("HH:mm", Locale.US).parse(compareTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);

            Date x = calendar3.getTime();
            if (isFromTime) {
                if (x.equals(calendar1.getTime())
                        || (x.after(calendar1.getTime()) && x.before(calendar2.getTime()))) {
                    return true;
                }
            } else {
                if ((x.after(calendar1.getTime()) && x.before(calendar2.getTime()))
                        || x.equals(calendar2.getTime())) {
                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * This Method Use to check whether the Date is between Two Dates
     *
     * @param fromDate - Comparing date should be equal to or greater than this date
     * @param toDate - Comparing date should be equal to or lesser than this date
     * @param compareDate - Input date to compare
     * @return true or false
     */
    public static boolean isBetweenDate(String fromDate, String toDate, String compareDate, String dateFormat) {

        try {
            Date startDate = new SimpleDateFormat(dateFormat, Locale.getDefault()).parse(fromDate);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(startDate);

            Date endDate = new SimpleDateFormat(dateFormat, Locale.getDefault()).parse(toDate);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(endDate);

            Date date = new SimpleDateFormat(dateFormat, Locale.getDefault()).parse(compareDate);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(date);

            Date x = calendar3.getTime();
            if ((x.equals(calendar1.getTime()) || (x.after(calendar1.getTime()) && x.before(calendar2.getTime()))) ||
                    ((x.after(calendar1.getTime()) && x.before(calendar2.getTime()))
                    || x.equals(calendar2.getTime())) )
                return true;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @return true if the supplied when is today else false
     */
    public static boolean isToday(long when) {
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }

    private static SimpleDateFormat getDateFormat(int format) {
        switch (format) {
            case TIME:
                return new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            case DATE:
                return new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            case DATE_TIME:
                return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
            case DATE_TIME_NEW:
                return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            case DATE_GLOBAL:
                return new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            case DATE_TIME_ID_MILLIS:
                return new SimpleDateFormat("MMddyyyyHHmmssSSS", Locale.ENGLISH);
            case GMT_DATE_TIME:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                return sdf;
            case DATE_GLOBAL_PLAIN:
                return new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            case DATE_GLOBAL_HYPHEN:
                return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            case DATE_DOB_FORMAT_PLAIN:
                return new SimpleDateFormat("MMddyyyy", Locale.ENGLISH);
            case TIME_HOUR_MINS:
                return new SimpleDateFormat("HH", Locale.ENGLISH);
            default:
                return new SimpleDateFormat("MMddyyyyHHmmss", Locale.ENGLISH);
        }
    }

    public static long getSeconds(String time1, String time2, int format) {
        try {
            SimpleDateFormat sdf = getDateFormat(format);
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            long difference = date2.getTime() - date1.getTime();
            return (difference / 1000);
        } catch (Exception e) {
            Commons.printException(e);
        }
        return 0;
    }
    /**
     *
     * @param startTime
     * @param endTime
     * @param dateFormat
     * @return Total Time Spent
     */
    public static String getTimeSpend(String startTime, String endTime, String dateFormat) {

        if (startTime == null)
            return "00:00:00";

        SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            Date date1 = sdf1.parse(startTime);
            Date date2 = sdf1.parse(endTime);

            long durationInMillis = date2.getTime() - date1.getTime();

            return String.format(Locale.getDefault(),FORMAT,
                    TimeUnit.MILLISECONDS.toHours(durationInMillis),
                    TimeUnit.MILLISECONDS.toMinutes(durationInMillis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(durationInMillis)),
                    TimeUnit.MILLISECONDS.toSeconds(durationInMillis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationInMillis)));

        } catch (ParseException e) {
            Commons.printException(e);
        }
        return "00:00:00";
    }

    public static String getTimeFromMillis(Long millis, boolean isUTC){
        if(millis !=null && millis != 0) {
            Date date = new Date(millis);
            DateFormat format = new SimpleDateFormat("hh:mm a", Locale.US);
            if(isUTC)
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
            return format.format(date);
        } else
            return "";
    }

    public static String convertMillisToHMmmSs(long millis) {

        try {
            return String.format(Locale.US,FORMAT,
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        }catch(Exception e){
            Commons.printException(e);
            return "00:00:00";
        }
    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.ENGLISH);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            Commons.printException(ex);
        }
        return date != null;
    }

    public static String getTimeAgo(String dateTime) {

        long time=0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.US);
        try {
            Date mDate = sdf.parse(dateTime);
            time = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return "";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            // return diff / DAY_MILLIS + " days ago";
            return dateTime;
        }
    }

    public static int currentDateValidation(String downloadDate) {
        return compareDate(downloadDate, now(DateTimeUtils.DATE_GLOBAL),
                "yyyy/MM/dd");
    }


    public static String getDayForReqDate(Calendar cal) {

        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return "SUN";
            case 2:
                return "MON";
            case 3:
                return "TUE";
            case 4:
                return "WED";
            case 5:
                return "THU";
            case 6:
                return "FRI";
            case 7:
                return "SAT";
            default:
                return "MON";
        }
    }

    public static String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }


        public static String dateFormat(int year, int monthOfYear, int dayOfMonth) {
        String month;
        String day;

        if (monthOfYear + 1 < 9)
            month = "0" + (monthOfYear + 1);
        else
            month = Integer.toString(monthOfYear + 1);

        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        else
            day = Integer.toString(dayOfMonth);

        return year + "/" + month + "/" + day;

    }

    public static String changeDate(String date) {

        if (null != date && !"".equals(date))
            try {
                String[] dat = date.split(" ");

                SimpleDateFormat cf = new SimpleDateFormat("yyyy/MM/dd",
                        Locale.ENGLISH);
                Date dt = cf.parse(dat[0]);
                SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy",
                        Locale.ENGLISH);
                return sf.format(dt);
            } catch (Exception e) {
                Commons.printException("" + e);
            }

        return "";
    }

}
