package com.app.Todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
public class TypeErrorException extends RuntimeException {
    private static final long serialversionUID = 1L;
    private String fieldName;
    private Object fieldValue;

    public TypeErrorException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("Enter a valid %s type : '%s'",fieldName,fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName(){
        return fieldName;
    }

    public Object getFieldValue(){
        return fieldValue;
    }

}
