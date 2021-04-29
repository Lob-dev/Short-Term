package com.lob.app.domain.mapper;

import com.lob.app.controller.form.UrlForm.Request;
import com.lob.app.controller.form.UrlForm.Response;
import com.lob.app.domain.persistence.UrlEntity;
import com.lob.app.domain.service.model.Url;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UrlMapper {

	UrlMapper mapper = Mappers.getMapper(UrlMapper.class);

	Url                toUrl(Request.Create create);
	Url                toUrl(String shortUrl);


	Response.Info      toInfo(UrlEntity urlEntity);
	Response.UrlInfo   toUrlInfo(UrlEntity urlEntity);
	Response.CountInfo toCountInfo(UrlEntity urlEntity);
	String             toUrl(UrlEntity urlEntity);

}
