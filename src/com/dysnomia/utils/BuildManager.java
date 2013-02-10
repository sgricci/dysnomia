package com.dysnomia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class BuildManager {
	private static final String BUILD_ID = "build-number";
	private static final String WHEN = "build-date";
	private static final String VERSION = "version-number";
	private static final Locale LOCALE = Locale.UK;
	
	private String version = "0.1";
	private int build = 0;
	private Date when;
	
	private DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, LOCALE);
	private final File f;
	private final Properties p = new Properties();
	
	public BuildManager(String file)
	{
		f = new File(file);
		if (f.exists())
		{
			InputStream is = null;
			try {
				is = new FileInputStream(f);
				init(is);
				step(version);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(3);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(4);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(5);
			}
		}
	}

	private void init(InputStream is) throws IOException {
		try {
			load(is);
		} finally {
			if (is != null)
				is.close();
		}
	}

	private void load(InputStream is) throws IOException {
		 p.load(is);
		 
		 try {
			 Integer n = Integer.valueOf(p.getProperty(BUILD_ID));
			 if (n != null)
				 build = n.intValue();
		 } catch(Throwable e)
		 {
			 // Absorb and deflect
		 }
		 
		 try
	      {
	         String d = p.getProperty(WHEN);
	         if(d != null)
	            when = df.parse(d);
	      }
	      catch(Throwable e)
	      {
	         // Absorb
	      }   
	      
	      try
	      {
	         String ver = p.getProperty(VERSION);
	         if(ver != null)
	            version = ver;
	      }
	      catch(Throwable e)
	      {
	         // Absorb
	      }   
	}
	private void step(String newVersion) throws Exception
	{
		if(newVersion != null)
	    {
			if(!version.equals(newVersion)) 
	        {
				version = newVersion;
	            build = 0;
	        }
	    }
	      
		p.setProperty(VERSION, version);
	      
		build += 1;
		p.setProperty(BUILD_ID, Integer.toString(build));

		Calendar cal = newCalendar();
		when = cal.getTime();
		df.setCalendar(cal);
		String temp = df.format(when);      
		p.setProperty(WHEN, temp);
	      
	      
		OutputStream os = new FileOutputStream(f);
		try
		{
		   p.store(os, "build-manager");
		}
		finally
		{
		   if(os != null)
		      os.close();
		}
	}
	   
	private static Calendar newCalendar()
	{
		Calendar cal = Calendar.getInstance(LOCALE);
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		return cal;      
	}
	
	public String getBuildDate()
	{
		Calendar cal = newCalendar();
		cal.setTime(when);
		
		DateFormat ddff = new SimpleDateFormat("yyyy.MM.dd");
		ddff.setCalendar(cal);
		
		return ddff.format(cal.getTime());         
	}
	
	public String getBuildTime()
	{
		Calendar cal = newCalendar();
		cal.setTime(when);
		
		DateFormat ddff = new SimpleDateFormat("HH.mm");
		ddff.setCalendar(cal);
		
		return ddff.format(cal.getTime());         
	}
	
	public int getBuild() 
	{
		return build;
	}
	
	public String getVersion()
	{
		return version;
	}
}
