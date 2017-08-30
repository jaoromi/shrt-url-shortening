package com.jaoromi.urlshortening.shrt.services;

import com.jaoromi.urlshortening.shrt.documents.ShortUrl;
import com.jaoromi.urlshortening.shrt.dto.ShortUrlDTO;
import com.jaoromi.urlshortening.shrt.repositories.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.net.URI;
import java.util.Optional;

@Service
public class ShortUrlService {

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

        repository.save(shortUrl);

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
