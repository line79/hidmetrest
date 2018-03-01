package com.sargije.rest.hidmet.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sargije.rest.hidmet.app.model.ShortTermForecastModel;

@Transactional
public interface ShortTermForecastRepository extends CrudRepository<ShortTermForecastModel, Long>{	
	List<ShortTermForecastModel> findByActive(Long active);
	boolean existsByActiveAndTableTime(Long active, Date tableTime);
	boolean existsByActiveAndSyncTime(Long active, Date syncTime);
}
