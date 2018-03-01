package com.sargije.rest.hidmet.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema="hidmet", name="nedeljna")
public class FiveDayForecastModel {
	
	@Id
	@GeneratedValue
	@Column(name="n_id")
	private Long fiveDayForecastId;

	@Column(name="n_max_temp")
	private Long maxTemperature;
	
	@Column(name="n_min_temp")
	private Long minTemperature;
	
	@Column(name="n_active")
	private Long active;
	
	@Column(name="n_table_time")
	private Date tableTime;
	
	@Column(name="n_sync_time")
	private Date syncTime;	
	
	@Column(name="n_image")
	private String image;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="n_g_id", referencedColumnName="g_id")
	private CityModel cityId;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="n_d_id", referencedColumnName="d_id")
	private ForecastDateModel dateId;

	public Long getFiveDayForecastId() {
		return fiveDayForecastId;
	}

	public void setFiveDayForecastId(Long fiveDayForecastId) {
		this.fiveDayForecastId = fiveDayForecastId;
	}

	public Long getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(Long maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public Long getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(Long minTemperature) {
		this.minTemperature = minTemperature;
	}

	public Long getActive() {
		return active;
	}

	public void setActive(Long active) {
		this.active = active;
	}

	public Date getTableTime() {
		return tableTime;
	}

	public void setTableTime(Date tableTime) {
		this.tableTime = tableTime;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CityModel getCityId() {
		return cityId;
	}

	public void setCityId(CityModel cityId) {
		this.cityId = cityId;
	}

	public ForecastDateModel getDateId() {
		return dateId;
	}

	public void setDateId(ForecastDateModel dateId) {
		this.dateId = dateId;
	}
	
}
