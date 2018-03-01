package com.sargije.rest.hidmet.app.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sargije.rest.hidmet.app.model.CityModel;
import com.sargije.rest.hidmet.app.model.FiveDayForecastModel;
import com.sargije.rest.hidmet.app.repository.CityRepository;
import com.sargije.rest.hidmet.app.repository.CurrentForecastRepository;
import com.sargije.rest.hidmet.app.repository.FiveDayForecastRepository;
import com.sargije.rest.hidmet.app.repository.ShortTermForecastRepository;

@Controller
@RequestMapping(value = "/readable")
public class ReadableController {
	@Autowired
	CurrentForecastRepository currentForecastRepository;
	
	@Autowired
	ShortTermForecastRepository shortTermForecastRepository;
	
	@Autowired
	FiveDayForecastRepository fiveDayForecastRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@RequestMapping(value = "/fiveday", method = RequestMethod.GET)
	public String getFiveDayForecast(Model model){
		
		List<FiveDayForecastModel> listFiveDayForecastModel = fiveDayForecastRepository.findByActive(1l);
		
		Set<CityModel> listCityModel = new HashSet<CityModel>(); 
		for(FiveDayForecastModel fiveDayForecastModel : listFiveDayForecastModel){
			listCityModel.add(fiveDayForecastModel.getCityId());
		}
		model.addAttribute("listFiveDayForecast", listFiveDayForecastModel);
		model.addAttribute("listCities", listCityModel);
        return "readable";
	}
}
