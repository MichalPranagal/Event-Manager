package com.example.Event_Manager.models.user.repository;

import com.example.Event_Manager.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
