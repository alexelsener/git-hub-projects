package com.techelevator.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.model.ActivityLevel;
import com.techelevator.model.Park;
import com.techelevator.model.ParkDao;
import com.techelevator.model.States;
import com.techelevator.model.Survey;
import com.techelevator.model.SurveyDao;
import com.techelevator.model.SurveyResult;
import com.techelevator.model.Weather;
import com.techelevator.model.WeatherDao;

@Controller
public class ParkController {
	
	@Autowired
	private ParkDao parkDao;
	@Autowired
	private WeatherDao weatherDao;
	@Autowired
	private SurveyDao surveyDao;
	
	@RequestMapping(path="/", method=RequestMethod.GET) 
	public String displayHomePage(ModelMap mm) {
		List<Park> allParks = parkDao.getAllParks();
		mm.put("parks", allParks);
		return "home";
	}
	
	@RequestMapping(path="/details", method=RequestMethod.GET) 
	public String displayParkDetails(@RequestParam String parkCode,
									
									ModelMap mm,
									HttpSession s) {
		Park park = parkDao.getParkByCode(parkCode);
		mm.put("park", park);
		List<Weather> weather = weatherDao.getForecastByLatAndLong(park.getLatitude(), park.getLongitude());
		if(s.getAttribute("tempSelect")!=null && s.getAttribute("tempSelect").equals("c")) {
			for (Weather w : weather) {
				w.setCelsius(true);
			}
		} 
		mm.put("forecasts", weather);
		return "details";
	}
	
	@RequestMapping(path="/details", method=RequestMethod.POST) 
	public String displayParkDetailsWithCelsius(@RequestParam String parkCode,
												@RequestParam String tempSelect, 
												RedirectAttributes ra,
												HttpSession s) {
		s.setAttribute("tempSelect", tempSelect);
		ra.addAttribute("parkCode", parkCode);
		return "redirect:/details";
	}
	
	@RequestMapping(path="/survey", method=RequestMethod.GET)
	public String displaysSurvey(ModelMap mm) {
		if(mm.containsAttribute("surveyData") == false) {
			Survey empty = new Survey();
			mm.put("surveyData", empty);
		}
		List<Park> allParks = parkDao.getAllParks();
		mm.put("parks", allParks);
		mm.put("states", States.values());
		mm.put("activityLevels", ActivityLevel.values());
		return "survey";
	}
	
	@RequestMapping(path="/survey", method=RequestMethod.POST) 
	public String processSurveyInput(@Valid @ModelAttribute Survey survey,
									BindingResult result,
									RedirectAttributes ra) {
		List<Park> parks = parkDao.getAllParks();
		for (Park p : parks) {
			if(survey.getParkCode().equals(p.getParkCode())) {
				if(result.hasErrors()) {
					ra.addFlashAttribute("surveyData", survey);
					ra.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "surveyData", result);
					return "redirect:/survey";
				} else {
				surveyDao.save(survey);
				return "redirect:/surveyResults";
				}
			} 
		}
		return "redirect:/survey";
	}
	
	@RequestMapping(path="/surveyResults", method=RequestMethod.GET) 
	public String displaySurveyResults(ModelMap mm) {
		
		List<SurveyResult> favParks = surveyDao.getFavoriteParks();
		mm.put("favoriteParks", favParks);
		return "surveyResults";
	}
	
	
	
}
