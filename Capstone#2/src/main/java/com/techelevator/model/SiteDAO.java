package com.techelevator.model;

import java.time.LocalDate;
import java.util.Map;

public interface SiteDAO {
	
	/**
	 * get all sites in the selected campground using the campgroundId. 
	**/
	public Map<Integer, Site> returnListOfAvailableSites(int campgroundId, LocalDate arrivalDate, LocalDate departureDate);
	
}
