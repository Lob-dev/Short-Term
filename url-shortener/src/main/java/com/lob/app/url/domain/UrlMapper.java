package com.lob.app.url.domain;

import com.lob.app.url.controller.form.UrlForm.Request;
import com.lob.app.url.controller.form.UrlForm.Response;
import com.lob.app.url.domain.model.Url;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UrlMapper {

	UrlMapper mapper = Mappers.getMapper(UrlMapper.class);

	Url toUrl(Request.Create create);
	Url toUrl(String shortUrl);

	UrlEntity toEntity(Url url);

	UrlEntity toEntity(Url url, String shortUrl);

	Response.Info toInfo(String shortUrl);
	Response.UrlInfo toUrlInfo(Url url);
	Response.CountInfo toCountInfo(Url url);

}
