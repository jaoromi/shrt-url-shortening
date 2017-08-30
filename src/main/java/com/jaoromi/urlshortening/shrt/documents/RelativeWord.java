package com.jaoromi.urlshortening.shrt.documents;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class RelativeWord
{
    @Id
    private String domain;

    private String relativeWord;

}
