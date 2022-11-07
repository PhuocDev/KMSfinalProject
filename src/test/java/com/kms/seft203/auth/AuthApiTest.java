package com.kms.seft203.auth;

import com.kms.seft203.Seft203Application;
import com.kms.seft203.auth.DTO.LoginRequest;
import com.kms.seft203.auth.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static net.minidev.json.JSONValue.toJSONString;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Seft203Application.class)
@AutoConfigureMockMvc
class AuthApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    void loginWithCorrectAccount_shouldReturnOk() throws Exception {
        LoginRequest loginRequest = new LoginRequest("phuoc", "phuoc");
        this.mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJSONString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void loginWithIncorrectAccountUsername_shouldReturnError() throws Exception {
        LoginRequest loginRequest = new LoginRequest("phuocdeptrai", "phuoc");
        this.mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(toJSONString(loginRequest)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
    @Test
    void loginWithIncorrectPassword_shouldReturnError() throws Exception {
        LoginRequest loginRequest = new LoginRequest("phuoc", "phuoc98765");
        this.mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(toJSONString(loginRequest)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
}