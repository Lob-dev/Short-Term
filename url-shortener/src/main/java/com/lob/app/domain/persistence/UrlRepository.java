package com.lob.app.domain.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

	@Query("SELECT U FROM UrlEntity U WHERE U.shortUrl = ?1")
	UrlEntity findByShortUrl(String shortUrl);

}
