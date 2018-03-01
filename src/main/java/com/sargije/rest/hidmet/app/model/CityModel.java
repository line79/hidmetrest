package com.sargije.rest.hidmet.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="grad")
public class CityModel {
	
	@Id
	@GeneratedValue
	@Column(name="g_id")
	private Long cityId;
	
	@Column(name="g_grad")
	private String cityName;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="cityId")
	private Set<CityModel> cityFK = new HashSet<CityModel>();
	
	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
		
}
