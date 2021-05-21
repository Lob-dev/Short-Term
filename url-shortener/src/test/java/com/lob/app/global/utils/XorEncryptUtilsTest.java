package com.lob.app.global.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XorEncryptUtilsTest {

	@Test
	void XorEncrypt_테스트() {

		String testString = "dasfasfeeeqfga";
		String encrypt = XorEncryptUtils.encrypt(testString);

		assertNotEquals(testString, encrypt);
	}

}