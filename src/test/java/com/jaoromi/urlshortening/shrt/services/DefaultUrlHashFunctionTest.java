package com.jaoromi.urlshortening.shrt.services;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DefaultUrlHashFunctionTest {

    @Test
    public void testHash() {
        DefaultUrlHashFunction hashFunction = new DefaultUrlHashFunction();

        try {
            assertThat(
                    hashFunction.hash(new URI("https://charsyam.wordpress.com/2016/11/03/%EC%9E%85-%EA%B0%9C%EB%B0%9C-base62%EC%99%80-%EC%A7%84%EB%B2%95-%EC%97%B0%EC%82%B0/a.html?a=1234#fasdf"))
                            .length(), is(2));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}