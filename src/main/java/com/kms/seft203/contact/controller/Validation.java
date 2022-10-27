package com.kms.seft203.contact.controller;

import com.kms.seft203.contact.dto.SaveContactRequest;
import com.kms.seft203.task.SaveTaskRequest;

public class Validation {

    //Phương pháp tạm thời vì validate bằng annotation đang bị lỗi
    public static boolean validateContact(SaveContactRequest contact) {
        if ( contact.getAvatar() != null && contact.getFirstName() != null && contact.getLastName() != null
                && contact.getTitle() != null && contact.getEmployeeId() != null
                && contact.getDepartment() != null) return true;
        else  return  false;
    }

    public static boolean validateTaskDTO(SaveTaskRequest request) {
        if (request.getTask() == null || request.getCompleted() == null || request.getUserId() == null ){
            return  false;
        }
        else return true;
    }
}
