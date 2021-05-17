package com.lob.app.url.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
		name = "URL",
		indexes = {
			@Index(name = "IDX_SHORT_URL", columnList = "SHORT_URL")
		}
)
public class UrlEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "URL_ID")
	private Long id;

	@Column(name = "SHORT_URL", nullable = false, length = 8, unique = true)
	private String shortUrl;

	@Column(name = "TARGET_URL", nullable = false)
	private String targetUrl;

	@Column(name = "REQUEST_COUNT")
	private Long count;

	public void incrementCount() {
		++count;
	}

}
