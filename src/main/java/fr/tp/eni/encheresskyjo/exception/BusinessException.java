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
}
