package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;

public class JDBCCampgroundDAO implements CampgroundDAO{

	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	@Override
	public List<Campground> returnListOfCampgroundsByParkId(int parkId) {
		List<Campground> campgrounds= new ArrayList<>();
		String sql = "SELECT campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee FROM campground WHERE park_id = ? ORDER BY name;"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
		while (results.next()) {
			Campground myCampground = createNewCampground(results);
			campgrounds.add(myCampground);
		}
		return campgrounds;
	}
	
	private Campground createNewCampground(SqlRowSet results) {
		Campground myCampground = new Campground();
		myCampground.setId(results.getInt("campground_id"));
		myCampground.setName(results.getString("name"));
		myCampground.setParkId(results.getInt("park_id"));
		myCampground.setOpenFrom(results.getString("open_from_mm"));
		myCampground.setOpenTo(results.getString("open_to_mm"));
		myCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		return myCampground;
	}
	
}
