package com.jaoromi.urlshortening.shrt.services;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DomainUtilsTest {
    @Test
    public void testMeaningful() throws Exception {
        // 2차 도메인까지만 있는 경우
        assertThat(DomainUtils.meaningful("kakaopay.im"), is("kakaopay.im"));
        // 1차 도메인이 국가 도메인이 아닌 경우
        assertThat(DomainUtils.meaningful("www.kakaopay.com"), is("kakaopay.com"));
        // 1차 도메인이 국가도메인인 경우, 2차 도메인이 기관 도메인인 경우
        assertThat(DomainUtils.meaningful("www.kakaopay.co.kr"), is("kakaopay.co.kr"));
        // 1차 도메인이 국가도메인인 경우, 2차 도메인이 기관 도메인이 아닌 경우
        assertThat(DomainUtils.meaningful("www.kakaopay.kr"), is("kakaopay.kr"));
    }

    @Test
    public void testMeaningfulWord() throws Exception {
        // 2차 도메인까지만 있는 경우
        assertThat(DomainUtils.meaningfulWord("kakaopay.im"), is("kakaopay"));
        // 1차 도메인이 국가 도메인이 아닌 경우
        assertThat(DomainUtils.meaningfulWord("www.kakaopay.com"), is("kakaopay"));
        // 1차 도메인이 국가도메인인 경우, 2차 도메인이 기관 도메인인 경우
        assertThat(DomainUtils.meaningfulWord("www.kakaopay.co.kr"), is("kakaopay"));
        // 1차 도메인이 국가도메인인 경우, 2차 도메인이 기관 도메인이 아닌 경우
        assertThat(DomainUtils.meaningfulWord("www.kakaopay.kr"), is("kakaopay"));
    }

}