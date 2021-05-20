package com.lob.app.url.domain.persistence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "URL")
public class UrlEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "URL_ID")
	private Long id;

	@Column(name = "SHORT_URL", nullable = false, length = 8, unique = true)
	private String shortUrl;

	@Column(name = "TARGET_URL", nullable = false, unique = true)
	private String targetUrl;
	@Column(name = "REQUEST_COUNT")
	private Long count;

	public void incrementCount() {
		++count;
	}

	@Builder
	public UrlEntity(Long id, String shortUrl, String targetUrl, Long count) {
		this.id = id;
		this.shortUrl = shortUrl;
		this.targetUrl = targetUrl;
		this.count = count;
	}
}
