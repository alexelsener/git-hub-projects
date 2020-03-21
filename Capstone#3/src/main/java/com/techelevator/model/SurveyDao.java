package com.techelevator.model;

import java.util.List;

public interface SurveyDao {

	public List<SurveyResult> getFavoriteParks();
	public void save(Survey survey);
	
}
