package com.kms.seft203.auth.DTO;

import lombok.Data;

@Data
public class LogoutRequest {
    private String token;
    private String userName;
}
