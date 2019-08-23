package com.demo.ms.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(schema="oauth")
@Entity
public class Authorities implements Serializable{

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
    private Long id;
	
	@Column
	private String role;
	
	@Column
	private Boolean status;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(columnDefinition="authority_id", referencedColumnName="id")
	private Authorities authoritie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	

}
