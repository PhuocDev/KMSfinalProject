package com.kms.seft203.task;

import com.kms.seft203.contact.controller.Validation;
import com.kms.seft203.exception.APImessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskApi {

    @Autowired
    TaskService taskService;
    private static final Map<String, Task> DATA = new HashMap<>();


    @GetMapping("/createDB")
    public List<Task> createDB() {
        for (int i = 1; i < 5; i++) {
            Task task = new Task("Task " + i, false, "100" +i*3419384   );
            taskService.insertNewTask(task);
        }
        return taskService.getTasks();
    }
    @GetMapping
    public List<Task> getAll() {
        //return new ArrayList<>(DATA.values());
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        if(taskService.getTaskById(id) != null) {
            return new ResponseEntity<Task>(taskService.getTaskById(id), HttpStatus.FOUND);
        } else {
            APImessages message = new APImessages("Not found task id: " + id);
            return new ResponseEntity<APImessages>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody @Valid SaveTaskRequest request) {
        //DATA.put(request.getId(), request);
        if (Validation.validateTaskDTO(request)) {
            Task task = new Task(request);
            taskService.insertNewTask(task);
            return  new ResponseEntity<Task>(task, HttpStatus.OK);
        } else
            return new ResponseEntity<String>("Request invalid", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody SaveTaskRequest request) {
        //DATA.put(id, request);
        if (Validation.validateTaskDTO(request)) {
            Task newTask = new Task(request);
            taskService.update(id, newTask);
            return  new ResponseEntity<Task>(taskService.getTaskById(id), HttpStatus.OK);
        } else
            return new ResponseEntity<String>("Request invalid", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        //return DATA.remove(id);
        //check if not found task id
        if (taskService.getTaskById(id) == null) {
            APImessages apImessages = new APImessages("Not found task id: " + id);
            return  new ResponseEntity<APImessages>(apImessages, HttpStatus.NOT_FOUND);
        } else {
            taskService.deleteTask(id);
            return new ResponseEntity<String>("Deleted task id :" + id, HttpStatus.ACCEPTED);
        }
    }
}
