package com.jaoromi.urlshortening.shrt.documents;

import com.jaoromi.urlshortening.shrt.base62.Base62;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@CompoundIndexes({
        @CompoundIndex(name = "hash_idx", def = "{'relativeWords': 1, 'hash': 1}")
})
public class ShortUrl {

    /**
     * 단축 URL. Unique ID
     */
    @Id
    private String shortUrl;

    /**
     * 원본 URL.
     */
    @Indexed(unique = true)
    private String originalUrl;

    /**
     * 추측 가능한 도메인 2,3차 도메인에서 도출된 연관단어
     */
    private String relativeWord;

    /**
     * URL 의 경로 부분에 대한 해시 값
     */
    private String hash;

    /**
     * 동일 해시에 대한 순차 인덱스
     */
    private int seq;

    public void buildShortUrl() {
        this.shortUrl = relativeWord + "/" + hash + Base62.encode(seq);
    }

}
