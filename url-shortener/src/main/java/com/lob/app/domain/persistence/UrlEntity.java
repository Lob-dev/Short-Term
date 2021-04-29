package com.lob.app.domain.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "URL")
public class UrlEntity {

	@Id
	@GeneratedValue
	@Column(name = "URL_ID")
	private Long id;

}
