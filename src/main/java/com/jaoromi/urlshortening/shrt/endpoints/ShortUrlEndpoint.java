package com.jaoromi.urlshortening.shrt.endpoints;

import com.jaoromi.urlshortening.shrt.dto.ShortUrlDTO;
import com.jaoromi.urlshortening.shrt.services.ShortUrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin(maxAge = 3600)
@RestController
public class ShortUrlEndpoint {

    @Inject
    private ShortUrlService shortUrlService;

    @RequestMapping(
            value = {"/{relativeWords}/{index}"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<?> redirectShortUrlToOriginal(
            @PathVariable("relativeWords") String relativeWords,
            @PathVariable("index") String index) {

        return shortUrlService.getShortUrlDTO(relativeWords + "/" + index)
                .map(dto -> ResponseEntity
                        .status(HttpStatus.MOVED_PERMANENTLY)
                        .location(URI.create(dto.getOriginalUrl()))
                        .build())
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(
            value = {"/short-url"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    public ResponseEntity<ShortUrlDTO> postShortUrl(@RequestBody ShortUrlDTO data) {
        try {
            URI originalUrl = new URI(data.getOriginalUrl());

            if(originalUrl.getHost() == null) {
                throw new IllegalArgumentException("not melformed original url: " + data.getOriginalUrl());
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(shortUrlService.register(originalUrl));
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("not melformed original url: " + data.getOriginalUrl());
        }
    }

}
