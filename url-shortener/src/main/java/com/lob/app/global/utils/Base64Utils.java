package com.lob.app.global.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Utils {

	public static String encode(String inputString) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(inputString.getBytes(StandardCharsets.UTF_8));
	}

}
