package com.techelevator.model;

import java.util.List;

public interface CampgroundDAO {

	/**
	 * get all campgrounds in the selected national park. 
	**/
	public List<Campground> returnListOfCampgroundsByParkId(int parkId);
	
}
