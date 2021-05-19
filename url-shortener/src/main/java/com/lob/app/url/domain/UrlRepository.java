package com.lob.app.url.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

	@Query("SELECT U FROM UrlEntity U WHERE U.shortUrl = :url")
	UrlEntity findByShortUrl(@Param("url") String url);

}
