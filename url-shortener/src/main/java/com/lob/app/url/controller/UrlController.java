package com.lob.app.url.controller;

import com.lob.app.url.controller.form.UrlForm.Request;
import com.lob.app.url.controller.form.UrlForm.Response;
import com.lob.app.url.domain.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.net.URI;

import static com.lob.app.url.domain.service.mapper.UrlMapper.mapper;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UrlController {

	private final UrlService urlService;

	@PostMapping("/urls")
	@ResponseStatus(HttpStatus.CREATED)
	public Response.Info createUrl(@Valid @RequestBody Request.Create create) {
		return mapper.toInfo(urlService.createUrl(mapper.toUrl(create)));
	}

	@GetMapping("/{shortUrl}")
	public ResponseEntity redirectUrl(@PathVariable @Size(min = 8, max = 8) String shortUrl) {
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlService.redirectUrl(mapper.toUrl(shortUrl)))).build();
	}

	@GetMapping("/{shortUrl}/info")
	public Response.UrlInfo getUrlInfo(@PathVariable @Size(min = 8, max = 8) String shortUrl) {
		return mapper.toUrlInfo(urlService.getUrl(mapper.toUrl(shortUrl)));
	}

	@GetMapping("/{shortUrl}/count")
	public Response.CountInfo getCountInfo(@PathVariable @Size(min = 8, max = 8) String shortUrl) {
		return mapper.toCountInfo(urlService.getCount(mapper.toUrl(shortUrl)));
	}

}
