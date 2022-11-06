package com.kms.seft203.auth.JWT;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kms.seft203.auth.user.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserJwt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private boolean isValid;
    private String refreshToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User user;

    public UserJwt() {

    }
    public UserJwt(long id, boolean isValid, String refreshToken, User user) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.user = user;
        this.isValid = isValid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
