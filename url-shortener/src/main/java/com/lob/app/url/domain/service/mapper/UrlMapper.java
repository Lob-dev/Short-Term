package com.lob.app.url.domain.service.mapper;

import com.lob.app.url.controller.form.UrlForm.Request;
import com.lob.app.url.controller.form.UrlForm.Response;
import com.lob.app.url.domain.persistence.UrlEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UrlMapper {

	UrlMapper mapper = Mappers.getMapper(UrlMapper.class);

	Url toUrl(Request.Create create);
	Url toUrl(String shortUrl);

	@Mapping(target = "count", source = "requestCount")
	UrlEntity toEntity(Url url);

	Response.Info toInfo(String shortUrl);
	Response.UrlInfo toUrlInfo(Url url);
	Response.CountInfo toCountInfo(Url url);

}
