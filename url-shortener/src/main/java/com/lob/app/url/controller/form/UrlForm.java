package com.lob.app.url.controller.form;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlForm {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Request {

		@Getter
		@NoArgsConstructor
		public static class Create {

			@URL(message = "올바른 URL 형식이 아닙니다.")
			@NotBlank
			private String targetUrl;

		}

		@Getter
		@NoArgsConstructor
		public static class GetCount {

			@NotBlank
			private String shortUrl;

		}
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Response {

		@Getter
		@Builder
		public static class Info {

			private final String shortUrl;

		}

		@Getter
		@Builder
		public static class CountInfo {

			private final String shortUrl;
			private final Integer requestCount;

		}

		@Getter
		@Builder
		public static class UrlInfo {

			private final String shortUrl;
			private final String targetUrl;

		}
	}
}
