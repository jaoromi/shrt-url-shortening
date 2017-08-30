package com.jaoromi.urlshortening.shrt.repositories;

import com.jaoromi.urlshortening.shrt.documents.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {

    Optional<ShortUrl> findByOrOriginalUrl(String originalUrl);

    List<ShortUrl> findByRelativeWordAndHash(String relativeWord, String hash);
}
