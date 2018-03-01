package com.sargije.rest.hidmet.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sargije.rest.hidmet.app.model.CurrentForecastModel;


@Transactional
public interface CurrentForecastRepository extends CrudRepository<CurrentForecastModel, Long>{	
	 
	List<CurrentForecastModel> findByActive(Long active);	
	boolean existsByActiveAndTableTime(Long active, Date tableTime);
	boolean existsByActiveAndSyncTime(Long active, Date syncTime);

}
