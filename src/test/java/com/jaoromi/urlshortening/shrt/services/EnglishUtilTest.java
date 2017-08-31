package com.jaoromi.urlshortening.shrt.services;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EnglishUtilTest {

    @Test
    public void testExtractConsonant() {
        assertThat(EnglishUtil.extractConsonant("instagram"), is("nstgrm"));
        assertThat(EnglishUtil.extractConsonant("facebook"), is("fcbk"));
        assertThat(EnglishUtil.extractConsonant("hankooktire"), is("hnkktr"));
    }

}