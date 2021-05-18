package com.lob.app.url.domain.service;

import com.lob.app.url.domain.UrlEntity;
import com.lob.app.url.domain.UrlRepository;
import com.lob.app.url.domain.event.CountingEvent;
import com.lob.app.url.domain.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import static com.lob.app.global.utils.Base62Utils.encode;
import static com.lob.app.global.utils.XorEncryptUtils.encrypt;
import static com.lob.app.url.domain.UrlMapper.mapper;

@Service
@RequiredArgsConstructor
public class UrlService {

	private final UrlRepository             urlRepository;
	private final ApplicationEventPublisher eventPublisher;
	private final RedisTemplate<String, String> redisTemplate;

	@Transactional
	public String createUrl(Url url) {

		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

		Url buildUrl = Url.builder()
				.shortUrl(encode(encrypt(url.getTargetUrl())))
				.targetUrl(url.getTargetUrl())
				.requestCount(0L)
				.build();
		String createUrl = buildUrl.getShortUrl();

		UrlEntity buildEntity = mapper.toEntity(buildUrl);
		urlRepository.save(buildEntity);

		String savedUrl = valueOperations.get(createUrl);
		if (ObjectUtils.isEmpty(savedUrl)){
			valueOperations.append(createUrl, buildUrl.getTargetUrl());
			return createUrl;
		}

		eventPublisher.publishEvent(new CountingEvent(createUrl));
		return createUrl;
	}

	@Transactional
	public String redirectUrl(Url url) {

		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		eventPublisher.publishEvent(new CountingEvent(url.getShortUrl()));
		return valueOperations.get(url.getShortUrl());
	}

	@Transactional(readOnly=true)
	public Url getUrl(Url url) {

		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String targetUrl = valueOperations.get(url.getShortUrl());

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