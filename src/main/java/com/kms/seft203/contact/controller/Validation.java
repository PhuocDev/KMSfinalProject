package com.kms.seft203.contact.controller;

import com.kms.seft203.contact.dto.SaveContactRequest;

public class Validation {

    //Phương pháp tạm thời vì validate bằng annotation đang bị lỗi
    public static boolean validateContact(SaveContactRequest contact) {
        if ( contact.getAvatar() != null && contact.getFirstName() != null && contact.getLastName() != null
                && contact.getTitle() != null && contact.getEmployeeId() != null
                && contact.getDepartment() != null) return true;
        else  return  false;
    }
}
