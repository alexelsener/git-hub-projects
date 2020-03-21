package com.techelevator.model.jdbc;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public int createReservation(int site_id, String reservationName, LocalDate arrivalDate, int numberOfDays) {
		int reservationId = 0;
		String sql = "insert into reservation (site_id, name, start_date, num_days) "
				+ "values (?, ?, ?, ?) returning reservation_id;";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, site_id, reservationName,
				arrivalDate, numberOfDays);

		if (results.next()) {
			reservationId = results.getInt("reservation_id");
		}
		
		return reservationId;
	}

}
