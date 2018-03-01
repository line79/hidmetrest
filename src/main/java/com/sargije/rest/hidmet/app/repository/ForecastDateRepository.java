package com.sargije.rest.hidmet.app.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sargije.rest.hidmet.app.model.ForecastDateModel;

@Transactional
public interface ForecastDateRepository extends CrudRepository<ForecastDateModel, Long>{	
	ForecastDateModel findByForecastDate(Date date);
}
