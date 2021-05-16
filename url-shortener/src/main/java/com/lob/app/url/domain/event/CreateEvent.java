package com.lob.app.url.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateEvent {

	private final String shortUrl;
	private final String targetUrl;

}
