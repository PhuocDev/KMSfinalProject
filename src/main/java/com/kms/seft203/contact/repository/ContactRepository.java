package com.kms.seft203.contact.repository;

import com.kms.seft203.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

    @Transactional  //nhớ thêm 2 annotiation này vàoo để thực hiện custom query
    @Modifying
    @Query("update Contact u set u.firstName = ?1, u.lastName = ?2, " +
            "u.title = ?3, u.department = ?4, u.avatar = ?6, u.project = ?5, u.employeeId = ?7   " +
            " where u.id = ?8")
    void update(String firstName, String lastName, String title,
                String department, String project, String avatar, Integer employID, String id);

    @Transactional
    @Query(value = "select count(*) from Contact c where c.title = ?1", nativeQuery = true)
    Integer findNumberOfTitle(String title);
}
