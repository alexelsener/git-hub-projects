package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcSurveyDao implements SurveyDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcSurveyDao(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	@Override
	public List<SurveyResult> getFavoriteParks() {
		List<SurveyResult> favoriteParks = new ArrayList<SurveyResult>();
		String sqlQuery = "SELECT park.parkName, park.parkCode, park.state, park.parkdescription, count(survey_result.parkcode) AS fav_count FROM park "
				+ " JOIN survey_result ON survey_result.parkcode = park.parkCode GROUP BY park.parkCode ORDER BY fav_count desc, park.parkName;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		while (results.next()) {
			favoriteParks.add(mapResultToSurvey(results));
		}
		return favoriteParks;
	}

	@Override
	public void save(Survey survey) {
		String sqlQuery = "INSERT INTO survey_result (parkcode, emailaddress, state, activitylevel) "
				+ " VALUES (?, ?, ?, ?) ;";
		jdbcTemplate.update(sqlQuery, survey.getParkCode(), survey.getEmailAddress(), survey.getState(), survey.getActivityLevel());
	}

	private SurveyResult mapResultToSurvey(SqlRowSet results) {
		SurveyResult surveyResult = new SurveyResult();
		surveyResult.setParkName(results.getString("parkName"));
		surveyResult.setState(results.getString("state"));
		surveyResult.setParkDescription(results.getString("parkDescription"));
		surveyResult.setFavoriteCount(results.getInt("fav_count"));
		return surveyResult;
	}
	
}
