package fr.tp.eni.encheresskyjo.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {
    private List<String> keys = new ArrayList<>();

    public BusinessException() {
    }

    public void addKey(String key) {
        keys.add(key);
    }

    public List<String> getKeys() {
        return keys;
    }

    private final List<BusinessError> errors = new ArrayList<>();

    public void addGlobalError(String messageCode) {
        errors.add(new BusinessError(null, messageCode));
    }

    public void addFieldError(String field, String messageCode) {
        errors.add(new BusinessError(field, messageCode));
    }

    public List<BusinessError> getErrors() {
        return errors;
    }
}
