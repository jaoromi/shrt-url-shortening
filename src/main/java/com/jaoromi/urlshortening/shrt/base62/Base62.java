package com.jaoromi.urlshortening.shrt.base62;

public class Base62 {

    /**
     * All possible chars for representing a number as a String
     */
    private final static char[] digits = {
            'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', '0', '1',
            '2', '3', '4', '5', '6', '7',
            '8', '9',
    };

    private final static int radix = 62;

    public static String encode(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = digits[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = digits[(int) (-i)];

        return new String(buf, charPos, (65 - charPos));
    }

    public static String next(String base62Digits) {
        char[] buf = new char[65];

        System.arraycopy(base62Digits.toCharArray(), 0, buf, 65 - base62Digits.length(), base62Digits.length());

        int idx = buf.length;
        boolean overFlag = false;
        while (--idx >= 0 && buf[idx] > 0) {
            if (buf[idx] != '9') {
                if ((buf[idx] >= 'a' && buf[idx] < 'z')
                        || (buf[idx] >= 'A' && buf[idx] < 'Z')
                        || (buf[idx] >= '0' && buf[idx] < '9'))
                    buf[idx] = (char) (buf[idx] + 1);
                else if (buf[idx] == 'z')
                    buf[idx] = 'A';
                else if (buf[idx] == 'Z')
                    buf[idx] = '0';

                overFlag = false;
                break;
            }

            buf[idx] = 'a';
            overFlag = true;
        }

        if(overFlag) {
            buf[idx] = 'b';
        }

        if(buf[idx] == -1)
            throw new IndexOutOfBoundsException("overflow base62 digits");

        return new String(buf).trim();
    }
}
