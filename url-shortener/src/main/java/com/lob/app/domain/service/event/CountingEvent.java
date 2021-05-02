package com.lob.app.domain.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountingEvent {

	private final String shortUrl;

}
