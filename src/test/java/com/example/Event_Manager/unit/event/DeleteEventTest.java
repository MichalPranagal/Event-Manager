package com.example.Event_Manager.unit.event;

import com.example.Event_Manager.models.category.Category;
import com.example.Event_Manager.models.city.City;
import com.example.Event_Manager.models.country.Country;
import com.example.Event_Manager.models.event.Event;
import com.example.Event_Manager.models.event.enums.Status;
import com.example.Event_Manager.models.event.exceptions.EventNotFoundException;
import com.example.Event_Manager.models.event.repository.EventRepository;
import com.example.Event_Manager.models.event.service.EventService;
import com.example.Event_Manager.models.event.validation.EventValidation;
import com.example.Event_Manager.models.user.User;
import com.example.Event_Manager.models.user.enums.Role;
import com.example.Event_Manager.models.venue.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteEventTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventValidation eventValidation;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private User organizer;
    private Category category;
    private Venue venue;
    private LocalDateTime futureDate;
    private LocalDateTime pastDate;

    @BeforeEach
    void setUp() {
        Country country = Country.builder()
                .id(1L)
                .name("Polska")
                .code("PL")
                .build();

        City city = City.builder()
                .id(1L)
                .name("Warszawa")
                .country(country)
                .build();

        category = Category.builder()
                .id(1L)
                .name("Muzyka")
                .description("Wydarzenia muzyczne")
                .build();

        venue = Venue.builder()
                .id(1L)
                .name("Główna Sala Koncertowa")
                .address("Ulica 123")
                .description("Duża sala koncertowa")
                .city(city)
                .build();

        organizer = User.builder()
                .id(1L)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .phoneNumber("123456789")
                .password("haslo")
                .role(Role.ORGANIZER)
                .build();

        futureDate = LocalDateTime.now().plusDays(7);
        pastDate = LocalDateTime.now().minusDays(1);

        event = Event.builder()
                .id(1L)
                .name("Rockowy koncert")
                .description("Niesamowity koncert rockowy")
                .startTime(Timestamp.valueOf(futureDate))
                .status(Status.PUBLISHED)
                .category(category)
                .venue(venue)
                .organizer(organizer)
                .build();
    }

    @Test
    void deleteEvent_Success() {
        // Given
        Long eventId = 1L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(true);
        doNothing().when(eventRepository).deleteById(eventId);

        // When
        assertDoesNotThrow(() -> eventService.deleteEvent(eventId));

        // Then
        verify(eventValidation).checkIfIdValid(eventId);
        verify(eventRepository).existsById(eventId);
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    void deleteEvent_EventNotFound_ThrowsException() {
        // Given
        Long eventId = 999L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(false);

        // When & Then
        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(eventId);
        });

        assertEquals("Event with this id is not in database.", exception.getMessage());
        verify(eventValidation).checkIfIdValid(eventId);
        verify(eventRepository).existsById(eventId);
        verify(eventRepository, never()).deleteById(any());
    }

    @Test
    void deleteEvent_InvalidEventId_NegativeId_ThrowsException() {
        // Given
        Long invalidEventId = -1L;

        doThrow(new EventNotFoundException("Event with this id is not in database."))
                .when(eventValidation).checkIfIdValid(invalidEventId);

        // When & Then
        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(invalidEventId);
        });

        assertEquals("Event with this id is not in database.", exception.getMessage());
        verify(eventValidation).checkIfIdValid(invalidEventId);
        verify(eventRepository, never()).existsById(any());
        verify(eventRepository, never()).deleteById(any());
    }

    @Test
    void deleteEvent_NullEventId_ThrowsException() {
        // Given
        doThrow(new EventNotFoundException("Event with this id is not in database."))
                .when(eventValidation).checkIfIdValid(null);

        // When & Then
        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(null);
        });

        assertEquals("Event with this id is not in database.", exception.getMessage());
        verify(eventValidation).checkIfIdValid(null);
        verify(eventRepository, never()).existsById(any());
        verify(eventRepository, never()).deleteById(any());
    }

    @Test
    void deleteEvent_ZeroEventId_ThrowsException() {
        // Given
        Long eventId = 0L;

        doThrow(new EventNotFoundException("Event with this id is not in database."))
                .when(eventValidation).checkIfIdValid(eventId);

        // When & Then
        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(eventId);
        });

        assertEquals("Event with this id is not in database.", exception.getMessage());
        verify(eventValidation).checkIfIdValid(eventId);
        verify(eventRepository, never()).existsById(any());
        verify(eventRepository, never()).deleteById(any());
    }

    @Test
    void deleteEvent_RepositoryDeleteFailure_ThrowsException() {
        // Given
        Long eventId = 1L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(true);
        doThrow(new RuntimeException("Database connection error"))
                .when(eventRepository).deleteById(eventId);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventService.deleteEvent(eventId);
        });

        assertEquals("Database connection error", exception.getMessage());
        verify(eventValidation).checkIfIdValid(eventId);
        verify(eventRepository).existsById(eventId);
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    void deleteEvent_VerifyOperationOrder() {
        // Given
        Long eventId = 1L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(true);
        doNothing().when(eventRepository).deleteById(eventId);

        // When
        eventService.deleteEvent(eventId);

        // Then - verify order of operations
        var inOrder = inOrder(eventValidation, eventRepository);

        inOrder.verify(eventValidation).checkIfIdValid(eventId);
        inOrder.verify(eventRepository).existsById(eventId);
        inOrder.verify(eventRepository).deleteById(eventId);
    }

    @Test
    void deleteEvent_MultipleValidationFailures_StopsAtFirstFailure() {
        // Given
        Long eventId = -1L;

        doThrow(new EventNotFoundException("Event with this id is not in database."))
                .when(eventValidation).checkIfIdValid(eventId);

        // When & Then
        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(eventId);
        });

        assertEquals("Event with this id is not in database.", exception.getMessage());
        verify(eventValidation).checkIfIdValid(eventId);
        // Verify that subsequent operations are not called
        verify(eventRepository, never()).existsById(any());
        verify(eventRepository, never()).deleteById(any());
    }

    @Test
    void deleteEvent_ExistsCheckReturnsFalse_ThrowsException() {
        // Given
        Long eventId = 999L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(false);

        // When & Then
        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(eventId);
        });

        assertEquals("Event with this id is not in database.", exception.getMessage());
        verify(eventValidation).checkIfIdValid(eventId);
        verify(eventRepository).existsById(eventId);
        verify(eventRepository, never()).deleteById(any());
    }

    @Test
    void deleteEvent_ValidIdButNotInDatabase_ThrowsException() {
        // Given
        Long eventId = 100L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(false);

        // When & Then
        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(eventId);
        });

        assertEquals("Event with this id is not in database.", exception.getMessage());
        verify(eventValidation).checkIfIdValid(eventId);
        verify(eventRepository).existsById(eventId);
        verify(eventRepository, never()).deleteById(eventId);
    }

    @Test
    void deleteEvent_RepositoryExistsCheckThrowsException() {
        // Given
        Long eventId = 1L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId))
                .thenThrow(new RuntimeException("Database connection timeout"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventService.deleteEvent(eventId);
        });

        assertEquals("Database connection timeout", exception.getMessage());
        verify(eventValidation).checkIfIdValid(eventId);
        verify(eventRepository).existsById(eventId);
        verify(eventRepository, never()).deleteById(any());
    }

    @Test
    void deleteEvent_LargeEventId_Success() {
        // Given
        Long largeEventId = 999999999L;

        doNothing().when(eventValidation).checkIfIdValid(largeEventId);
        when(eventRepository.existsById(largeEventId)).thenReturn(true);
        doNothing().when(eventRepository).deleteById(largeEventId);

        // When
        assertDoesNotThrow(() -> eventService.deleteEvent(largeEventId));

        // Then
        verify(eventValidation).checkIfIdValid(largeEventId);
        verify(eventRepository).existsById(largeEventId);
        verify(eventRepository).deleteById(largeEventId);
    }

    @Test
    void deleteEvent_CalledMultipleTimes_ValidatesEachTime() {
        // Given
        Long eventId1 = 1L;
        Long eventId2 = 2L;

        doNothing().when(eventValidation).checkIfIdValid(anyLong());
        when(eventRepository.existsById(eventId1)).thenReturn(true);
        when(eventRepository.existsById(eventId2)).thenReturn(true);
        doNothing().when(eventRepository).deleteById(anyLong());

        // When
        eventService.deleteEvent(eventId1);
        eventService.deleteEvent(eventId2);

        // Then
        verify(eventValidation, times(2)).checkIfIdValid(anyLong());
        verify(eventRepository).existsById(eventId1);
        verify(eventRepository).existsById(eventId2);
        verify(eventRepository).deleteById(eventId1);
        verify(eventRepository).deleteById(eventId2);
    }

    @Test
    void deleteEvent_ValidationCheckExecutedBeforeExistsCheck() {
        // Given
        Long eventId = 1L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(true);
        doNothing().when(eventRepository).deleteById(eventId);

        // When
        eventService.deleteEvent(eventId);

        // Then - verify validation is called before repository check
        var inOrder = inOrder(eventValidation, eventRepository);
        inOrder.verify(eventValidation).checkIfIdValid(eventId);
        inOrder.verify(eventRepository).existsById(eventId);
    }

    @Test
    void deleteEvent_DeleteByIdCalledWithCorrectParameter() {
        // Given
        Long eventId = 42L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(true);
        doNothing().when(eventRepository).deleteById(eventId);

        // When
        eventService.deleteEvent(eventId);

        // Then
        verify(eventRepository).deleteById(eq(42L));
    }

    @Test
    void deleteEvent_NoInteractionWithRepositoryWhenValidationFails() {
        // Given
        Long eventId = -5L;

        doThrow(new EventNotFoundException("Event with this id is not in database."))
                .when(eventValidation).checkIfIdValid(eventId);

        // When & Then
        assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(eventId);
        });

        verifyNoInteractions(eventRepository);
    }

    @Test
    void deleteEvent_TransactionalBehavior_RollbackOnException() {
        // Given
        Long eventId = 1L;

        doNothing().when(eventValidation).checkIfIdValid(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(true);
        doThrow(new RuntimeException("Transaction rollback"))
                .when(eventRepository).deleteById(eventId);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventService.deleteEvent(eventId);
        });

        assertEquals("Transaction rollback", exception.getMessage());
        verify(eventRepository).deleteById(eventId);
    }
}