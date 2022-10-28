package com.kms.seft203.contact.repository;

import com.kms.seft203.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

    @Transactional
    @Query(value = "select count(*) from Contact c where c.title = ?1", nativeQuery = true)
    Integer findNumberOfTitle(String title);
}
