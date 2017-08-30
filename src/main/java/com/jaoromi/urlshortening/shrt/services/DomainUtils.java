package com.jaoromi.urlshortening.shrt.services;

import org.apache.commons.lang3.ArrayUtils;

public class DomainUtils {

    public static String meaningful(String domain) {
        String[] parts = domain.replace("www.", "").split("\\.");

        String[] meaningful;
        // 2차 도메인까지만 있는 경우
        if(parts.length == 2)
            meaningful = parts;
        // 1차 도메인이 국가 도메인이 아닌 경우
        else if(parts[parts.length - 1].length() > 2)
            meaningful = ArrayUtils.subarray(parts, parts.length - 2, parts.length);
            // 1차 도메인이 국가도메인인 경우
        else if(parts[parts.length - 1].length() == 2) {
            // 2차 도메인이 기관 도메인인 경우
            if(parts[parts.length - 2].length() == 2)
                meaningful = ArrayUtils.subarray(parts, parts.length - 3, parts.length);
            else
                meaningful = ArrayUtils.subarray(parts, parts.length - 2, parts.length);
        }
        // 예외 상황
        else
            return domain;

        return String.join(".", meaningful);
    }

    public static String meaningfulWord(String domain) {
        String[] parts = domain.replace("www.", "").split("\\.");

        // 2차 도메인까지만 있는 경우
        if(parts.length == 2)
            return parts[0];
            // 1차 도메인이 국가 도메인이 아닌 경우
        else if(parts[parts.length - 1].length() > 2)
            return parts[parts.length - 2];
            // 1차 도메인이 국가도메인인 경우
        else if(parts[parts.length - 1].length() == 2) {
            // 2차 도메인이 기관 도메인인 경우
            if(parts[parts.length - 2].length() == 2)
                return parts[parts.length - 3];
            else
                return parts[parts.length - 2];
        }
        // 예외 상황
        else
            return domain;
    }
}
