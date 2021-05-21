package com.lob.app.url.domain.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lob.app.global.utils.Base62Utils.encode;
import static com.lob.app.global.utils.XorEncryptUtils.encrypt;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

	private static final String regex = "^[0-9A-Za-z]{8}$";

	@ParameterizedTest
	@ValueSource(strings = {
			"https://jojoldu.tistory.com/266",
			"https://lob-dev.tistory.com/",
			"https://www.naver.com/",
			"https://www.daum.net/",
			"https://www.google.com/",
			"https://zum.com/"
	})
	void Short_URL_생성_테스트(String targetUrl) {

		String encodedString = encode(encrypt(targetUrl));
		assertTrue(encodedString.matches(regex));
	}
}