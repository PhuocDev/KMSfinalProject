package com.kms.seft203.Validation;

import com.kms.seft203.contact.SaveContactRequest;
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
        if (request.getTask() == null || request.getIsCompleted() == null || request.getUserId() == null ){
            return  false;
        }
        return true;
    }
}
