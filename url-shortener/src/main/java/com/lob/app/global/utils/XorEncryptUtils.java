package com.lob.app.global.utils;

public class XorEncryptUtils {

	public static String encrypt(String inputString) {
		char xorKey = 'P';

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < inputString.length(); i++) {
			stringBuilder.append(Character.toString(inputString.charAt(i) ^ xorKey));
		}

		return stringBuilder
				.subSequence(0, 7)
				.toString();
	}
}
