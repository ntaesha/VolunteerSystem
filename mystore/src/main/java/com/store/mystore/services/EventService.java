package com.store.mystore.services;

import com.store.mystore.models.Event;
import com.store.mystore.models.EventDto;
import com.store.mystore.models.User;
import com.store.mystore.models.UserEvent;
import com.store.mystore.repositories.EventRepository;
import com.store.mystore.repositories.UserEventRepository;
import com.store.mystore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEventRepository userEventRepository;

    // Get all events sorted by ID
    public List<Event> findAll() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    // Find an event by its ID
    public Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event ID: " + id));
    }
    // Convert Event to EventDto
    public EventDto findEventByIdAsDto(Long id) {
        Event event = findEventById(id);
        return mapEventToDto(event);
    }
    // Delete an event by ID
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }



    // Search events by name or location
    public List<Event> searchByNameOrLocation(String query) {
        return eventRepository.findByNameContainingOrLocationContaining(query, query);
    }

    // Update an existing event
    public void updateEvent(Long id, EventDto eventDto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event ID: " + id));

        event.setName(eventDto.getName());
        event.setLocation(eventDto.getLocation());
        event.setDescription(eventDto.getDescription());
        event.setOrganizer(eventDto.getOrganizer());
        event.setEventDate(eventDto.getEventDate());

        eventRepository.save(event);  // Persist changes to the database
    }

    // Create a new event
    public Event createEvent(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setLocation(eventDto.getLocation());
        event.setDescription(eventDto.getDescription());
        event.setOrganizer(eventDto.getOrganizer());
        event.setEventDate(eventDto.getEventDate());
        return eventRepository.save(event);
    }
    // Method to register a user for an event
    public void registerUserForEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        Event event = findEventById(eventId);

        UserEvent userEvent = new UserEvent();
        userEvent.setUser(user);
        userEvent.setEvent(event);

        userEventRepository.save(userEvent);
    }

    // Method to fetch events for users
    public List<Event> getEventsForUsers() {
        // Fetch events from the repository
        return eventRepository.findAll(); // Replace with actual logic
    }


    // Helper: Map Event to EventDto
    private EventDto mapEventToDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId((long) event.getId());
        eventDto.setName(event.getName());
        eventDto.setLocation(event.getLocation());
        eventDto.setDescription(event.getDescription());
        eventDto.setOrganizer(event.getOrganizer());
        eventDto.setEventDate(event.getEventDate());
        return eventDto;
    }

    // Helper: Map EventDto to Event
    private Event mapDtoToEvent(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setLocation(eventDto.getLocation());
        event.setDescription(eventDto.getDescription());
        event.setOrganizer(eventDto.getOrganizer());
        event.setEventDate(eventDto.getEventDate());
        return event;
    }
    // Method to get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Method to search events based on the query (e.g., by name or location)
    public List<Event> searchEvents(String query) {
        // You can customize the query to search by multiple fields (e.g., name, location)
        return eventRepository.findByNameContainingOrLocationContaining(query, query);
    }


}