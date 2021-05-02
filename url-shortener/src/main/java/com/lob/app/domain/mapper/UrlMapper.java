package com.lob.app.domain.mapper;

import com.lob.app.controller.form.UrlForm.Request;
import com.lob.app.controller.form.UrlForm.Response;
import com.lob.app.domain.persistence.UrlEntity;
import com.lob.app.domain.service.model.Url;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.net.URI;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UrlMapper {

	UrlMapper mapper = Mappers.getMapper(UrlMapper.class);


	Url                toUrl(Request.Create create);
	Url                toUrl(String shortUrl);
	Url                toUrl(UrlEntity entity);
	Url                toUrl(UrlEntity entity, Long count);

	UrlEntity		   toEntity(Url url);

	Response.Info      toInfo(String shortUrl);
	Response.UrlInfo   toUrlInfo(Url url);
	Response.CountInfo toCountInfo(Url url);

}
