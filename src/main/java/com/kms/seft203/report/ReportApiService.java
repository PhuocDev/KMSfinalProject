package com.kms.seft203.report;

import com.kms.seft203.contact.ContactService;
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
        if (collection.equals("contacts")) {
            Integer numberOfField = 0;
            numberOfField = contactService.findNumberOfTitle(field);
            result.put(field, numberOfField);
        }
        else if (collection.equals("tasks")) {
            Integer numberOfIncompletedTasks = taskService.findNumberOfIncompletedTask();
            Integer numberOfComletedTasks = taskService.findNumberOfCompletedTask();
            if (field.equals("isNotCompleted"))
                result.put("Incompleted Task: ", numberOfIncompletedTasks);
            if (field.equals("isCompleted"))
                result.put("Completed Task: ", numberOfComletedTasks);
        }
        return result;
    }

}
