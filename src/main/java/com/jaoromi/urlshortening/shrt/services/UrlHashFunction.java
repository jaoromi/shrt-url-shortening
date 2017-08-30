package com.jaoromi.urlshortening.shrt.services;

import java.net.URI;

public interface UrlHashFunction {

    String hash(URI url);

}
