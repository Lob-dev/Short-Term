package com.lob.app.url.domain.service;

import com.lob.app.global.utils.XorEncryptUtils;
import com.lob.app.url.domain.UrlEntity;
import com.lob.app.url.domain.UrlRepository;
import com.lob.app.url.domain.event.CountingEvent;
import com.lob.app.url.domain.event.CreateEvent;
import com.lob.app.url.domain.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lob.app.url.domain.UrlMapper.mapper;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {

	private final UrlRepository             urlRepository;
	private final ApplicationEventPublisher eventPublisher;

	public String createUrl(Url url) {

		UrlEntity entity = urlRepository.save(mapper.toEntity(url, XorEncryptUtils.encrypt(url.getTargetUrl())));

		// redis 에 shortUrl : counting 형식으로 저장.
		eventPublisher.publishEvent(new CreateEvent(url.getShortUrl()));

		// 생성된 단축 url 반환
		return entity.getShortUrl();
	}

	public String redirectUrl(Url url) {

		// counting
		eventPublisher.publishEvent(new CountingEvent(url.getShortUrl()));

		// redis 에서 get 후 반환
		// shortUrl find -> get targetUrl
		return "targetUrl";
	}

	@Transactional(readOnly=true)
	public UrlEntity getUrl(Url url) {
		return urlRepository.findByShortUrl(url.getShortUrl());
	}

	@Transactional(readOnly=true)
	public Url getCount(Url url) {

		// redis 에서 url 정보로 get 하여 카운팅 정보 반환
		// RedisTemplate<String, Long> redis;
		Long count = 0L;

		return Url.builder()
				.shortUrl(url.getShortUrl())
				.requestCount(count)
				.build();
	}
}
