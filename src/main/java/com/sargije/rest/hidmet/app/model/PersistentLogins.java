package com.sargije.rest.hidmet.app.model;
// Generated Jun 14, 2017 10:17:23 PM by Hibernate Tools 5.2.3.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PersistentLogins generated by hbm2java
 */
@Entity
@Table(name = "persistent_logins", schema = "hidmet")
public class PersistentLogins implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8340980993971787034L;
	private String series;
	private String username;
	private String token;
	private Date lastUsed;

	public PersistentLogins() {
	}

	public PersistentLogins(String series, String username, String token, Date lastUsed) {
		this.series = series;
		this.username = username;
		this.token = token;
		this.lastUsed = lastUsed;
	}

	@Id

	@Column(name = "series", unique = true, nullable = false, length = 64)
	public String getSeries() {
		return this.series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	@Column(name = "username", nullable = false, length = 64)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "token", nullable = false, length = 64)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_used", nullable = false, length = 19)
	public Date getLastUsed() {
		return this.lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

}
