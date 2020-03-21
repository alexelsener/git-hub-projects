package com.techelevator.model;

public enum ActivityLevel {

	INACTIVE("Inactive"),
	SEDENTARY("Sedentary"),
	ACTIVE("Active"),
	EXTREMELY_ACTIVE("Extremely Active")
	;
	
	public String activityLevel;
	
	ActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}
	
	public String getActivityLevel() {
		return activityLevel;
	}
	
}
