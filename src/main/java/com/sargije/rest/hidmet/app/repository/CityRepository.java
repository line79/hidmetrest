package com.sargije.rest.hidmet.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sargije.rest.hidmet.app.model.CityModel;

@Transactional
public interface CityRepository extends CrudRepository<CityModel, Long>{	
	CityModel findByCityName(String cityName);
}
