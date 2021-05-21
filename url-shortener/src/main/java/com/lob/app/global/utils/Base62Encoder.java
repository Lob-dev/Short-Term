package com.lob.app.global.utils;

import io.seruco.encoding.base62.Base62;

import java.nio.charset.StandardCharsets;

public class Base62Encoder implements Encoder{

	@Override
	public String encode(String inputString) {
		Base62 encoder = Base62.createInstance();
		return new String(encoder.encode(inputString.getBytes(StandardCharsets.UTF_8))).subSequence(0, 8)
				.toString();
	}

}
