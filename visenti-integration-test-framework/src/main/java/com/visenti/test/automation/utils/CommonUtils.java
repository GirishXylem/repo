package com.visenti.test.automation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.visenti.test.automation.helpers.Log;

/**
 * Class Description: This class holds all the Java Common Util methods
 */
public class CommonUtils {

	private CommonUtils() {
		throw new IllegalStateException("Common Utils class");
	}

	public static String returnProperties(String filePath, String keyName) throws IOException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream(filePath);
		prop.load(input);
		return prop.getProperty(keyName);
	}

	/**
	 * @return Getting the current system time in milliseconds
	 */
	public static long getCurrentTimeInMilliseconds() {
		long currentTimeMilliSeconds = System.currentTimeMillis();
		return currentTimeMilliSeconds;
	}

	/**
	 * Waits for specified number of seconds
	 */
	public static void wait(int seconds){
		long milliSeconds = seconds * 1000;
		try{
			Thread.sleep(milliSeconds);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param timeInMilliseconds
	 * @param dateFormat
	 * @return Convert time in milliseconds to a given date format
	 */
	public static String convertTimeInMillisecondsToADateFormat(long timeInMilliseconds, String dateFormat) {
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMilliseconds);
		String dateInFormat = sdf.format(cal.getTime());
		return dateInFormat;
	}

	public static String convertTimeInMillisecondsToUTCDateFormat(long timeInMilliseconds, String dateFormat) {
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(utcTimeZone);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMilliseconds);
		String dateInFormat = sdf.format(cal.getTime());
		return dateInFormat;
	}

	/**
	 * @param date
	 * @param dateFormat
	 * @return Getting time in milliseconds of a given date and given format
	 */
	public static long gettingMillisecondsOfAgivenDate(String date, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		System.out.println("Date string " + date);
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date d;
		try {
			d = sdf.parse(date);
			System.out.println(d);
			cal.setTime(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cal.getTimeInMillis();
	}

	/**
	 * @param startTimeInMilliseconds
	 * @param endTimeInMilliseconds
	 * @return
	 * 
	 * 		Getting the difference in months when Start time and end time
	 *         provided in milliseconds
	 * 
	 */
	public static int getDifferenceinMonthsBetweenStartTimeAndEndTime(long startTimeInMilliseconds,
			long endTimeInMilliseconds) {

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startTimeInMilliseconds);
		int startMonth = cal.get(Calendar.MONTH);
		cal.setTimeInMillis(endTimeInMilliseconds);
		int endMonth = cal.get(Calendar.MONTH);
		int monthDiff = endMonth - startMonth;
		return monthDiff;
	}

	public static int getDifferenceInMonthsBetweenStartDateAndEndDate(String startDate, String endDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();

		Date d;
		int startMonth = 0;
		int endMonth = 0;
		try {
			d = sdf.parse(startDate);

			cal.setTime(d);
			startMonth = cal.get(Calendar.MONTH);
			d = sdf.parse(endDate);
			cal.setTime(d);
			endMonth = cal.get(Calendar.MONTH);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endMonth - startMonth;
	}

	/**
	 * @param calendarField
	 * @param timeInMilliseconds
	 * @return Getting the Calendar field value when time in Milliseconds is
	 *         provided
	 */
	public static String getCalendarField(String calendarField, long timeInMilliseconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMilliseconds);
		switch (calendarField.toLowerCase()) {
		case "year":
			System.out.println(timeInMilliseconds);
			int year = cal.get(Calendar.YEAR);
			System.out.println("Year" + year);
			return String.valueOf(year);

		case "monthdate":
			System.out.println(timeInMilliseconds);
			int date = cal.get(Calendar.DAY_OF_MONTH);

			System.out.println(date);
			return String.valueOf(date);

		case "hour":
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			if (hour >= 1) {
				return String.valueOf(hour);
			} else {
				return "0" + String.valueOf(hour);
			}
		case "minute":
			int minute = cal.get(Calendar.MINUTE);
			if (minute >= 10) {
				return String.valueOf(minute);
			} else {
				return "0" + String.valueOf(minute);
			}

		default:
			throw new RuntimeException("Wrong Calendar field entered");
		}
	}

	public static String getCalendarField(String calendarFieldType, String date, String format) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(d);
		switch (calendarFieldType.toLowerCase()) {
		case "year":
			int year = cal.get(Calendar.YEAR);
			return String.valueOf(year);

		case "monthdate":
			int dateValue = cal.get(Calendar.DAY_OF_MONTH);
			return String.valueOf(dateValue);

		case "month":
			int monthValue = cal.get(Calendar.MONTH) + 1;
			return String.valueOf(monthValue);
		case "hour":
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			if (hour >= 1) {
				return String.valueOf(hour);
			} else {
				return "0" + String.valueOf(hour);
			}
		case "minute":
			int minute = cal.get(Calendar.MINUTE);
			if (minute >= 10) {
				return String.valueOf(minute);
			} else {
				System.out.println("minute " + minute);
				return "0" + String.valueOf(minute);
			}

		default:
			throw new RuntimeException("Wrong Calendar field passed");
		}
	}

	/**
	 * @param date
	 * @param dateFormat
	 * @param minValue
	 * @return
	 * 
	 * 		This method returns the Date after rounding time down to nearest Nth
	 *         minute If we want to round a date to the nearest 5 min Input date is
	 *         13/7/2019 22:58 , returns 13/7/2019 22:55 by rounding it down to
	 *         nearest 5 min
	 */
	public static String returningDateAfterRoundingTimeDownToNearestNMin(String date, String dateFormat,
			String minValue) {
		long minuteValueInMilliseconds = Long.parseLong(minValue) * 60 * 1000;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(d);
		long milliseconds = cal.getTimeInMillis();

		long millisecondsAfterRoundingToNthMinute = (milliseconds / minuteValueInMilliseconds)
				* minuteValueInMilliseconds;
		cal.setTimeInMillis(millisecondsAfterRoundingToNthMinute);
		String dateInFormat = sdf.format(cal.getTime());
		return dateInFormat;
	}

	/**
	 * @param milliseconds
	 * @param minValue
	 * @return Returning milliseconds to nearest Nth minute
	 */
	public static long returningMillisecondsAfterRoundingDownToNearestNMin(long milliseconds, String minValue) {
		long minuteValueInMilliseconds = Long.parseLong(minValue) * 60 * 1000;

		long millisecondsAfterRoundingToNthMinute = (milliseconds / minuteValueInMilliseconds)
				* minuteValueInMilliseconds;

		return millisecondsAfterRoundingToNthMinute;
	}

	/**
	 * @param decimal
	 * @return Removing Trailing Zeroes from Decimal
	 */
	public static String removingTrailingZeroesFromADecimal(String decimal) {
		BigDecimal bigDecimal = new BigDecimal(decimal);
		return bigDecimal.stripTrailingZeros().toPlainString();
	}

	public static String getSystemDateInAGivenFormat(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static String getSystemLocalDateTimeInAGivenFormat(String pattern, LocalDateTime date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
		return date.format(format);
	}
	public static String removeAllWhiteSpaces(String inputString) {
		String replacedString = inputString.replaceAll("\\s", "");
		return replacedString;
	}
	
	public static String splitInto2PartsAndReturnSecondPart(String inputString,String regex)
	{
		String replacedString=inputString.split(regex, 2)[1];
		return replacedString;
	}
	
		
	/**
	 * @param source
	 * @param wordToBeReplaced
	 * @return
	 * 
	 * This method replaces all occurences of a word in a Source String with empty space ,irrespective
	 * of the case of the Word
	 * 
	 * Example:  String source="This is my LIFE"
	 * 			 String wordToBeReplaced="life"
	 * 
	 *  		It will return "This is my " the word 'life' got replaced with Empty String though the case didnt
	 *  		match	
	 */
	
	public static String replaceAllOccurencesOfAWordWithEmptyStringRegardlessOfCase(String source,String wordToBeReplaced)
	{
		//(?i) in Regex starts case-insensitive mode 
		 String afterReplace=source.replaceAll("(?i)"+Pattern.quote(wordToBeReplaced), "");
		 return afterReplace;
	}
	
	public static String generateStringFromFileAndRemoveAllWhiteSpaces(String filePath) throws IOException {
		File file = new File(filePath);
		String generatedString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		generatedString = removeAllWhiteSpaces(generatedString);
		return generatedString;
	}
	
	public static String generateStringFromAFile(String filePath) throws IOException
	{
		File file = new File(filePath);
		String generatedString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		return generatedString;
	}

	public static void deleteAFileTypeFromADirectory(String directory, String fileExtension) {
		File folder = new File(directory);
		if (folder.isDirectory()) {

			if (folder.list().length > 0) {

				Log.debug("Directory is not empty");
				for (File f : folder.listFiles()) {
					if (f.getName().endsWith(fileExtension)) {
						boolean isFileDeleted = f.delete();
						Log.info(f.getName() + " is deleted - "+isFileDeleted);
					}
				}
			} else {
				Log.debug("Directory is empty!");
			}

		} else {
			Log.debug("This is not a directory");
		}
	}

}
