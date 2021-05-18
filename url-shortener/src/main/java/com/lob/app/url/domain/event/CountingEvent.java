package com.lob.app.url.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CountingEvent {

	private final String shortUrl;

}
