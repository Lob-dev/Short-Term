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
		// shortUrl : targetUrl
			// save
	}

}
