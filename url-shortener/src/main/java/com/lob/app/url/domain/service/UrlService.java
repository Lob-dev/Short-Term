package com.lob.app.url.domain.service;

import com.lob.app.global.utils.Base62Encoder;
import com.lob.app.url.domain.event.CountingEvent;
import com.lob.app.url.domain.exception.NoSuchURLException;
import com.lob.app.url.domain.persistence.UrlEntity;
import com.lob.app.url.domain.persistence.UrlRepository;
import com.lob.app.url.domain.service.mapper.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lob.app.url.domain.service.mapper.UrlMapper.mapper;
import static org.springframework.util.ObjectUtils.*;

@Service
@RequiredArgsConstructor
public class UrlService {

	private final UrlRepository             urlRepository;
	private final ApplicationEventPublisher eventPublisher;
	private final RedisTemplate<String, String> redisTemplate;

	public String createUrl(Url url) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		url.generateShortUrl(new Base62Encoder()).initialCount();

		String createUrl = url.getShortUrl();
		if (isEmpty(valueOperations.get(createUrl))) {
			urlRepository.save(mapper.toEntity(url));
			valueOperations.append(createUrl, url.getTargetUrl());
		}
		return createUrl;
	}

	public String redirectUrl(Url url) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

		String savedUrl = valueOperations.get(url.getShortUrl());
		if (isEmpty(savedUrl)) { throw new NoSuchURLException("URL Not Found"); }

		eventPublisher.publishEvent(new CountingEvent(url.getShortUrl()));
		return valueOperations.get(url.getShortUrl());
	}

	public Url getUrl(Url url) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

		String targetUrl = valueOperations.get(url.getShortUrl());
		if (isEmpty(targetUrl)) { throw new NoSuchURLException("URL Not Found"); }

		return Url.from(url, targetUrl);
	}

	@Transactional(readOnly=true)
	public Url getCount(Url url) {
		UrlEntity entity = urlRepository.findByShortUrl(url.getShortUrl());
		if (isEmpty(entity)) { throw new NoSuchURLException("URL Not Found"); }

		return Url.from(url.getShortUrl(), entity.getCount());
	}
}
