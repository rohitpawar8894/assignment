package com.uber.assignment.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class DriverDetail {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String locality;
	
	// If we want to use foreign Key into tables instead of intermediate table;
	/*@OneToMany(cascade = CascadeType.ALL)  
	@JoinColumn(name="driver_id")  
	@OrderColumn(name="type")  */
	
	
	@OneToMany(cascade = CascadeType.ALL) 
	@JoinTable( name="DRIVER_IDENTITY", 
    joinColumns=@JoinColumn(name="ID"), 
    inverseJoinColumns=@JoinColumn(name="IDENTITY_ID"))
	private List<IdentityDetails> identityDetails;
	
	
	@OneToMany(cascade = CascadeType.ALL) 
	@JoinTable( name="DRIVER_DOCUMENT", 
    joinColumns=@JoinColumn(name="ID"), 
    inverseJoinColumns=@JoinColumn(name="DOCUMENT_ID"))
	private List<Documents> documents;
	
	
	public void addIdentityDetails(IdentityDetails identity) {
		  if (identityDetails == null) {
			  identityDetails = new ArrayList<IdentityDetails>();
		  }
		  identityDetails.add(identity);	
	}
	
	public void addDocuments(Documents document){
		if(documents==null){
			documents = new ArrayList<Documents>();
		}
		documents.add(document);
	}
	
}
