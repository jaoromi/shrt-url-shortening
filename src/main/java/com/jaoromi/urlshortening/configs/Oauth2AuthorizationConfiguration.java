package com.jaoromi.urlshortening.configs;

import com.jaoromi.urlshortening.configs.security.AdminUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private DataSource dataSource;

    @Inject
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //oauthServer.checkTokenAccess("hasRole('ROLE_TRUSTED_CLIENT')");
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("admin-trusted-client")
//                .authorizedGrantTypes("client_credentials", "password")
//                .authorities("ROLE_TRUSTED_CLIENT")
//                .secret("fab860495e3c888236ead2f15790215e20d0db2e61a084ed492e8d04c876e07a");

        clients.jdbc(dataSource)
                .withClient("admin-web")
                .secret("3382ad5ec99e330b8bf8145feba2c1804a479cc3d0312a0e782289a581eb1696")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("read", "write");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

}
