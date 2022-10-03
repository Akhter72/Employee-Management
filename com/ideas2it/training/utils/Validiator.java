package com.ideas2it.training.utils;

import com.ideas2it.training.exceptions.InvalidAgeException;

import java.util.regex.Pattern;
import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

import java.time.format.DateTimeParseException;
import java.time.Period;

public class Validiator {


    private final static int MAX_AGE = 80;
    private final static int MIN_AGE = 18;

    public boolean validiateName(String inputString) {
	String regexPattern = "[A-Z][a-z]*([ ]{0,1}[a-z])*[ ]*";
	boolean matches = Pattern.matches(regexPattern,inputString);
	return matches;
    }

    public boolean validiateContact(String inputString) {
	String regexPattern = "^[6-9](\\d{9})$";//"[0-9]{10}";
	boolean matches = Pattern.matches(regexPattern,inputString);
	return matches;
    }

    public boolean validiateEmail(String inputString) {
	String regexPattern = "^[a-zA-Z]{1,20}[[0-9]{0,5}[a-zA-Z]{0,20}]*[_]{0,2}([.]{0,1}[a-zA-Z0-9])*[@][a-z]{0,7}([.][a-z]{1,5})+$";
	boolean matches = Pattern.matches(regexPattern,inputString);
	return matches;
    }

    public boolean validiateBirthdate(String inputString) {	
	LocalDate curDate = LocalDate.now();
	//LocalDate LowestDatev = LocalDate.parse("1920-01-01");
	try {
	    LocalDate dob = LocalDate.parse(inputString);
	    if (curDate.compareTo(dob) > MIN_AGE && curDate.compareTo(dob) < MAX_AGE) 
		return true;
	    else {
		System.out.println("invalid age, age should be between 18 to 80 your age is "+curDate.compareTo(dob));
		//throw new InvalidAgeException();
		return false;
	    }
	} catch (Exception e) {
	    System.out.println("birthdate entered is invalid, enter in format YYYY-MM-DD"+e);
	    return false;
        }

    }

    public boolean isValidDate(String dateStr) {
	try {
	    LocalDate date = LocalDate.parse(dateStr);
	    return true;
	} catch (DateTimeParseException e) {
	    return false;
        }
    }

    public LocalDate stringToDate(String date) {
	return LocalDate.parse(date);
    }

    public boolean isValidlLeaveDates(String fromDate,String toDate,int leaveCount) {

	final int MAX_LEAVES_ALLOWED = 10;
	int leavesLeft = MAX_LEAVES_ALLOWED - leaveCount;
	LocalDate sDate = stringToDate(fromDate);
	LocalDate tDate = stringToDate(toDate);
	long noOfDaysBetween = ChronoUnit.DAYS.between(sDate, tDate);
	if (noOfDaysBetween > leavesLeft) 
	    return false;
        else
	    return true; 
    }
}