package com.kms.seft203.task;

import com.kms.seft203.auth.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserService userService;
    List<Task> getTasks() {
        return taskRepository.findAll();
    }

    Task getTaskById(String id){
        Task getTask = taskRepository.findById(id).get();
        if (getTask == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else
        return taskRepository.findById(id).get();
    }
    void insertNewTask(Task task) {
        taskRepository.save(task);
    }

    void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
    void update(String id, Task newTask) {
        Task oldtask = taskRepository.getById(id);
        oldtask.setTask(newTask.getTask());
        oldtask.setIsCompleted(newTask.getIsCompleted());
        oldtask.setUserId(newTask.getUserId());

        taskRepository.saveAndFlush(oldtask);

    }

    public Integer findNumberOfIncompletedTask() {
        return taskRepository.findNumberOfIncompletedTask();
    }

    public Integer findNumberOfCompletedTask() {
        return taskRepository.findNumberOfCompletedTask();
    }

    public boolean existUserId(String userId) {
        return userService.isUserIdExist(userId);
    }
}
