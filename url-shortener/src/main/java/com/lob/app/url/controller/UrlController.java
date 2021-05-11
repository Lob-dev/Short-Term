package com.lob.app.url.controller;

import com.lob.app.url.controller.form.UrlForm.Request;
import com.lob.app.url.controller.form.UrlForm.Response;
import com.lob.app.url.domain.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static com.lob.app.url.domain.UrlMapper.mapper;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UrlController {

	private final UrlService urlService;

	@PostMapping("/urls")
	public Response.Info createUrl(@Valid @RequestBody Request.Create create) {
		return mapper.toInfo(urlService.createUrl(mapper.toUrl(create)));
	}

	@GetMapping("/{shortUrl}")
	public ResponseEntity redirectUrl(@PathVariable String shortUrl, HttpHeaders headers) {
		headers.setLocation(URI.create(urlService.redirectUrl(mapper.toUrl(shortUrl))));
		return ResponseEntity.ok().headers(headers).build();
	}

	@GetMapping("/{shortUrl}/?")
	public Response.UrlInfo getUrlInfo(@PathVariable String shortUrl) {
		return mapper.toUrlInfo(urlService.getUrl(mapper.toUrl(shortUrl)));
	}

	@GetMapping("/{shortUrl}/count")
	public Response.CountInfo getCountInfo(@PathVariable String shortUrl) {
		return mapper.toCountInfo(urlService.getCount(mapper.toUrl(shortUrl)));
	}

}
