package com.lob.app.url.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Url {

	private final String targetUrl;
	private final String shortUrl;
	private final Long   requestCount;

}
