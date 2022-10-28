package com.kms.seft203.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
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

    public Task() {

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

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
