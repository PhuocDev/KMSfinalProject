package com.kms.seft203.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    @Transactional  //nhớ thêm 2 annotiation này vàoo để thực hiện custom query
    @Modifying
    @Query("update Task u set u.task = ?1, u.isCompleted = ?2, " +
            "u.userId = ?3 " +
            " where u.id = ?4")
    void update(String task, Boolean isCompleted, String userId, String id);

//    @Transactional
//    @Query(value = "select count(*) from Task t where t.is_completed = ?1", nativeQuery = true)
//    Integer findNumberOfTitle(String field);

    @Transactional
    @Query(value = "select count(*) from Task t where t.is_completed = false", nativeQuery = true)
    Integer findNumberOfIncompletedTask();

    @Transactional
    @Query(value = "select count(*) from Task t where t.is_completed = true", nativeQuery = true)
    Integer findNumberOfCompletedTask();
}
