package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

//@Component
public class JdbcWeatherDao implements WeatherDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcWeatherDao(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	@Override
	public List<Weather> getForecastByParkCode(String parkCode) {
		List<Weather> forecast = new ArrayList<Weather>();
		String sqlQuery = "SELECT parkcode, fivedayforecastvalue, low, high, forecast FROM weather "
				+ " WHERE parkcode = ? ORDER BY fivedayforecastvalue ;";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, parkCode);
		while (results.next()) {
			forecast.add(mapRowToWeather(results));
		}
		return forecast;
	}
	
	public List<Weather> getForecastByLatAndLong(double latitude, double longitute){
	 List<Weather> weather = new ArrayList<Weather>(); 	
	 return weather;
	}
	
	private Weather mapRowToWeather(SqlRowSet results) {
		Weather weather = new Weather();
		weather.setParkCode(results.getString("parkcode"));
		weather.setFiveDayForecastValue(results.getInt("fivedayforecastvalue"));
		weather.setLow(results.getInt("low"));
		weather.setHigh(results.getInt("high"));
		weather.setForecast(results.getString("forecast"));
		return weather;
	}
}
