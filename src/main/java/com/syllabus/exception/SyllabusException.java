package com.syllabus.exception;

public class SyllabusException extends Exception {
    public SyllabusException(String message) {
        super(message);
    }

    public SyllabusException(String message, Throwable cause) {
        super(message, cause);
    }
}
