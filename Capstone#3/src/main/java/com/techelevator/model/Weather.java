package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Weather {

	private String parkCode;
	private int fiveDayForecastValue;
	private double low;
	private double high;
	private double celsiusLow;
	private double celsiusHigh;
	private String forecast;
	private String summary;
	private boolean celsius;
	private static final int HIGH_THRESHOLD = 75;
	private static final int LOW_THRESHOLD = 20;
	private static final int TEMP_DIFFERENCE_THRESHOLD = 20;
	
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public int getFiveDayForecastValue() {
		return fiveDayForecastValue;
	}
	public void setFiveDayForecastValue(int fiveDayForecastValue) {
		this.fiveDayForecastValue = fiveDayForecastValue;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double i) {
		this.high = i;
	}
	public double getCelsiusLow() {
		celsiusLow = (int) ((low-32) * (5.0/9.0));
		return celsiusLow;
	}
	public void setCelsiusLow(double celsiusLow) {
		this.celsiusLow = celsiusLow;
	}
	public double getCelsiusHigh() {
		celsiusHigh = (int) ((high-32) * (5.0/9.0));
		return celsiusHigh;
	}
	public void setCelsiusHigh(double celsiusHigh) {
		this.celsiusHigh = celsiusHigh;
	}
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	
	public String getImageName() {
		int i = forecast.indexOf(" ");
		String s = "";
		if (i>=0) {
			s += forecast.substring(0, i);
			s += forecast.substring(i+1, i+2).toUpperCase();
			s += forecast.substring(i+2);
		} else {
			s = forecast;
		}
		return s;
	}
	
	public List<String> getRecommendations() {
		List<String> recommendations = new ArrayList<String>();
		if(forecast.contains("snow")) {
			recommendations.add("Pack Snow Shoes!");
		}
		if(forecast.contains("rain")) {
			recommendations.add("Pack Rain Gear and Waterproof Shoes!");
		}
		if(forecast.contains("thunderstorms")) {
			recommendations.add("Seek shelter and avoid hiking on exposed ridges!");
		}
		if(forecast.contains("sun")) {
			recommendations.add("Pack Sunblock!");
		}
		if(high > HIGH_THRESHOLD) {
			recommendations.add("Bring an extra gallon of water!");
		}
		if(high - low > TEMP_DIFFERENCE_THRESHOLD) {
			recommendations.add("Wear breathable layers");
		}
		if(low < LOW_THRESHOLD) {
			recommendations.add("Beware of exposure to frigid temperatures!");
		}
		if(low > LOW_THRESHOLD && high < HIGH_THRESHOLD) {
			recommendations.add("Enjoy your day!");
		}
		return recommendations;
	}
	public boolean isCelsius() {
		return celsius;
	}
	public void getCelsius(boolean celsius) {
		this.celsius = celsius;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public void setCelsius(boolean celsius) {
		this.celsius = celsius;
	}
	
	
	
}

