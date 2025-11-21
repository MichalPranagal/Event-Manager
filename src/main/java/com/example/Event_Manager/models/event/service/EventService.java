package com.example.Event_Manager.models.event.service;

import com.example.Event_Manager.models.event.dto.request.CreateEventDTO;
import com.example.Event_Manager.models.event.dto.request.UpdateEventDTO;
import com.example.Event_Manager.models.event.dto.response.EventDTO;
import com.example.Event_Manager.models.event.mapper.EventMapper;
import com.example.Event_Manager.models.event.repository.EventRepository;
import com.example.Event_Manager.models.event.validation.EventValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//TODO:: zrobic implementacje tych metod
@Service
@RequiredArgsConstructor
public class EventService implements IEventService{

    private final EventMapper eventMapper;

    private final EventValidation eventValidation;
    private final CategoryValidation categoryValidation;
    private final VenueValidation venueValidation;

    private final CategoryRepository categoryRepository;
    private final VenueRepository venueRepository;
    private final EventRepository eventRepository;

    @Override
    public EventDTO createEvent(CreateEventDTO eventDTO) {

        return null;
    }

    @Override
    public EventDTO updateEvent(Long eventId, UpdateEventDTO eventDTO) {
        return null;
    }

    @Override
    public void deleteEvent(Long eventId) {}

    @Override
    public EventDTO getEventById(Long eventId) {
        return null;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return List.of();
    }

    @Override
    public List<EventDTO> getEventsByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public List<EventDTO> getEventsByVenue(Long venueId) {
        return List.of();
    }

    @Override
    public List<EventDTO> getEventsByDateRange(LocalDateTime start, LocalDateTime end) {
        return List.of();
    }

    @Override
    public List<EventDTO> searchEventsByName(String name) {
        return List.of();
    }

    @Override
    public List<EventDTO> getEventsByOrganizer(Long organizerId) {
        return List.of();
    }
}
