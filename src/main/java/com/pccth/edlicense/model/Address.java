package com.pccth.edlicense.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity(name = "Address")
@Table(name = "address")
@Data
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	private String address;
	

	@NotNull
	private String subDistrict;
	
	@NotNull
	private String district;
	
	@NotNull
	private String province;
	
	@NotNull
	private String postCode;
	
	@OneToOne(mappedBy = "address")
	private Bussiness bussiness;
	
	
	@Override
	public String toString() {
		String address = this.getAddress() + " " + 
						this.getSubDistrict() + " " + 
						this.getDistrict() + " " + 
						this.getProvince() + " " + 
						this.getPostCode();
		return address;
	}
	
}
