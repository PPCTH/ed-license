package com.pccth.edlicense.model;

import java.util.Optional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pccth.edlicense.model.audit.Audit;

import lombok.Data;

@Entity(name = "Bussiness")
@Table(name = "bussiness")
@Data
public class Bussiness extends Audit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    @Column(unique = true)
	private String name;
	
	@NotNull
	private boolean status = false;
	
	@ManyToOne(
			fetch = FetchType.LAZY, 
			optional = false)
    @JoinColumn(
    		name = "owner_id", 
    		nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Owner owner;

	@Override
	public String toString() {
		
		return "Bussiness Name: " + this.name 
				+ " status " + this.isStatus() 
				+ " hase owner " + this.owner.getName();
	};
}
