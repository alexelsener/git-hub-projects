package com.techelevator.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class Survey {
	private int surveyId;
	private String parkCode;
	@NotBlank(message = "Please provide a valid email address.")
	@Email(message = "That is not a valid email address.")
	private String emailAddress;
	private String state;
	private String activityLevel;
	
	@AssertTrue(message = "Please select one of the fifty states provided.")
	public boolean isStateValid() {
		boolean result = false;
		States[] states = States.values();
		for (States s : states) {
			if(state != null && state.equals(s.getName())) {
				result = true;
			}
		}
		return result;
	}
	
	@AssertTrue(message = "Please select an activity level.")
	public boolean isActivityLevelValid() {
		boolean result = false;
		ActivityLevel[] activityLevels = ActivityLevel.values();
		for (ActivityLevel al : activityLevels) {
			if(activityLevel != null && activityLevel.equals(al.getActivityLevel())) {
				result = true;
			}
		}	
		return result;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}

}
