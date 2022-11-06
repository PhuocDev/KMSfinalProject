package com.kms.seft203.auth.DTO;

import antlr.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String refreshToken;

    public LoginResponse(String token, String refreshToken){
        this.refreshToken = refreshToken;
        this.token = token;
    }

}
