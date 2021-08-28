package com.lob.app.url.domain.service.mapper;

import com.lob.app.global.utils.Encoder;
import lombok.Builder;
import lombok.Getter;

import static com.lob.app.global.utils.XorEncryptUtils.encrypt;

@Getter
public class Url {

	private String shortUrl;
	private final String targetUrl;
	private Long requestCount;

	public Url generateShortUrl(Encoder encoder) {
		this.shortUrl = encoder.encode(encrypt(this.targetUrl));
		return this;
	}

	public void initialCount() {
		this.requestCount = 0L;
	}

	public static Url from(Url url, String targetUrl) {
		return Url.builder()
				.shortUrl(url.getShortUrl())
				.targetUrl(targetUrl)
				.build();
	}

	public static Url from(String shortUrl, Long requestCount) {
		return Url.builder()
				.shortUrl(shortUrl)
				.requestCount(requestCount)
				.build();
	}

	@Builder
	public Url(String targetUrl, String shortUrl, Long requestCount) {
		this.targetUrl = targetUrl;
		this.shortUrl = shortUrl;
		this.requestCount = requestCount;
	}
}
