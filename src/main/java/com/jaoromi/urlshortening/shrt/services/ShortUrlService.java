package com.jaoromi.urlshortening.shrt.services;

import com.jaoromi.urlshortening.shrt.documents.ShortUrl;
import com.jaoromi.urlshortening.shrt.dto.ShortUrlDTO;
import com.jaoromi.urlshortening.shrt.repositories.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.net.URI;
import java.util.Optional;

@Service
public class ShortUrlService {

    private static final int DUPLICATED_RETRY_COUNT = 5;

    @Inject
    @Qualifier("defaultRelativeWordsConverter")
    protected RelativeWordsConverter converter;

    @Inject
    private UrlHashFunction hashFunction;

    @Inject
    private ShortUrlRepository repository;

    public Optional<ShortUrlDTO> getShortUrlDTO(String unique) {
        ShortUrlDTO dto = toDto(repository.findOne(unique));

        return dto == null ? Optional.empty() : Optional.of(dto);
    }

    public ShortUrlDTO register(URI url) {
        Optional<ShortUrl> oldUrl = repository.findByOrOriginalUrl(url.toString());
        if (oldUrl.isPresent()) {
            return oldUrl.map(shortUrl -> toDto(shortUrl)).get();
        }

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(url.toString());
        shortUrl.setRelativeWord(converter.convertRelativeWords(url.getHost()));
        shortUrl.setHash(hashFunction.hash(url));
        shortUrl.setSeq(nextSeq(shortUrl.getRelativeWord(), shortUrl.getHash()));
        shortUrl.buildShortUrl();

        return insertShortUrl(shortUrl);
    }

    private ShortUrlDTO insertShortUrl(ShortUrl shortUrl) {
        int retryCount = 0;
        while (retryCount++ < DUPLICATED_RETRY_COUNT) {
            try {
                repository.insert(shortUrl);
                break;
            } catch (DuplicateKeyException e) {
                shortUrl.setSeq(nextSeq(shortUrl.getRelativeWord(), shortUrl.getHash()));
            }
        }

        if (retryCount > DUPLICATED_RETRY_COUNT) {
            throw new DuplicateKeyException("duplicate key error: short url id { " + shortUrl.getShortUrl() + " }");
        }

        return toDto(shortUrl);
    }

    private int nextSeq(String relativeWord, String hash) {
        int max = repository.findByRelativeWordAndHash(relativeWord, hash)
                .stream()
                .map(shortUrl -> shortUrl.getSeq())
                .max(Integer::compareTo)
                .orElse(0);

        return max + 1;
    }

    private ShortUrlDTO toDto(ShortUrl shortUrl) {
        if (shortUrl == null) return null;

        return ShortUrlDTO.builder()
                .originalUrl(shortUrl.getOriginalUrl())
                .shortUrl(shortUrl.getShortUrl())
                .build();
    }
}
