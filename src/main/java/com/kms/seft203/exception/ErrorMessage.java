package com.kms.seft203.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private int statusCode;
    private String message;
    private Date date;
    private String description;
    String value;

    public ErrorMessage() {

    }
    public ErrorMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorMessage(int statusCode, String message, Date date, String description, String value) {
        this.statusCode = statusCode;
        this.message = message;
        this.date = date;
        this.description = description;
        this.value = value;
    }

    public ErrorMessage(int value, Date date, String message, String description) {
        this.value = String.valueOf(value);
        this.date = date;
        this.message = message;
        this.description = description;
    }
}
