package com.jaoromi.urlshortening.shrt.services;

import java.util.HashSet;
import java.util.Set;

public class EnglishUtil {

    static final char[] vowels = {'a', 'e', 'i', 'o', 'u', 'w'};

    private static final Set<Character> vowelSet = new HashSet<>(vowels.length);
    static  {
        for(char vowel : vowels)
            vowelSet.add(vowel);
    }

    public static String extractConsonant(String str) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(!vowelSet.contains(ch)) {
                builder.append(ch);
            }
        }

        return builder.toString();
    }
}
