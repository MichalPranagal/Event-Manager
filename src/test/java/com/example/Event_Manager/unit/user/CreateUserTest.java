package com.example.Event_Manager.unit.user;

import com.example.Event_Manager.auth.repository.UserRepository;
import com.example.Event_Manager.models.user.User;
import com.example.Event_Manager.models.user.dto.request.CreateUserDTO;
import com.example.Event_Manager.models.user.dto.response.UserDTO;
import com.example.Event_Manager.models.user.enums.Role;
import com.example.Event_Manager.models.user.enums.Status;
import com.example.Event_Manager.models.user.mapper.UserMapper;
import com.example.Event_Manager.models.user.service.UserService;
import com.example.Event_Manager.models.user.validation.UserValidation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Tests for User Creation")
public class CreateUserTest {

    @Mock
    private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private UserValidation userValidation;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should create user successfully when data is valid")
    void createUser_Success() {
        //Given
        CreateUserDTO dto = new CreateUserDTO("Jan", "Janowski", "jan@gmail.com", "123456789", "password", Role.ATTENDEE);
        User mappedUser = User.builder().email(dto.email()).build(); //To co zwraca mapper
        User savedUser = User.builder().id(1L).email(dto.email()).status(Status.ACTIVE).build(); // To co zwraca repo po zapisaniu
        UserDTO expectedDTO = new UserDTO(1L, "Jan", "Janowski", "jan@gmail.com", "123456789", Role.ATTENDEE);

        //mockowanie
        doNothing().when(userValidation).checkIfRequestNotNull(dto);
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(dto.phoneNumber())).thenReturn(false);
        when(userMapper.toEntity(dto)).thenReturn(mappedUser);
        when(passwordEncoder.encode(dto.password())).thenReturn("hashedPass");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toDTO(savedUser)).thenReturn(expectedDTO);

        //When
        UserDTO result = userService.createUser(dto);

        //Then
        assertEquals(expectedDTO.email(), result.email());
        verify(userRepository).save(mappedUser);
        //sprawdzamy czy hasło zostalo zakodowane
        verify(passwordEncoder).encode("password");
    }

    @Test
    @DisplayName("Should throw exception when email is already taken")
    void createUser_EmailTaken_ThrowsException() {
        //Given
        CreateUserDTO dto = new CreateUserDTO("Jan", "Janowski", "zajety@email.com", "123456789", "password123", Role.ATTENDEE);

        doNothing().when(userValidation).checkIfRequestNotNull(dto);
        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        //Then
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when phone number is already taken")
    void createUser_PhoneTaken_ThrowsException() {
        //Given
        CreateUserDTO dto = new CreateUserDTO("Jan", "Janowski", "jan@test.com", "999999999", "password", Role.ATTENDEE);

        doNothing().when(userValidation).checkIfRequestNotNull(dto);
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(dto.phoneNumber())).thenReturn(true);

        //Then
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(dto));
        verify(userRepository, never()).save(any());
    }
}