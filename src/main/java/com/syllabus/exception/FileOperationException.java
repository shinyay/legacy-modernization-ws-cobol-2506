package com.syllabus.exception;

public class FileOperationException extends SyllabusException {
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
