package com.kms.seft203.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportApi {

    /*
    Count by field in a collection
    For example:
        - Number of each title (EM, TE, SE, BA) in Contact collection
        - Number of completed, not completed tasks in Task collection
    * */
    @Autowired
    ReportApiService reportApiService;

    @GetMapping("_countBy/{collection}/{field}")
    public ResponseEntity<?> countBy(@PathVariable String collection, @PathVariable String field) {
        if (collection.equals("tasks") || collection.equals("contacts") ) {
            return new ResponseEntity<Map<String, Integer>>(reportApiService.ContactFilter(collection, field), HttpStatus.OK);
        } else return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);



    }
}
