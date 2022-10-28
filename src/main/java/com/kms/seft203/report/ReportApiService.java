package com.kms.seft203.report;

import com.kms.seft203.contact.repository.ContactRepository;
import com.kms.seft203.contact.service.ContactService;
import com.kms.seft203.task.TaskRepository;
import com.kms.seft203.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportApiService {
    /*
    Count by field in a collection
    For example:
    - Number of each title (EM, TE, SE, BA) in Contact collection
    - Number of completed, not completed tasks in Task collection
    * */

    @Autowired
    TaskService taskService;
    @Autowired
    ContactService contactService;

    public Map<String,Integer> ContactFilter(String collection, String field) {
        Map<String, Integer> result = new HashMap<>();
        if (collection.equals("contact")) {
            Integer numberOfField = 0;
            numberOfField = contactService.findNumberOfTitle(field);
            result.put(field, numberOfField);
        }
        else if (collection.equals("task")) {
            Integer numberOfIncompletedTasks = taskService.findNumberOfIncompletedTask();
            Integer numberOfComletedTasks = taskService.findNumberOfCompletedTask();
            result.put("Incompleted Task: ", numberOfIncompletedTasks);
            result.put("Completed Task: ", numberOfComletedTasks);
        }
        return result;
    }

}
