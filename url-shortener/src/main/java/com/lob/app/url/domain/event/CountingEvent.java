package com.lob.app.url.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountingEvent {

	private final String shortUrl;

}
