package com.example.Event_Manager.models.event.exceptions;

//TODO:: przeniesc w odpowiednie miejsce
public class VenueNotFoundException extends RuntimeException {
    public VenueNotFoundException(String message) {
        super(message);
    }
}
