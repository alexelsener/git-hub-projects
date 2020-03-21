package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> parks = new ArrayList<>();
		String sql = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park ORDER BY name;"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			Park myPark = createNewPark(results);
			parks.add(myPark);
		}
		return parks;
	}

	@Override
	public List<Park> searchParksByName(String nameSearch) {
		List<Park> parks = new ArrayList<>();
		String sql = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park WHERE name ilike ?;"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%"+nameSearch+"%");
		while (results.next()) {
			Park myPark = createNewPark(results);
			parks.add(myPark);
		}
		return parks;
	}



	@Override
	public Park getParkById(int park_id) {
		Park myPark = new Park();
		String sql = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park WHERE park_id = ?;"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, park_id);
		while (results.next()) {
			myPark = createNewPark(results);
		}
		return myPark;
	}
	
	private Park createNewPark(SqlRowSet results) {
		Park myPark = new Park();
		myPark.setId(results.getInt("park_id"));
		myPark.setName(results.getString("name"));
		myPark.setLocation(results.getString("location"));
		myPark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		myPark.setArea(results.getInt("area"));
		myPark.setVisitors(results.getInt("visitors"));
		myPark.setDescription(results.getString("description"));
		return myPark;
	}
	
}
