package com.kms.seft203.auth.JWT;

import com.kms.seft203.auth.JWT.UserJwt;
import com.kms.seft203.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJwtRepository extends JpaRepository<UserJwt, Long> {
    Optional<UserJwt> findByUserId(String userId);

    Optional<UserJwt> findByRefreshToken(String refreshToken);
    boolean existsByRefreshToken(String refreshToken);

    UserJwt findUserJwtByRefreshToken(String refreshToken);
}
