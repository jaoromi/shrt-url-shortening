package com.jaoromi.urlshortening.shrt.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Map;

@RestController
public class Oauth2TokenEndpoint {

    @Inject
    private TokenEndpoint tokenEndpoint;

    @RequestMapping(
            value = {"/oauth/token"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<OAuth2AccessToken> getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        return tokenEndpoint.getAccessToken(principal, parameters);
    }

    @RequestMapping(
            value = {"/oauth/token"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        return tokenEndpoint.postAccessToken(principal, parameters);
    }
}
