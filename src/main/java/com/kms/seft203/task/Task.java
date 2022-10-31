package com.kms.seft203.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kms.seft203.auth.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.net.UnknownServiceException;

@Data
@Entity
@Table(name = "task")
public class Task implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @NotNull
    private String id;
    @NotNull
    private String task;
    @NotNull
    private Boolean isCompleted;

    @NotNull
    private String userId;
//    @OneToOne(fetch = FetchType.EAGER)
//    @JsonIgnore
//    private User user;

    public Task() {

    }

    public Task(String id, String task, Boolean isCompleted, String userId) {
        this.id = id;
        this.task = task;
        this.isCompleted = isCompleted;
        this.userId = userId;
    }

    public Task(String task, Boolean isCompleted, String userId) {
        this.task = task;
        this.isCompleted = isCompleted;
        this.userId = userId;
    }

    public Task(SaveTaskRequest request) {
        this.task = request.getTask();
        this.isCompleted = request.getIsCompleted();
        this.userId = request.getUserId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
