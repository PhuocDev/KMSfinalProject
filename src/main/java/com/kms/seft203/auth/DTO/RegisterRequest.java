package com.kms.seft203.auth.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {
    @NotNull @NotEmpty
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String fullName;
}
