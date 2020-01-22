package com.pccth.edlicense.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pccth.edlicense.model.audit.Audit;

import lombok.Data;

@Entity(name = "Bussiness")
@Table(name = "bussiness",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = "name"),
		@UniqueConstraint(columnNames = "bussinessLicenseId")
	}
)
@Data
public class Bussiness extends Audit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull
    @Column(unique = true)
	private String bussinessLicenseId;
	
	@NotNull
    @Column(unique = true)
	private String name;
	
	/*
	 * @NotNull private boolean status = false;
	 */

	private Date startLicenseDate;
	
	private Date endLicenseDate;
	
	@ManyToOne(
			fetch = FetchType.LAZY, 
			optional = false)
    @JoinColumn(
    		name = "owner_id", 
    		nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Owner owner;

	@OneToOne(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL)
	@JoinColumn(unique = true)
	public Address address;
	
	@ManyToOne(
			fetch = FetchType.LAZY, 
			optional = false)
    @JoinColumn(
    		name = "product_type_id", 
    		nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NotNull
	public ProductType productType;
	
	public Boolean isAvaiable() {
		Date today = new Date();
		return today.before(this.getEndLicenseDate());
	}
	public String getStatus() {
		return this.isAvaiable()? "Aviable":"Expired"; 
		
	}
	
	
	public Map getDetail() {
		Map<String, Serializable> detail = new HashMap<>();
		detail.put("owner_lincense_id", owner.getLicenseId());
		detail.put("owner_name", owner.getName());
		detail.put("bussiness_license_id", this.getBussinessLicenseId());
		detail.put("bussiness_name", this.getName());
		detail.put("product_type", productType.getName());
		detail.put("address", address.getName());
		detail.put("bussiness_license_start", this.getStartLicenseDate());
		detail.put("bussiness_license_end", this.getEndLicenseDate());
		return detail;
	}
	
	@Override
	public String toString() {
		return "Bussiness Name: " + this.name 
				+ " status " + this.isAvaiable() 
				+ " hase owner " + this.owner.getName();
	};
}
