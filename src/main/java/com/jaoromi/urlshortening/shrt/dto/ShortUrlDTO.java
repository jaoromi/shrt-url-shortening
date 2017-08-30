package com.jaoromi.urlshortening.shrt.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortUrlDTO {

    private String originalUrl;

    private String shortUrl;

}
