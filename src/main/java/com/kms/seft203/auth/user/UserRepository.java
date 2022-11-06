package com.kms.seft203.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    boolean existsByPassword(String password);

    User findByUsername(String userName);

    @Query(value = "SELECT u from User u where u.verificationCode = ?1")
    User findByVerificationcode(String code);

}
