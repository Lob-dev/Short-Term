package com.lob.app.url.domain.service;

import com.lob.app.url.domain.UrlEntity;
import com.lob.app.url.domain.UrlRepository;
import com.lob.app.url.domain.event.CreateEvent;
import com.lob.app.url.domain.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lob.app.global.utils.Base64Utils.encode;
import static com.lob.app.global.utils.XorEncryptUtils.encrypt;
import static com.lob.app.url.domain.UrlMapper.mapper;

@Service
@RequiredArgsConstructor
public class UrlService {

	private final UrlRepository             urlRepository;
	private final ApplicationEventPublisher eventPublisher;

	// private final RedisTemplate<String, String> redisTemplate;

	@Transactional
	public String createUrl(Url url) {

		// redis 에 shortUrl : TargetUrl 형식으로 저장.
		eventPublisher.publishEvent(new CreateEvent(url.getShortUrl(), url.getTargetUrl()));

		// 생성된 단축 url 반환
		return urlRepository.save(mapper.toEntity(url, encrypt(encode(url.getTargetUrl()))))
				.getShortUrl();
	}

	@Transactional
	public String redirectUrl(Url url) {

		// ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		// String targetUrl = valueOperations.get(url.getShortUrl());
		UrlEntity entity = urlRepository.findByShortUrl(url.getShortUrl());
		entity.incrementCount();

		return "targetUrl";
	}

	@Transactional(readOnly=true)
	public Url getUrl(Url url) {

		// ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		// String targetUrl = valueOperations.get(url.getShortUrl());
		String targetUrl = "";

		return Url.builder()
				.shortUrl(url.getShortUrl())
				.targetUrl(targetUrl)
				.build();
	}

	@Transactional(readOnly=true)
	public Url getCount(Url url) {

		UrlEntity entity = urlRepository.findByShortUrl(url.getShortUrl());

		return Url.builder()
				.shortUrl(entity.getShortUrl())
				.requestCount(entity.getCount())
				.build();
	}
}
