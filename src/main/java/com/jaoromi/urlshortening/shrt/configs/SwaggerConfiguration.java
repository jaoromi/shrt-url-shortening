package com.jaoromi.urlshortening.shrt.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ImplicitGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("mykey", authorizationScopes));
    }

    @Bean
    SecurityScheme apiKey() {
        return new ApiKey("Authentication Token", "Authorization", "header");// header ,query two values allowed
    }

    public static final String securitySchemaOAuth2 = "oauth2schema";

    private OAuth securitySchema() {
        return new OAuth(securitySchemaOAuth2, newArrayList(scopes()), newArrayList(grantTypes()));
    }



    @Bean
    SecurityScheme oauths() {
        return new OAuthBuilder().name("api-oauth").grantTypes(grantTypes()).scopes(scopes()).build();
    }


    List<AuthorizationScope> scopes() {
        return newArrayList(new AuthorizationScope("write:*", "modify endpoints"), new AuthorizationScope("read:*", "read endpoints"));
    }



    List<GrantType> grantTypes() {
        final GrantType grantType = new ImplicitGrantBuilder().loginEndpoint(new LoginEndpoint("http://localhost:8080/oauth/token")).build();
        return newArrayList(grantType);
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration("clientapp", "123456", "drucare-realm", "drucare-microservices-api", "AuthenticationToken", ApiKeyVehicle.HEADER,
                "", ",");
    }
}
