package org.university.exception;

public class CastingException extends RuntimeException {
    private static final String TEXT_ERROR = "Під час створення нової сутності сталася помилка. Ось текс помилки: ";
    public CastingException(String message) {
        super(TEXT_ERROR + message);
    }
}
