package com.techelevator.model;

import java.util.List;

public interface ParkDAO {
	
	/**
	* Get all parks from the datasource.
	* 
	* @return all parks as a Park object in a List.
	**/
	public List<Park> getAllParks();
	
	/**
	* Get all parks from the datasource with searched name input.
	* 
	* @return all parks as a Park object in a List.
	**/
	public List<Park> searchParksByName(String nameSearch);
	
	/**
	* Get a single park from the datasource associated with a specific id.
	* 
	* @return a single park as a Park object.
	**/
	public Park getParkById(int park_id);
	
	
}
