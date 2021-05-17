package com.lob.app.url.domain.service;

import com.lob.app.url.domain.UrlEntity;
import com.lob.app.url.domain.UrlRepository;
import com.lob.app.url.domain.model.Url;
import lombok.RequiredArgsConstructor;
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
	private final RedisTemplate<String, String> redisTemplate;

	@Transactional
	public String createUrl(Url url) {

		// redis 에 shortUrl : TargetUrl 형식으로 저장.
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

		String shortUrl = urlRepository.save(mapper.toEntity(url, encode(encrypt(url.getTargetUrl()))))
				.getShortUrl();

		String savedUrl = valueOperations.get(url.getShortUrl());
		if (ObjectUtils.isEmpty(savedUrl)){
			valueOperations.append(url.getShortUrl(), url.getTargetUrl());
			return shortUrl;
		}

		UrlEntity entity = urlRepository.findByShortUrl(shortUrl);
		entity.incrementCount();
		return shortUrl;
	}

	@Transactional
	public String redirectUrl(Url url) {

		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		UrlEntity entity = urlRepository.findByShortUrl(url.getShortUrl());
		entity.incrementCount();

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
