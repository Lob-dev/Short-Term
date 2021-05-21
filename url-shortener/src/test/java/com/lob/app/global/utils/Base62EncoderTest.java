package com.lob.app.global.utils;

import io.seruco.encoding.base62.Base62;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class Base62EncoderTest {

	@Test
	void Base_62_인코딩_테스트() {

		Base62 encoder = Base62.createInstance();

		String regex = "^[0-9A-Za-z]{8}$";
		String testString = "?>2adas_-bv?//";

		String encodedString = new String(encoder
											.encode(testString
														.getBytes(StandardCharsets.UTF_8)))
											.substring(0, 8);

		assertTrue(encodedString.matches(regex));
	}

}