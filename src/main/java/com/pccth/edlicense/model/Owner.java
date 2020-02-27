package com.pccth.edlicense.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.pccth.edlicense.model.audit.Audit;

import lombok.Data;

@Entity(name = "Owner")
@Table(name = "owner")
@Data
public class Owner extends Audit{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    @Column(unique = true)
	private String licenseId;
	
	@NotNull
    @Column(unique = true)
	private String name;

	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "owner")
    private Set<Bussiness> bussiness;
	
	@Override
	public String toString() {
		return "Owner ID : " + this.id
				+ " Name: " + this.name;
	}
	
	public Owner() {};
	
	public Owner(Long id, String name){
		this.id = id;
		this.name = name;
	}
}
