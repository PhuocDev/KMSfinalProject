package com.kms.seft203.contact;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @NotNull
    private String id;

    @NotEmpty(message = "firstname cannot be null")
    private String firstName;
    @NotBlank(message = "Name is mandatory")
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "title cannot be empty")
    private String title;
    @NotEmpty(message = "department cannot be empty")
    private String department;

    private String project;
    private String avatar;
    @NotEmpty(message = "employid cannot empty")
    private Integer employeeId;

    public Contact(String id, String firstName, String lastName, String title, String department, String project, String avatar, Integer employeeId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.department = department;
        this.project = project;
        this.avatar = avatar;
        this.employeeId = employeeId;
    }

    public Contact(String firstName, String lastName, String title, String department, String project, String avatar, Integer employeeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.department = department;
        this.project = project;
        this.avatar = avatar;
        this.employeeId = employeeId;
    }

    public Contact() {

    }
    public Contact(SaveContactRequest request) {
        this.title = request.getTitle();
        this.project = request.getProject();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.department = request.getDepartment();
        this.avatar = request.getAvatar();
        this.employeeId = request.getEmployeeId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public void clone(Contact contactNew) {
        this.avatar = contactNew.getAvatar();
        this.department = contactNew.getDepartment();
        this.firstName = contactNew.getFirstName();
        this.lastName = contactNew.getLastName();
        this.project = contactNew.getProject();
        this.title = contactNew.getTitle();
    }

}
