package com.pccth.edlicense.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.pccth.edlicense.model.audit.Audit;

import lombok.Data;
@Entity(name = "License")
@Table(name = "license")
@Data
public class License extends Audit {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    @Column(unique = true)
	private String licenseId;
	
	@ManyToOne(
			fetch = FetchType.LAZY, 
			optional = false)
    @JoinColumn(
    		name = "bussiness_id", 
    		nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Bussiness bussiness;
	
	@OneToOne(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL)
	public ProductType productType;
	
	private Date startLicenseDate;
	
	private Date endLicenseDate;
	
	public Boolean isAvaiable() { 
		Date today = new Date(); 
		return today.before(this.getEndLicenseDate()); 
	}
	
	public String isExpired() {
		return this.isAvaiable()? "on License": "Expired";
	}
	 
	@Override
	public String toString() {
		return "Owner name:" + this.getBussiness().getOwner().getName() +
				" Bussiness name: " + this.getBussiness().getName() + 
				" License ID: " + this.getLicenseId() + 
				" License Type: " + this.getProductType().getName();
	}
}
