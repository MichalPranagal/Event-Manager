package com.example.Event_Manager.models.event.exceptions;

//TODO:: przeniesc w odpowiednie miejsce
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
