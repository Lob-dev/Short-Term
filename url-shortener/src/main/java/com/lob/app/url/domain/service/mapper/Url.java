package com.lob.app.url.domain.service.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Url {

	private final String targetUrl;
	private final String shortUrl;
	private final Long   requestCount;

}
