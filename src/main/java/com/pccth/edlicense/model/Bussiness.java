package com.pccth.edlicense.model;

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
	private boolean status = false;
	
	@ManyToOne(
			fetch = FetchType.LAZY, 
			optional = false)
    @JoinColumn(
    		name = "owner_id", 
    		nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Owner owner;
}
