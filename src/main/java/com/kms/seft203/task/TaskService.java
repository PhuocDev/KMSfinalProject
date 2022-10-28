package com.kms.seft203.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    List<Task> getTasks() {
        return taskRepository.findAll();
    }

    Task getTaskById(String id){
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
        oldtask.setCompleted(newTask.getIsCompleted());
        oldtask.setUserId(newTask.getUserId());

        taskRepository.saveAndFlush(oldtask);

    }

    public Integer findNumberOfIncompletedTask() {
        return taskRepository.findNumberOfIncompletedTask();
    }

    public Integer findNumberOfCompletedTask() {
        return taskRepository.findNumberOfCompletedTask();
    }
}
