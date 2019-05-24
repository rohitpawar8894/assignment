package com.uber.assignment.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
public class IdentityDetails {
	
	@Id
	@Column(name="IDENTITY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
	
	@NotNull
	private String uid;
	
	private Type type;
	
	private Status status;
	
	private String url; // aws bucket URL
	
	enum Type {
		@JsonProperty("LICENCE")
		LICENCE("LICENCE"), 
		
		@JsonProperty("AADHAR")
		AADHAR("AADHAR");
		
		private String text;

		Type(String text) {
			this.text=text;
		}

		public String getText() {
			return text;
		}
	}
	
	enum Status {
		@JsonProperty("ACTIVE")
		ACTIVE("ACTIVE"), 
		
		@JsonProperty("INACTIVE")
		INACTIVE("INACTIVE");
	
		private String text;

		Status(String text) {
			this.text=text;
		}

		public String getText() {
			return text;
		}
	}
	
}
