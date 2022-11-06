package com.kms.seft203.contact;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper=false)
public class SaveContactRequest extends Contact {

    @NotEmpty(message = "firstname cannot be null")
    private String firstName;
    @NotBlank(message = "Name is mandatory")
    private String lastName;
    @NotEmpty(message = "title cannot be empty")
    private String title;
    @NotEmpty(message = "department cannot be empty")
    private String department;
    private String project;
    private String avatar;
    @NotEmpty(message = "employid cannot empty")
    private Integer employeeId;


}
