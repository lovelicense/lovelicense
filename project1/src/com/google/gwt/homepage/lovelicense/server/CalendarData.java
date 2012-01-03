package com.google.gwt.homepage.lovelicense.server;

public class CalendarData {
	private int year;
	private int month;
	private int date;
	private int time;
	private boolean isYunMonth=false;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public boolean isYunMonth() {
		return isYunMonth;
	}
	public void setYunMonth(boolean isYunMonth) {
		this.isYunMonth = isYunMonth;
	} 
}
