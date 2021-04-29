package com.lob.app.domain.service.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
public class UrlEventListener {

	@EventListener
	public void countIncrease(CountingEvent countingEvent) {
		// go to redis
	}

}
