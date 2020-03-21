package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAnySetter;

@Component
public class RestWeatherDao implements WeatherDao {
	
	public static class DarkSkyDataPoint {
		public String summary; 
		public String icon; 
		public String precipType;
		
		@JsonAnySetter
		public Map<String, Double> allInfo = new HashMap<>();
	}
	
	public static class DarkSkyDataBlock {
		public List<DarkSkyDataPoint> data = new ArrayList<>();
	}

	public static class DarkSkyForecast {
		public DarkSkyDataBlock daily = new DarkSkyDataBlock();
	}
	
	private static final String BASE_URL = "https://api.darksky.net/forecast/";
//	please use your private key as an environment parameter prior to running in 
//	order to access the weather icons.
	private static final String KEY = System.getenv("API_KEY");
	private RestTemplate restTemplate = new RestTemplate();


	public List<Weather> getForecastByLatAndLong(double latitude, double longitute) {
		List<Weather> weather = new ArrayList<>();
		String url = BASE_URL + KEY + latitude + "," + longitute;
		try {
			DarkSkyForecast response = restTemplate.getForObject(url, DarkSkyForecast.class);
			int counter = 1;
			for(int i = 0; i < 5; i++) {
				DarkSkyDataPoint dsp = response.daily.data.get(i);
				Weather w = new Weather();
				w.setForecast(dsp.icon);
				w.setHigh(dsp.allInfo.get("temperatureHigh"));
				w.setLow(dsp.allInfo.get("temperatureLow"));
				w.setSummary(dsp.summary);
				counter++; 
				weather.add(w);
			}
		} catch (Exception e) {
			// return null list if exception is thrown.
		}
		return weather;
	
	}


	@Override
	public List<Weather> getForecastByParkCode(String parkCode) {
		// TODO Auto-generated method stub
		return null;
	}

}

