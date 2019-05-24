package com.uber.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Documents {
	
	@Id
	@Column(name="DOCUMENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String description;
	
	private Type type;
	
	private Status status;
	
	private String url; // aws bucket URL
	
	enum Type {
		RC_BOOK("RC_BOOK"), INSUARANCE("INSUARANCE");
		
		private String text;

		Type(String text) {
			this.text=text;
		}

		public String getText() {
			return text;
		}
	}
	
	enum Status {
		ACTIVE("ACTIVE"), INACTIVE("INACTIVE");
	
		private String text;

		Status(String text) {
			this.text=text;
		}

		public String getText() {
			return text;
		}
	}
}
