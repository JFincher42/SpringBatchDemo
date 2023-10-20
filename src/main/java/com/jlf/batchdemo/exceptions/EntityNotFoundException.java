package com.jlf.batchdemo.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message){
        super(message);
    }

    public EntityNotFoundException(){
        super();
    }

}
