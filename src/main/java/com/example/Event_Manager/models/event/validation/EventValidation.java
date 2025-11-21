package com.example.Event_Manager.models.event.validation;

import com.example.Event_Manager.models.event.exceptions.EventNotFoundException;
import com.example.Event_Manager.models.util.BaseValidation;
import com.example.Event_Manager.models.util.RequestEmptyException;
import org.springframework.stereotype.Component;


//TODO:: zrobic implementacje validacji jak bede robil serwis do eventow
@Component
public class EventValidation implements BaseValidation {
    public void checkIfEventExist(Long eventId) {
        if(eventId <= 0 && eventId.equals(null)) {
            throw new EventNotFoundException("Event with this id is not in database.");
        }
    }

    @Override
    public void checkIfRequestNotNull(Object request) {
        if(request == null) {
            throw new RequestEmptyException("Request cannot be null.");
        }
    }
}
