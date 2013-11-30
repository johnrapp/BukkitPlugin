package se.jrp.bankplugin.resources;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class Functions {
	public static String getTimeStamp() {
		return (new SimpleDateFormat("dd/MM HH:mm:ss")).format(new Date());
	}
	
	public static Integer getInteger(String s, Integer defaultValue) {
	    try { return Integer.parseInt(s); }
	    catch(NumberFormatException e) { return defaultValue; }
	}
	
	public static Boolean getBoolean(String s, Boolean defaultValue) {
	    try { return Boolean.parseBoolean(s); }
	    catch(NumberFormatException e) { return defaultValue; }
	}
	
	public static String[] cutFirstIndex(String[] array) {
		return Arrays.copyOfRange(array, 1, array.length);
	}
}