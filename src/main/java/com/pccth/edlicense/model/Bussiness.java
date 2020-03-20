package com.pccth.edlicense.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
	}
)
@Data
public class Bussiness extends Audit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    @Column(unique = true)
	private String name;
	
	
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
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "bussiness")
    private Set<License> license;
	
	
	public Map getDetail() {
		Map<String, Serializable> detail = new HashMap<>();
		detail.put("owner_lincense_id", owner.getLicenseId());
		detail.put("owner_name", owner.getName());
		detail.put("bussiness_name", this.getName());
		detail.put("address", address.getName());
//		detail.put("license_id",license);
		return detail;
	}
	
	@Override
	public String toString() {
		return "Bussiness Name: " + this.name 
				+ " hase owner " + this.owner.getName()
				+ " at "+ this.address.getName();
	};
}
