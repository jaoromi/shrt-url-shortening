package com.jaoromi.urlshortening.shrt.base62;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Base62Test {

    @Test
    public void testNext() {
        assertThat(Base62.next("a"), is("b"));
        assertThat(Base62.next("c"), is("d"));
        assertThat(Base62.next("z"), is("A"));
        assertThat(Base62.next("A"), is("B"));
        assertThat(Base62.next("C"), is("D"));
        assertThat(Base62.next("Z"), is("0"));
        assertThat(Base62.next("0"), is("1"));
        assertThat(Base62.next("3"), is("4"));
        assertThat(Base62.next("9"), is("ba"));
        assertThat(Base62.next("ba"), is("bb"));
        assertThat(Base62.next("bcd"), is("bce"));
        assertThat(Base62.next("bc9"), is("bda"));

        char[] testNum = new char[64];
        Arrays.fill(testNum, '9');

        char[] expectedNum = new char[65];
        Arrays.fill(expectedNum, 'a');
        expectedNum[0] = 'b';
        assertThat(Base62.next(new String(testNum)), is(new String(expectedNum)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNextOverflow() {
        char[] lastNum = new char[65];
        Arrays.fill(lastNum, '9');
        Base62.next(new String(lastNum));
    }
}