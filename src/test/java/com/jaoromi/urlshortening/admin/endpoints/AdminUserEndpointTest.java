package com.jaoromi.urlshortening.admin.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaoromi.urlshortening.admin.entities.AdminUser;
import com.jaoromi.urlshortening.testsupport.MockMvcBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminUserEndpointTest extends MockMvcBase {

    @Inject
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        passwordMap.put("test", "testpassword");
        passwordMap.put("test3", "testpassword3");
    }

    @Test
    public void register() throws Exception {
        AdminUser data = AdminUser.builder()
                .id("test")
                .password("testpassword")
                .name("테스트")
                .email("test@test.com")
                .phone("010-1234-5678")
                .build();
        mockMvc.perform(post("/api/admin/users")
                .with(userToken("master"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", is("http://localhost:8080/api/admin/users/test")))
                .andReturn();
    }

    @Test
    public void getAdminUser() throws Exception {
        mockMvc.perform(get("/api/admin/users/test")
                .with(userToken("test")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("test")))
                .andExpect(jsonPath("$.name", is("테스트")))
                .andExpect(jsonPath("$.email", is("test@test.com")))
                .andExpect(jsonPath("$.phone", is("010-1234-5678")))
                .andExpect(jsonPath("$.password", nullValue()))
                .andReturn();
    }

    @Test
    public void updateAdminUser() throws Exception {
        AdminUser data = AdminUser.builder()
                .id("test")
                .name("테스트2")
                .email("test@test2.com")
                .phone("010-1234-5670")
                .build();
        mockMvc.perform(put("/api/admin/users/test")
                .with(userToken("test"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("test")))
                .andExpect(jsonPath("$.name", is("테스트2")))
                .andExpect(jsonPath("$.email", is("test@test2.com")))
                .andExpect(jsonPath("$.phone", is("010-1234-5670")))
                .andExpect(jsonPath("$.password", nullValue()))
                .andReturn();
    }

    @Test
    @Sql("/test-init.sql")
    public void deleteAdminUser() throws Exception {
        mockMvc.perform(delete("/api/admin/users/test3")
                .with(userToken("test3")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

}