package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {

	public int createReservation(int site_id, String reservationName, LocalDate arrivalDate, int numberOfDays);
	
}
