package com.lob.app.domain.service;

import com.lob.app.domain.persistence.UrlEntity;
import com.lob.app.domain.persistence.UrlRepository;
import com.lob.app.domain.service.event.CountingEvent;
import com.lob.app.domain.service.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lob.app.domain.mapper.UrlMapper.mapper;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {

	private final UrlRepository             urlRepository;
	private final ApplicationEventPublisher eventPublisher;

	public String createUrl(Url url) {

		// 단축 url 생성
			// 해싱 추가?
			// url 인스턴스에 세팅

		// url 저장
		urlRepository.save(mapper.toEntity(url));

		// redis 에 단축 url : 카운팅 저장

		// 생성된 단축 url 반환
		return "shortUrl";
	}

	public String redirectUrl(Url url) {

		// counting
		eventPublisher.publishEvent(new CountingEvent(url.getShortUrl()));

		// redis 에서 get 후 반환
		// shortUrl find -> get targetUrl
		return "targetUrl";
	}

	@Transactional(readOnly=true)
	public Url getUrl(Url url) {
		return mapper.toUrl(urlRepository.findByShortUrl(url.getShortUrl()));
	}

	@Transactional(readOnly=true)
	public Url getCount(Url url) {
		UrlEntity entity = urlRepository.findByShortUrl(url.getShortUrl());

		// redis 에서 get 하여 카운팅 정보 반환
		Long count = 0L;
		return mapper.toUrl(entity, count);
	}

}
