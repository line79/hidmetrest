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
@Table(schema="hidmet", name="aktuelno")
public class CurrentForecastModel {
	
	@Id
	@GeneratedValue
	@Column(name="a_id")
	private Long currentWeatherId;

	@Column(name="a_temp")
	private Long temperature;
	
	@Column(name="a_presure")
	private Long presure;
	
	@Column(name="a_wind_direction")
	private String windDirection;

	@Column(name="a_wind_speed")
	private String windSpeed;
	
	@Column(name="a_humidity")
	private Long humidity;
	
	@Column(name="a_feels_like")
	private Long feelsLike;
	
	@Column(name="a_image")
	private String image;
	
	@Column(name="a_description")
	private String description;
	
	@Column(name="a_table_time")
	private Date tableTime;
	
	@Column(name="a_sync_time")
	private Date syncTime;	
	
	@Column(name="a_active")
	private Long active;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="a_g_id", referencedColumnName="g_id")
	private CityModel city;

	public Long getCurrentWeatherId() {
		return currentWeatherId;
	}

	public void setCurrentWeatherId(Long currentWeatherId) {
		this.currentWeatherId = currentWeatherId;
	}

	public Long getTemperature() {
		return temperature;
	}

	public void setTemperature(Long temperature) {
		this.temperature = temperature;
	}

	public Long getPresure() {
		return presure;
	}

	public void setPresure(Long presure) {
		this.presure = presure;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Long getHumidity() {
		return humidity;
	}

	public void setHumidity(Long humidity) {
		this.humidity = humidity;
	}

	public Long getFeelsLike() {
		return feelsLike;
	}

	public void setFeelsLike(Long feelsLike) {
		this.feelsLike = feelsLike;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getActive() {
		return active;
	}

	public void setActive(Long active) {
		this.active = active;
	}

	public CityModel getCity() {
		return city;
	}

	public void setCity(CityModel city) {
		this.city = city;
	}

}
