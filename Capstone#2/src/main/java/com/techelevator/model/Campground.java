package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Campground {

	private int id; 
	private int parkId;
	private String name;
	private String openFrom;
	private String openTo;
	private BigDecimal dailyFee;
	private static Map<String, String> months; 
		
	public Campground() {
			months = new HashMap<String, String>(); 
			months.put("01", "January");
			months.put("02", "February");
			months.put("03", "March");
			months.put("04", "April");
			months.put("05", "May");
			months.put("06", "June");
			months.put("07", "July");
			months.put("08", "August");
			months.put("09", "September");
			months.put("10", "October");
			months.put("11", "November");
			months.put("12", "December");
			
		}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenFrom() {
		return openFrom;
	}
	public void setOpenFrom(String openFrom) {
		this.openFrom = openFrom;
	}
	public String getOpenTo() {
		return openTo;
	}
	public void setOpenTo(String openTo) {
		this.openTo = openTo;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	@Override
	public String toString() {
		return String.format("%-35s %-10s %-10s $%5.2f", name, months.get(openFrom), months.get(openTo), dailyFee);
	}
	
}
