package com.jaoromi.urlshortening.shrt.services;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class DefaultRelativeWordsConverter implements RelativeWordsConverter {

    @Inject
    private RelativeWordService relativeWordService;

    @Inject
    private HeadRelativeWordConverter headRelativeWordConverter;

    @Override
    public String convertRelativeWords(String domain) {

        return relativeWordService.getRegistedRelativeWord(domain)
                .orElseGet(() -> headRelativeWordConverter.convertRelativeWords(domain));
    }


}
