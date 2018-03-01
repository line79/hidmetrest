package com.sargije.rest.hidmet.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sargije.rest.hidmet.app.model.CurrentForecastModel;
import com.sargije.rest.hidmet.app.model.FiveDayForecastModel;
import com.sargije.rest.hidmet.app.model.ShortTermForecastModel;
import com.sargije.rest.hidmet.app.repository.CurrentForecastRepository;
import com.sargije.rest.hidmet.app.repository.FiveDayForecastRepository;
import com.sargije.rest.hidmet.app.repository.ShortTermForecastRepository;

@RestController
@RequestMapping(value = "/rest")
public class ForecastController {

	@Autowired
	CurrentForecastRepository currentForecastRepository;
	
	@Autowired
	ShortTermForecastRepository shortTermForecastRepository;
	
	@Autowired
	FiveDayForecastRepository fiveDayForecastRepository;
	
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public List<CurrentForecastModel> showForecast(){

		//forecastControllerParser.parseForecastFromHidmet();

		//CurrentForecastModel currentForecastModel = new CurrentForecastModel();
		List<CurrentForecastModel> currentForecastData = currentForecastRepository.findByActive(1l);
		return currentForecastData;

	}

	@RequestMapping(value = "/shortterm", method = RequestMethod.GET)
	public List<ShortTermForecastModel> showCurrentWeather(){

		//forecastControllerParser.parseCurrentWeatherFromHidmet();

		List<ShortTermForecastModel> forecastModel = shortTermForecastRepository.findByActive(1l);
		return forecastModel;
	}
	
	@RequestMapping(value = "/fiveday", method = RequestMethod.GET)
	public List<FiveDayForecastModel> showFiveDayForecast(){		
		
		List<FiveDayForecastModel> fiveDayForecastModel = fiveDayForecastRepository.findByActive(1l);
		return fiveDayForecastModel;
	}
	
}
