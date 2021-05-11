package com.lob.app.url.domain.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
public class UrlEventListener {

	@EventListener
	public void create(CreateEvent createEvent) {
		// set redis
		// shortUrl : count
			// save
	}

	@EventListener
	public void countIncrease(CountingEvent countingEvent) {
		// increase redis counting
		// shortUrl : count
			// increase count of shortUrl
	}

}
