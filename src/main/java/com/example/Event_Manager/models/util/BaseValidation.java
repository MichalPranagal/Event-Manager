package com.example.Event_Manager.models.util;

public interface BaseValidation {

    // powinien rzucac RequestEmptyException
    void checkIfRequestNotNull(Object request);
}
