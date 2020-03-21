package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public Map<Integer, Site> returnListOfAvailableSites(int campgroundId, LocalDate arrivalDate, LocalDate departureDate) {
		Map<Integer, Site> sites = new HashMap<>();
		String sql = "SELECT site_id, c.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities, c.daily_fee FROM site s "
				+ "JOIN campground c ON s.campground_id = c.campground_id "
				+ "WHERE NOT (site_id IN (SELECT site_ID FROM reservation WHERE (start_date,(start_date + num_days)) OVERLAPS (?, ?))) AND c.campground_id = ? ORDER BY site_number LIMIT 5;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, arrivalDate, departureDate, campgroundId);
		while (results.next()) {
			Site mySite = createNewSite(results);
			sites.put(mySite.getSiteNumber(), mySite);
		}
		return sites;
	}

	
	private Site createNewSite(SqlRowSet results) {
		Site mySite = new Site();
		mySite.setId(results.getInt("site_id"));
		mySite.setCampgroundId(results.getInt("campground_id"));
		mySite.setSiteNumber(results.getInt("site_number"));
		mySite.setMaxOccupancy(results.getInt("max_occupancy"));
		mySite.setAccessible(results.getBoolean("accessible"));
		mySite.setMaxRvLength(results.getInt("max_rv_length"));
		mySite.setUtilities(results.getBoolean("utilities"));
		mySite.setDailyFee(results.getBigDecimal("daily_fee"));
		return mySite;
	}
}
