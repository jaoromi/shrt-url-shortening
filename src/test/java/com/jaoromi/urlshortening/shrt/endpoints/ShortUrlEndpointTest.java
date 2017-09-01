package com.jaoromi.urlshortening.shrt.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaoromi.urlshortening.admin.entities.AdminUser;
import com.jaoromi.urlshortening.shrt.dto.ShortUrlDTO;
import com.jaoromi.urlshortening.testsupport.MockMvcBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShortUrlEndpointTest extends MockMvcBase {

    @Inject
    private ObjectMapper objectMapper;

    private ShortUrlDTO testUrl;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        ShortUrlDTO data = ShortUrlDTO.builder()
                .originalUrl("https://jaoromi.github.io/shrt-url-shortening")
                .build();
        testUrl = objectMapper.readValue(mockMvc.perform(post("/short-url")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", notNullValue()))
                .andReturn()
                    .getResponse()
                    .getContentAsString(), ShortUrlDTO.class
                );
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void redirectShortUrlToOriginal() throws Exception {
        mockMvc.perform(get(testUrl.getShortUrl()))
                .andDo(print())
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("location", is(testUrl.getOriginalUrl())))
                .andReturn();
    }

    @Test
    public void postShortUrl() throws Exception {
        ShortUrlDTO data = ShortUrlDTO.builder()
                .originalUrl("https://jaoromi.github.io/shrt-url-shortening")
                .build();
        mockMvc.perform(post("/short-url")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", notNullValue()))
                .andReturn();
    }

}