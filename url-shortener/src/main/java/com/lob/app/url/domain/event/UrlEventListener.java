package com.lob.app.url.domain.event;

import com.lob.app.url.domain.UrlEntity;
import com.lob.app.url.domain.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Async
@Component
@RequiredArgsConstructor
public class UrlEventListener {

	private final UrlRepository urlRepository;

	@EventListener
	@Transactional
	public void increaseCount(CountingEvent event) {
		UrlEntity entity = urlRepository.findByShortUrl(event.getShortUrl());
		entity.incrementCount();
	}
}