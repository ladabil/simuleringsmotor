package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.text.DateFormat;

public class MyLog {
	static public void log(String format, Object... args)
	{
		java.util.Date date = new java.util.Date();
		DateFormat df = DateFormat.getDateTimeInstance();
		
		System.out.printf("%s: '%s'", df.format(date), String.format(format, args));
	}
	
	static public void println(String text)
	{
		MyLog.log("%s\n", text);
	}
	
	
	
}
