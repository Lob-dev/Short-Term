package com.lob.app.domain.service;

import com.lob.app.domain.persistence.UrlEntity;
import com.lob.app.domain.persistence.UrlRepository;
import com.lob.app.domain.service.event.CountIncreaseEvent;
import com.lob.app.domain.service.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {

	private final UrlRepository             urlRepository;
	private final ApplicationEventPublisher eventPublisher;

	public UrlEntity createUrl(Url url) {
		return new UrlEntity();
	}

	public UrlEntity redirectUrl(Url url) {
		// counting
		eventPublisher.publishEvent(new CountIncreaseEvent());
		return new UrlEntity();
	}

	public UrlEntity getUrl(Url url) {
		return new UrlEntity();
	}

	public UrlEntity getCount(Url url) {
		return new UrlEntity();
	}

}
