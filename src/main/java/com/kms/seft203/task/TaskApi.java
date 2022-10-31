package com.kms.seft203.task;

import com.kms.seft203.Validation.Validation;
import com.kms.seft203.exception.APImessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskApi {

    @Autowired
    TaskService taskService;
    private static final Map<String, Task> DATA = new HashMap<>();

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

    @PostMapping
    public ResponseEntity<?> insertNewTask(@RequestBody SaveTaskRequest request) {
        //DATA.put(request.getId(), request);
        if (Validation.validateTaskDTO(request)) {
            if (!taskService.existUserId(request.getUserId())) {
                return new ResponseEntity<>("Not found user id: " + request.getUserId(), HttpStatus.NOT_FOUND);
            }
            Task newTask = new Task(request);
            taskService.insertNewTask(newTask);
            return  new ResponseEntity<Task>(taskService.getTaskById(newTask.getId()), HttpStatus.OK);
        } else
            return new ResponseEntity<String>("Request invalid", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody SaveTaskRequest request) {
        //DATA.put(id, request);
        if (Validation.validateTaskDTO(request) && taskService.existUserId(request.getUserId())) {
            Task newTask = new Task(request);
            taskService.update(id, newTask);
            return  new ResponseEntity<Task>(taskService.getTaskById(id), HttpStatus.OK);
        } else
            return new ResponseEntity<String>("Request invalid", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
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
