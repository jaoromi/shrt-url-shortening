package com.jaoromi.urlshortening.shrt.services;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeadRelativeWordConverterTest {

    @Test
    public void testConvertRelativeWords() throws Exception {
        HeadRelativeWordConverter converter = new HeadRelativeWordConverter();

        assertThat(converter.convertRelativeWords("www.instagram.com"), is("nstg"));
        assertThat(converter.convertRelativeWords("www.facebook.com"), is("fcbk"));
        assertThat(converter.convertRelativeWords("hankookt.co.kr"), is("hnkk"));
        assertThat(converter.convertRelativeWords("kakao.com"), is("kaka"));
        assertThat(converter.convertRelativeWords("www.kakako.co.kr"), is("kkk"));
        assertThat(converter.convertRelativeWords("admin.kakako.co.kr"), is("kkk"));
    }

}