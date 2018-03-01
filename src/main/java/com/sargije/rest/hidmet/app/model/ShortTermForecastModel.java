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
@Table(schema="hidmet", name="prognoza")
public class ShortTermForecastModel {
	
   public ShortTermForecastModel() {}
	
   public ShortTermForecastModel(Long forecastId, Long maxTemperature, Long minTemperature,
		   Long active,  Date tableTime, Date syncTime ) {
      this.forecastId = forecastId;
      this.maxTemperature = maxTemperature;
      this.minTemperature = minTemperature;
      this.active = active;
      this.tableTime = tableTime;
      this.syncTime = syncTime;
   }
	
	@Id
	@GeneratedValue
	@Column(name="p_id")
	private Long forecastId;

	@Column(name="p_max_temp")
	private Long maxTemperature;
	
	@Column(name="p_min_temp")
	private Long minTemperature;
	
	@Column(name="p_active")
	private Long active;
	
	@Column(name="p_table_time")
	private Date tableTime;
	
	@Column(name="p_sync_time")
	private Date syncTime;	
	
	@Column(name="p_image")
	private String image;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="p_g_id", referencedColumnName="g_id")
	private CityModel cityId;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="p_d_id", referencedColumnName="d_id")
	private ForecastDateModel dateId;
	
	public Long getForecastId() {
		return forecastId;
	}

	public void setForecastId(Long forecastId) {
		this.forecastId = forecastId;
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

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

}
