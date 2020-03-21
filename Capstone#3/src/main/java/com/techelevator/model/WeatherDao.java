package com.techelevator.model;

import java.util.List;

public interface WeatherDao {
	
	public List<Weather> getForecastByParkCode(String parkCode);
	public List<Weather> getForecastByLatAndLong(double latitude, double longitute);
	
}
