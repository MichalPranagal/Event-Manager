package com.example.Event_Manager.models.user.validation;

import com.example.Event_Manager.models.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {
    public void checkIfUserExist(Long userId) {
        if(userId <= 0 && userId.equals(null)) {
            throw new UserNotFoundException("User with this id is not in database.");
        }
    }
}
