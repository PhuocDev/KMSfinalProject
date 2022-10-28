package com.kms.seft203.exception;

public class BookNotFoundException extends RuntimeException{
    BookNotFoundException(Long id) {
        super("could not find book id: " + id);
    }
}
