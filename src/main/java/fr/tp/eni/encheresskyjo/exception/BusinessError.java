package fr.tp.eni.encheresskyjo.exception;

public class BusinessError {
    private final String field;
    private final String messageCode; // par exemple "validation.user.email.blank"

    public BusinessError(String field, String messageCode) {
        this.field = field;
        this.messageCode = messageCode;
    }

    public String getField() {
        return field;
    }

    public String getMessageCode() {
        return messageCode;
    }
}

