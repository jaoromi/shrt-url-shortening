package com.jaoromi.urlshortening.shrt.services;

import com.jaoromi.urlshortening.shrt.base62.Base62;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class DefaultUrlHashFunction implements UrlHashFunction {

    @Override
    public String hash(URI url) {
        String path = url.getRawPath();
        if(url.getQuery() != null)
            path += "/" + url.getQuery();
        if(url.getFragment() != null)
            path += "#" + url.getFragment();
        return Base62.encode(path.hashCode() % (62 * 62)); // base62  2자리로 표현 가능한 해시
    }

}
