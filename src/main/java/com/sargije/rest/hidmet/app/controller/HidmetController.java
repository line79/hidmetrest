package com.sargije.rest.hidmet.app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sargije.rest.hidmet.app.model.CityModel;
import com.sargije.rest.hidmet.app.model.CurrentForecastModel;
import com.sargije.rest.hidmet.app.model.FiveDayForecastModel;
import com.sargije.rest.hidmet.app.model.ForecastDateModel;
import com.sargije.rest.hidmet.app.model.ShortTermForecastModel;
import com.sargije.rest.hidmet.app.repository.CityRepository;
import com.sargije.rest.hidmet.app.repository.CurrentForecastRepository;
import com.sargije.rest.hidmet.app.repository.FiveDayForecastRepository;
import com.sargije.rest.hidmet.app.repository.ForecastDateRepository;
import com.sargije.rest.hidmet.app.repository.ShortTermForecastRepository;

@Controller
public class HidmetController {
	
	@Autowired
	CurrentForecastRepository currentForecastRepository;
	
	@Autowired
	ShortTermForecastRepository shortTermForecastRepository;
	
	@Autowired
	FiveDayForecastRepository fiveDayForecastRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	ForecastDateRepository forecastDateRepository;
	
	@RequestMapping(value = "/populate_fiveday_forecast")
	public String populateFivedayForecast(Model model) {
		
		Document doc;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. hh:mm:ss");
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MINUTE, 15); 
		calendar.set(Calendar.SECOND, 0);

		Date synchronizationTime = calendar.getTime();
		
		boolean isSyncTyme = fiveDayForecastRepository.existsByActiveAndSyncTime(1l, synchronizationTime);
		if(!isSyncTyme){
			try {
				doc = Jsoup.connect("http://www.hidmet.gov.rs/latin/prognoza/stanica.php").get();
				String[] stringTableTimestamp = doc.select("table tfoot tr td").get(0).text().split("\u00a0");
				
				doc.select("table").remove();
				Elements links = doc.select("div#sadrzaj div").get(0).select("a[href]");
							
				Date tableTime = formatter.parse(stringTableTimestamp[1].substring(0, 20));
				boolean isTableTime = fiveDayForecastRepository.existsByActiveAndTableTime(1l, tableTime);
				if(!isTableTime){
					formatter = new SimpleDateFormat("dd.MM.yyyy");
					List<FiveDayForecastModel> listFiveDayForecastModel = fiveDayForecastRepository.findByActive(1l);
					
					for(FiveDayForecastModel fiveDayForecastModel : listFiveDayForecastModel){
						fiveDayForecastModel.setActive((long) 0);
						
					}
					fiveDayForecastRepository.save(listFiveDayForecastModel);
					for(Element href : links){
						CityModel city = cityRepository.findByCityName(href.text());
						
						if(city == null){
							city = new CityModel();
							city.setCityName(href.text());
							cityRepository.save(city);
							city = cityRepository.findByCityName(href.text());
						}
						
						
						StringBuilder sb = new StringBuilder();
						sb.append("http://www.hidmet.gov.rs/latin/prognoza/");
						sb.append(href.attr("href"));
						
						
						try {
							doc = Jsoup.connect(sb.toString()).get();
							Elements tbodyRowsMaxTemp = doc.select("div#sadrzaj div table  tbody tr").get(1).select("td");
	  						Elements tbodyRowsMinTemp = doc.select("div#sadrzaj div table  tbody tr").get(3).select("td");
	  						Elements tbodyRowsImage = doc.select("div#sadrzaj div table  tbody tr").get(5).select("td");
	  						Elements theadRows = doc.select("div#sadrzaj div table  thead").get(0).select("tr th");
	  						
	  						List<FiveDayForecastModel> listFiveDayForecast = new ArrayList<FiveDayForecastModel>();
	  						
	  						for(int i=2; i<theadRows.size()-1; i++){
	  							String[] stringForecastTimestamp = theadRows.get(i).text().split(" ");
	  							StringBuilder sbu = new StringBuilder();
	  							sbu.append(stringForecastTimestamp[1]);
	  							sbu.append(calendar.get(Calendar.YEAR));
	  							Date parseForecastDate = formatter.parse(sbu.toString());
	  							
	  							ForecastDateModel forecastDate = forecastDateRepository.findByForecastDate(parseForecastDate);
	  							
	  							if(forecastDate == null){
	  								ForecastDateModel forecastDateModel = new ForecastDateModel();
	  								forecastDateModel.setForecastDate(parseForecastDate);
	  								forecastDateRepository.save(forecastDateModel);
	  								forecastDate = forecastDateRepository.findByForecastDate(parseForecastDate);
	  							}
	  								
	  							
	  							FiveDayForecastModel fiveDayForecastModel = new FiveDayForecastModel();
	  							fiveDayForecastModel.setCityId(city);	
	  							fiveDayForecastModel.setDateId(forecastDate);
	  							
	  							if(!tbodyRowsMinTemp.get(i).text().equals("-")){
	  								fiveDayForecastModel.setMinTemperature(Long.parseLong(tbodyRowsMinTemp.get(i).text()));
	  							}
	  							fiveDayForecastModel.setMaxTemperature(Long.parseLong(tbodyRowsMaxTemp.get(i).text()));
	  							fiveDayForecastModel.setActive((long) 1);
	  							fiveDayForecastModel.setImage(tbodyRowsImage.get(i).select("img").attr("src"));
	  							fiveDayForecastModel.setTableTime(tableTime);
	  							fiveDayForecastModel.setSyncTime(synchronizationTime);
	  							
	  							listFiveDayForecast.add(fiveDayForecastModel);
	  							
	  						}
	  						/** all in one update */
	  						fiveDayForecastRepository.save(listFiveDayForecast);
						}catch (IOException | ParseException e) {
							e.printStackTrace();							
						}	
						
					}
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("name", "Uspešno popunjena baza sa petodnevnom prognozom");
		model.addAttribute("forecast", "fiveday");
        return "hidmet";
    }
	
	@RequestMapping(value = "/populate_current_forecast")
	public String populateCurrentForecast(Model model) {
		
		Document doc;
		
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd.MM.yyyy");
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MINUTE, 15); 
		calendar.set(Calendar.SECOND, 0);
		
		Date synchronizationTime = calendar.getTime();
		
		boolean isSyncTyme = currentForecastRepository.existsByActiveAndSyncTime(1l, synchronizationTime);
		if(!isSyncTyme){
			try {
				
				doc = Jsoup.connect("http://www.hidmet.gov.rs/latin/osmotreni/index.php").get();
				
				Elements tbodyRows = doc.select("table tbody").get(0).select("tr");
				String[] tableTimestamp = doc.select("table tfoot tr td").get(0).text().split("\u00a0");
				Date tableTime = formatter.parse(tableTimestamp[2]);
				boolean isTableTime = currentForecastRepository.existsByActiveAndTableTime(1l, tableTime);
				if(!isTableTime){
					
					List<CurrentForecastModel> listCurrentForecasts = currentForecastRepository.findByActive(1l);
					
					for(CurrentForecastModel currentForecastModel : listCurrentForecasts){
						currentForecastModel.setActive(0l);						
					}
					
					currentForecastRepository.save(listCurrentForecasts);
					
					List<CurrentForecastModel> listCurrentForecastsModel = new ArrayList<CurrentForecastModel>();
					
					for (int i = 0; i < tbodyRows.size(); i++) {
						Elements tdRows = tbodyRows.get(i).select("tr").select("td");
						CityModel city = cityRepository.findByCityName(tdRows.get(0).text());
						
						if(city == null){
							city = new CityModel();
							city.setCityName(tdRows.get(0).text());
							cityRepository.save(city);
							city = cityRepository.findByCityName(tdRows.get(0).text());
						}
						if(tdRows.size() == 9){
							CurrentForecastModel currentForecastModel = new CurrentForecastModel();
							currentForecastModel.setCity(city);
							
							currentForecastModel.setTemperature(Long.parseLong(tdRows.get(1).text()));
							currentForecastModel.setPresure(Long.parseLong(tdRows.get(2).text().replace("\u00a0", "")));
							currentForecastModel.setWindDirection(tdRows.get(3).text());
							currentForecastModel.setWindSpeed(tdRows.get(4).text().replace("\u00a0", ""));
							currentForecastModel.setHumidity(Long.parseLong(tdRows.get(5).text().replace("\u00a0", "")));
							currentForecastModel.setFeelsLike(Long.parseLong(tdRows.get(6).text().replace("\u00a0", "")));
							currentForecastModel.setImage(tdRows.get(7).select("img").attr("src"));
							currentForecastModel.setDescription(tdRows.get(8).text());
							
							currentForecastModel.setSyncTime(synchronizationTime);
							currentForecastModel.setTableTime(tableTime);
							currentForecastModel.setActive((long) 1);
							listCurrentForecastsModel.add(currentForecastModel);
						}
					}
					currentForecastRepository.save(listCurrentForecastsModel);
				}
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("name", "Uspešno popunjena baza trenutnom prognozom");
		model.addAttribute("forecast", "current");
        return "hidmet";		
	}
	
	@RequestMapping(value = "/populate_short_term_forecast")
	public String populateShortTermForecast(Model model) {
		Document doc;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. hh:mm:ss.");
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MINUTE, 0); 
		calendar.set(Calendar.SECOND, 0);

		Date synchronizationTime = calendar.getTime();
		boolean isSyncTyme = shortTermForecastRepository.existsByActiveAndSyncTime(1l, synchronizationTime);
		if(!isSyncTyme){
			try {
				doc = Jsoup.connect("http://www.hidmet.gov.rs/latin/prognoza/index.php").get();

				Element table = doc.select("table").get(0); // select the first table.
				Element thead = table.select("thead tr").get(0);
				Element tfoot = table.select("tfoot tr td").get(0);
				Element tbody = table.select("tbody").get(0);
				String[] stringForecastTimestamp = tfoot.text().split("\u00a0");
				Date tableTime = formatter.parse(stringForecastTimestamp[1]);
				Map<Integer, Date> listStringDates = new HashMap<Integer, Date>();
				boolean isTableTyme = shortTermForecastRepository.existsByActiveAndTableTime(1l, tableTime);
				if(!isTableTyme){
					List<ShortTermForecastModel> listShortTermForecast = shortTermForecastRepository.findByActive(1l);
					for(ShortTermForecastModel shortTermForecast : listShortTermForecast ){
						shortTermForecast.setActive(0l);
					}					
					
					Elements theadRows = thead.select("th");

					for (int i = 1; i < theadRows.size(); i++) { // first row is  the col names so skip it.

						Element th = theadRows.get(i);
						String[] stringDate = th.text().split("\u00a0\u00a0");

						formatter = new SimpleDateFormat("dd.MM.yyyy");
						Date parseForecastDate = formatter.parse(stringDate[1]);
						listStringDates.put(i, parseForecastDate);
						ForecastDateModel forecastDate = forecastDateRepository.findByForecastDate(parseForecastDate);
						if(forecastDate == null){
							ForecastDateModel forecastDateModel = new ForecastDateModel();
							forecastDateModel.setForecastDate(parseForecastDate);
							forecastDateRepository.save(forecastDateModel);
							forecastDate = forecastDateRepository.findByForecastDate(parseForecastDate);
						}

					}
					
					Elements tbodyRows = tbody.select("tr");
					
					for (int i = 0; i < tbodyRows.size(); i++) {

						Element tr = tbodyRows.get(i);
						Elements tdRows = tr.select("td");

						int tdSize = tdRows.size();

						Long minTemp = null;
						Long maxTemp = null;
						CityModel city = null;
						
						for (int j = 0; j < tdSize; j++) {
							Element td = tdRows.get(j);

							if (j == 0) {
								
								city = cityRepository.findByCityName(td.text());
								if(city == null){
									city = new CityModel();
									city.setCityName(td.text());
									cityRepository.save(city);
									city = cityRepository.findByCityName(td.text());
								}

							} else {
								switch (j % 3) {

								case 0:
									minTemp = Long.parseLong(td.html());
									break;
								case 1:
									maxTemp = Long.parseLong(td.html());
									break;
								case 2:
									ForecastDateModel forecastDate = forecastDateRepository.findByForecastDate(listStringDates.get(j / 3 + 1));
									ShortTermForecastModel shortTermForecastModel = new ShortTermForecastModel();
									shortTermForecastModel.setTableTime(tableTime);
									shortTermForecastModel.setActive((long) 1);
									if (minTemp != null) {
										shortTermForecastModel.setMinTemperature(minTemp);
									}
									shortTermForecastModel.setMaxTemperature(maxTemp);
									shortTermForecastModel.setCityId(city);
									shortTermForecastModel.setDateId(forecastDate);
									shortTermForecastModel.setImage(td.select("img").attr("src"));
									shortTermForecastModel.setSyncTime(synchronizationTime);
									listShortTermForecast.add(shortTermForecastModel);
									break;

								}
							}
						}
					}
					shortTermForecastRepository.save(listShortTermForecast);
				}
			}catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("name", "Uspešno popunjena baza kratkoročnom prognozom");
		model.addAttribute("forecast", "shortterm");
		return "hidmet";		
	}
}
