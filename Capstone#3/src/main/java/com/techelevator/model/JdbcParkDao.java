package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcParkDao implements ParkDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcParkDao(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> allParks = new ArrayList<>();
		String sqlQuery = "SELECT parkCode, parkName, state, acreage, milesOfTrail, numberOfCampSites, climate, "
				+ " yearFounded, annualVisitorCount, inspirationalQuote, inspirationalQuoteSource, parkDescription, entryFee, "
				+ " numberOfAnimalSpecies, longitude, latitude FROM park "
				+ " ORDER BY parkName;";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		while (results.next()) {
			Park park = mapResultToPark(results);
			allParks.add(park);
		}
		return allParks;
	}
	
	@Override
	public Park getParkByCode(String parkCode) {
		Park parkById = new Park();
		String sqlQuery = "SELECT parkCode, parkName, state, acreage, milesOfTrail, numberOfCampSites, climate, " + 
				"yearFounded, annualVisitorCount, inspirationalQuote, inspirationalQuoteSource, parkDescription, " + 
				"entryFee, numberOfAnimalSpecies, " + 
				"latitude, longitude FROM park " + 
				"WHERE parkCode = ? ;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, parkCode);
		while (results.next()) {
			parkById = mapResultToPark(results);
		}
		return parkById;
	}
	
	private Park mapResultToPark(SqlRowSet results) {
		Park park = new Park();
		park.setParkCode(results.getString("parkCode"));
		park.setParkName(results.getString("parkName"));
		park.setState(results.getString("state"));
		park.setAcreage(results.getInt("acreage"));
		park.setMilesOfTrail(results.getDouble("milesOfTrail"));
		park.setNumberOfCampsites(results.getInt("numberOfCampSites"));
		park.setClimate(results.getString("climate"));
		park.setYearFounded(results.getInt("yearFounded"));
		park.setAnnualVisitorCount(results.getInt("annualVisitorCount"));
		park.setInspirationalQuote(results.getString("inspirationalQuote"));
		park.setInspirationalQuoteSource(results.getString("inspirationalQuoteSource"));
		park.setParkDescription(results.getString("parkDescription"));
		park.setEntryFee(results.getInt("entryFee"));
		park.setNumberOfAnimalSpecies(results.getInt("numberOfAnimalSpecies"));
		park.setLongitude(results.getDouble("longitude"));
		park.setLatitude(results.getDouble("latitude"));
		return park;
	}

}
