package com.techelevator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private Scanner input;
	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;

	private static final String PARK_MENU_OPTION_VIEW_CAMPGROUND = "View Campgrounds";
	private static final String PARK_MENU_RETURN_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_OPTION_VIEW_CAMPGROUND,
								PARK_MENU_RETURN_PREVIOUS_SCREEN };
	private static final String PARK_CAMPGROUND_MENU_SEARCH_AVAILABLE_RESERVATION = "Search for Available Reservation";
	private static final String PARK_CAMPGROUND_MENU_RETURN_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] PARK_CAMPGROUND_MENU_OPTIONS = new String[] {
			PARK_CAMPGROUND_MENU_SEARCH_AVAILABLE_RESERVATION, PARK_CAMPGROUND_MENU_RETURN_PREVIOUS_SCREEN };

	public static void main(String[] args) {
		CampgroundCLI application = new CampgroundCLI();
		application.run();
	}

	public CampgroundCLI() {
		this.menu = new Menu(System.in, System.out);

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername(System.getenv("DB_USERNAME"));
		dataSource.setPassword(System.getenv("DB_PASSWORD"));

		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
	}

	public void run() {
		handleMainMenu();
		System.out.println("Goodbye!");
	}

	private void handleMainMenu() {
		printHeading("View Parks Interface");

		List<Park> allParks = parkDAO.getAllParks();
		int i = 1;
		for (Park myPark : allParks) {
			System.out.println(i + ") " + myPark.getName());
			i++;
		}
		System.out.println("Q) quit\n");
		input = new Scanner(System.in);
		System.out.println("Select a Park for Further Details >>> ");
		int userinputInt;
		boolean done = false;
		while (done == false) {
			String userinput = input.nextLine().toUpperCase();
			if (userinput.equals("Q")) {
				done = true;
			} else {
				try {
					userinputInt = Integer.parseInt(userinput);
					if (userinputInt > 0 && userinputInt <= allParks.size()) {
						listParkInformation(allParks.get(userinputInt - 1));
						done = true;
					} else {
						System.out.println("Not a valid option! Please select one of the valid inputs.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Not a valid option! Please select one of the valid inputs.");
				}
			}
		}
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	private void listParkInformation(Park selectedPark) {
		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
		printHeading("Park Information Screen");
		System.out.println(selectedPark.getName() + " National Park");
		System.out.format("%-20s%-20s\n", "Location:", selectedPark.getLocation());
		System.out.format("%-20s%-20s\n", "Established:", dateformatter.format(selectedPark.getEstablishDate()));
		System.out.format("%-20s%-20s\n", "Area:", selectedPark.getArea() + " sq km");
		System.out.format("%-20s%-20s\n", "Annual Visitors:", selectedPark.getVisitors());
		System.out.println();
		System.out.println(selectedPark.getDescription());
		System.out.println();

		printHeading("Select a Command");
		String choice = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);
		if (choice.equals(PARK_MENU_OPTION_VIEW_CAMPGROUND)) {
			handleCampgroundMenu(selectedPark);
		} else if (choice.equals(PARK_MENU_RETURN_PREVIOUS_SCREEN)) {
			handleMainMenu(); //not best practice to re-call method.
		}
	}

	private void handleCampgroundMenu(Park selectedPark) {
		printHeading("Park Campgrounds");
		System.out.println(selectedPark.getName() + " National Park Campgrounds");
		System.out.println("");
		List<Campground> allCampgrounds = campgroundDAO.returnListOfCampgroundsByParkId(selectedPark.getId());
		listCampgrounds(allCampgrounds);

		printHeading("Select a Command");
		String choice = (String) menu.getChoiceFromOptions(PARK_CAMPGROUND_MENU_OPTIONS);
		if (choice.equals(PARK_CAMPGROUND_MENU_SEARCH_AVAILABLE_RESERVATION)) {
			promptForCampground(selectedPark);
		} else if (choice.equals(PARK_CAMPGROUND_MENU_RETURN_PREVIOUS_SCREEN)) {
			handleMainMenu();
		}
	}

	private void listCampgrounds(List<Campground> campgroundsToDisplay) {
		int i = 1;
		System.out.format("   %-35s %-10s %-10s %s \n", "Name", "Open", "Close", "Daily Fee");
		for (Campground myCampground : campgroundsToDisplay) {
			System.out.println("#" + i + " " + myCampground);
			i++;
		}
	}

	private void promptForCampground(Park selectedPark) {
		System.out.println("Search for Campground Reservation");
		List<Campground> allCampgrounds = campgroundDAO.returnListOfCampgroundsByParkId(selectedPark.getId());
		listCampgrounds(allCampgrounds);

		System.out.print("Which campground (enter 0 to cancel)? ");
		input = new Scanner(System.in);
		int campGroundSelection = 0;
		boolean done = false;
		while (done == false) {
			String userInputString = input.nextLine();
			try {
				campGroundSelection = Integer.parseInt(userInputString);
				if (campGroundSelection >= 0 && campGroundSelection <= allCampgrounds.size()) {
					done = true;
				} else {
					System.out.println(
							"Not a Valid Selection! Please select a number between 0 and " + allCampgrounds.size());
				}
			} catch (NumberFormatException e) {
				System.out.println(
						"Not a Valid Selection! Please select a number between 0 and " + allCampgrounds.size());
			}
		}
		if (campGroundSelection == 0) {
			handleMainMenu();
		}
		promptForDateRange(selectedPark, allCampgrounds, campGroundSelection);
	}

	private void promptForDateRange(Park selectedPark, List<Campground> allCampgrounds, int campGroundSelection) {
		LocalDate arrivalDate = promtForArrivalDate();
		if (arrivalDate == null) {
			promptForCampground(selectedPark);
		} else {
			LocalDate departureDate = promptForDepartureDate(arrivalDate);
			if (departureDate == null) {
				promptForCampground(selectedPark);
			} else {
				returnAvailableSites(selectedPark, allCampgrounds, campGroundSelection,
						allCampgrounds.get(campGroundSelection - 1), arrivalDate, departureDate);
			}
		}
	}

	private LocalDate promptForDepartureDate(LocalDate arrivalDate) {
		System.out.print("What is the departure date (YYYY-MM-dd)? ");
		LocalDate departureDate = null;
		String departureDateString = input.nextLine();
		try {
			departureDate = LocalDate.parse(departureDateString);
			if (departureDate.compareTo(arrivalDate) <= 0) {
				departureDate = null;
				System.out.println(
						"THAT'S BEFORE THE ARRIVAL DATE! Please input a departure date in the format YYYY-MM-dd after the arrival date.");
			}
		} catch (DateTimeParseException e) {
			System.out.println("Not a Valid Date! Please input a date in the format YYYY-MM-dd");
		}
		return departureDate;
	}

	private LocalDate promtForArrivalDate() {
		System.out.print("What is the arrival date (YYYY-MM-dd)? ");
		LocalDate arrivalDate = null;
		String arrivalDateString = input.nextLine();
		try {
			arrivalDate = LocalDate.parse(arrivalDateString);
			if (arrivalDate.compareTo(LocalDate.now()) <= 0) {
				arrivalDate = null;
				System.out.println(
						"THAT'S IN THE PAST! Please input an arrival date in the format YYYY-MM-dd after today's date.");
			}
		} catch (DateTimeParseException e) {
			System.out.println("Not a Valid Date! Please input a date in the format YYYY-MM-dd");
		}

		return arrivalDate;
	}

	private void returnAvailableSites(Park selectedPark, List<Campground> allCampgrounds, int campGroundSelection,
									Campground selectedCampground, LocalDate arrivalDate, LocalDate departureDate) {
		input = new Scanner(System.in);
		int numberOfNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
		System.out.println("Results Matching Your Search Criteria");
		Map<Integer, Site> allAvailableSites = siteDAO.returnListOfAvailableSites(selectedCampground.getId(), arrivalDate,
				departureDate);
		displaySites(allAvailableSites, numberOfNights);
		// Send them back to prompt for new dates.
		if (allAvailableSites.size() == 0) {
			System.out.println(
					"There are no available sites for those dates! Would you like to try a different date range (Y/N)? ");
			String tryAgain = input.nextLine();
			if (tryAgain.toUpperCase().equals("Y")) {
				promptForDateRange(selectedPark, allCampgrounds, campGroundSelection); // Prompt for new dates to find
																						// some new available dates if
																						// possible.
			} else if (tryAgain.toUpperCase().equals("N")) {
				handleMainMenu(); // If they don't want to try again, return the user to main menu.
			}
		} else {
			// Ask for sites to be reserved via users input.
			System.out.print("Which site should be reserved (enter 0 to cancel)? ");
			Integer siteSelection = 0;
			boolean done = false;
			while (done == false) {
				String userInputString = input.nextLine();
				try {
					siteSelection = Integer.parseInt(userInputString);
					int siteIdSelected = allAvailableSites.get(siteSelection).getId(); // ensure the selection
																								// exists in
																								// allAvailableSites.
					if (siteSelection == 0 || siteIdSelected != 0) {
						done = true;
					} else {
						System.out.println("Not a Valid Selection! Please try again.");
					}
				} catch (Exception e) {
					System.out.println("Not a Valid Selection! Please try again.");
				}
			}
			if (siteSelection == 0) {
				handleCampgroundMenu(selectedPark);
			}

			System.out.print("What name should the reservation be made under? ");
			String reservationName = input.nextLine();
			int reservationId = reservationDAO.createReservation(allAvailableSites.get(siteSelection).getId(), reservationName, arrivalDate, numberOfNights);
			if (reservationId == 0) {
				System.out.println("Reservation failed. Please call system administrator.");
			} else {
				System.out.println("The reservation has been made and the confirmation id is " + reservationId);
			}

			System.out.print("Thank you for using our application. Would you like to make another reservation (Y/N)? ");
			String newReservation = input.nextLine();
			if (newReservation.toUpperCase().equals("Y")) {
				handleMainMenu();
			}
		}
	}

	private void displaySites(Map<Integer, Site> sitesToDisplay, int numberOfNights) {
		System.out.format("%-10s %-10s %-15s %-15s %-10s %-10s\n", "Site No.", "Max Occup.", "Accessible?",
				"Max RV Length", "Utility", "Cost");
		for (Map.Entry<Integer, Site> mySite : sitesToDisplay.entrySet()) {
			System.out.println(mySite.getValue().getSiteRowWithCost(numberOfNights));
		}

	}

}
