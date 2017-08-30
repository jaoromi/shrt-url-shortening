package com.jaoromi.urlshortening.shrt.services;

import org.springframework.stereotype.Component;

@Component
public class HeadRelativeWordConverter implements RelativeWordsConverter {

    @Override
    public String convertRelativeWords(String domain) {
        String meaningful = DomainUtils.meaningfulWord(domain);
        String relativeWord = EnglishUtil.extractConsonant(meaningful);

        if(relativeWord.length() < 3)
            return meaningful.substring(0,4);
        if(relativeWord.length() > 4)
            return relativeWord.substring(0,4);
        else
            return relativeWord;
    }


}
