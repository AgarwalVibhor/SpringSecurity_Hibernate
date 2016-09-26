package com.tcs.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name = "users_hibernate", catalog = "test")
public class User {
	
	private String username;
	private String password;
	private boolean enabled;
	private Set<UserRole> userRoles;
	// This is a set containing objects of class that has the foreign key
	// Foreign key class will be involved inside the primary key class.
	
	/*
	 * 1. When the User object is made, it will contain not only username, password, enabled but also
	 * userRoles.
	 * 
	 * 2. However, in the "users_hibernate" table , only username, password and enabled will be there. This is 
	 * because we have made use of mapping annotations and Spring will automatically take care of primary key
	 * and foreign key.
	 */
	
	public User() {
		
	}

	public User(String username, String password, boolean enabled,
			Set<UserRole> userRoles) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRoles = userRoles;
	}
	
	@Id
	@Column(name = "username", unique = true, nullable = false, length = 64)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "password", nullable = false, length = 64)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // mappedby - by the table containing primary key.
	@Fetch(FetchMode.SELECT)
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	
	
	

}
