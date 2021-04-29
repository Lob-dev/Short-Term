package com.lob.app.controller;

import com.lob.app.controller.form.UrlForm.Response;
import com.lob.app.controller.form.UrlForm.Request;
import com.lob.app.domain.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.function.Function;

import static com.lob.app.domain.mapper.UrlMapper.mapper;

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
	public String redirectUrl(@PathVariable String shortUrl) {
		return "redirect:"+mapper.toUrl(urlService.redirectUrl(mapper.toUrl(shortUrl)));
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
