package com.lob.app.url.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
@RequiredArgsConstructor
public class UrlEventListener {

	private final RedisTemplate<String, String> redisTemplate;

	@EventListener
	public void create(CreateEvent createEvent) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.append(createEvent.getShortUrl(), createEvent.getTargetUrl());
	}
}
