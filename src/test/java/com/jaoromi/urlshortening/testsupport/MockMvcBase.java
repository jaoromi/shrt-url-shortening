package com.jaoromi.urlshortening.testsupport;

import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.servlet.Filter;

import java.util.HashMap;
import java.util.Map;

import static capital.scalable.restdocs.AutoDocumentation.*;
import static capital.scalable.restdocs.misc.AuthorizationSnippet.documentAuthorization;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.cli.CliDocumentation.curlRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class MockMvcBase {

    @Inject
    private WebApplicationContext context;

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private Filter springSecurityFilterChain;

    protected MockMvc mockMvc;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(resolveOutputDir());

    protected Map<String, String> passwordMap = new HashMap<>();

    private String resolveOutputDir() {
        String outputDir = System.getProperties().getProperty(
                "org.springframework.restdocs.outputDir");
        if (outputDir == null) {
            outputDir = "target/generated-snippets";
        }
        return outputDir;
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .alwaysDo(commonDocumentation())
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080)
                        .and().snippets()
                        .withDefaults(
                                curlRequest(),
                                httpRequest(),
                                httpResponse(),
                                requestFields(),
                                responseFields(),
                                pathParameters(),
                                requestParameters(),
                                description(),
                                methodAndPath(),
                                section()))
                .build();

        this.passwordMap.put("master", "master1234!@#$");
    }

    protected RestDocumentationResultHandler commonDocumentation() {
        return document("{class-name}/{method-name}",
                preprocessRequest(), commonResponsePreprocessor());
    }

    protected OperationResponsePreprocessor commonResponsePreprocessor() {
        return preprocessResponse(replaceBinaryContent(), limitJsonArrayLength(objectMapper),
                prettyPrint());
    }

    protected RequestPostProcessor userToken(String id) {
        return request -> {
            String accessToken;
            try {
                accessToken = getAccessToken(id, passwordMap.get(id));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            request.addHeader("Authorization", "Bearer " +  accessToken);
            return documentAuthorization(request, "User access token required.");
        };
    }

    private String getAccessToken(String username, String password) throws Exception {
        String clientId = "admin-web";
        String clientSecret = "3382ad5ec99e330b8bf8145feba2c1804a479cc3d0312a0e782289a581eb1696";

        String authorization = "Basic "
                + new String(Base64Utils.encode(
                        (clientId + ":" + clientSecret).getBytes()
        ));
        String contentType = MediaType.APPLICATION_JSON_UTF8_VALUE;

        String body = mockMvc
                .perform(
                        post("/oauth/token")
                                .header("Authorization", authorization)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("username", username)
                                .param("password", password)
                                .param("grant_type", "password")
                                .param("scope", "read write")
                                .param("client_id", clientId)
                                .param("client_secret", clientSecret))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.access_token", is(notNullValue())))
                .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
                .andExpect(jsonPath("$.refresh_token", is(notNullValue())))
                .andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
                .andExpect(jsonPath("$.scope", is(equalTo("read write"))))
                .andReturn().getResponse().getContentAsString();

        return body.substring(17, 53);
    }
}
