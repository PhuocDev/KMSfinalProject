package com.kms.seft203.auth.user;

import com.kms.seft203.auth.DTO.RegisterRequest;
import com.kms.seft203.dashboard.Dashboard;
import com.kms.seft203.task.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @NotNull
    @Column(name = "id")
    private String id;
    @NotNull @NotEmpty
    private String username;
    @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty
    private String password;
    @NotNull @NotEmpty
    private String fullName;

    private Boolean enable;

    private String verificationCode;

    public User() {

    }

    public User(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public User(String username, String email, String password, String fullName, Boolean enable, String verificationCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.enable = enable;
        this.verificationCode = verificationCode;
    }

    public User(RegisterRequest registerRequest) {
        this.username = registerRequest.getUsername();
        this.fullName = registerRequest.getFullName();
        this.email = registerRequest.getEmail();
        this.password = registerRequest.getPassword();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getVerificationcode() {
        return this.verificationCode;
    }

    public void setVerificationcode(String randomCode) {
        this.verificationCode = randomCode;
    }

    public void setEnabled(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnabled() {
        return this.enable;
    }
}
