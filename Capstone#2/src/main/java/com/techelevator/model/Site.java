package com.techelevator.model;

import java.math.BigDecimal;

public class Site {

	private int id;
	private int campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accessible;
	private int maxRvLength;
	private boolean utilities;
	private BigDecimal dailyFee;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public String getAccessible() {
		return accessible ? "Yes": "No";
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public String getMaxRvLength() {
		return (maxRvLength == 0) ? "N/A" : String.valueOf(maxRvLength);
	}
	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	public String getUtilities() {
		return utilities ? "Yes" : "N/A";
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	public String getSiteRowWithCost(int numberOfNights) {
		return String.format("%-10s %-10s %-15s %-15s %-10s $%7.2f", siteNumber, maxOccupancy, getAccessible(), getMaxRvLength(), getUtilities(), (dailyFee.multiply(new BigDecimal(numberOfNights))));
	}
	
	@Override
	public String toString() {
		return String.format("%-30s %-20s %-20d %-20d", siteNumber, maxOccupancy, accessible, maxRvLength, utilities);
	}

}
