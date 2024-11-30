package com.store.mystore.controllers;

import com.store.mystore.models.Event;
import com.store.mystore.models.EventDto;
import com.store.mystore.models.User;
import com.store.mystore.services.EventService;
import com.store.mystore.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    // Display events for all users
    @GetMapping({"", "/"})
    public String showEventList(Model model) {
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        return "events/index";
    }

    // Admin: Create Event (Form)
    @GetMapping("/create")
    public String showCreateEvent(Model model) {
        model.addAttribute("eventDto", new EventDto());
        return "events/create-event";
    }

    // Admin: Save New Event
    @PostMapping("/create")
    public String createEvent(@Valid @ModelAttribute EventDto eventDto, BindingResult result) {
        if (result.hasErrors()) {
            return "events/create-event";
        }
        eventService.createEvent(eventDto);
        return "redirect:/events";
    }



    // Admin: Edit Event (Form)
    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        EventDto eventDto = eventService.findEventByIdAsDto(id);
        model.addAttribute("eventDto", eventDto);
        return "events/edit-event";
    }

    // Admin: Update Event
    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable Long id, @Valid @ModelAttribute EventDto eventDto, BindingResult result) {
        if (result.hasErrors()) {
            return "events/edit-event";
        }
        eventService.updateEvent(id, eventDto);
        return "redirect:/events";
    }

    // Admin: Delete Event
    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }

    // Search Events
    @GetMapping("/search")
    public String searchEvents(@RequestParam("query") String query, Model model) {
        List<Event> events = eventService.searchByNameOrLocation(query);
        model.addAttribute("events", events);
        return "events/index";
    }

    // User: Register for Event
    @PostMapping("/register/{eventId}")
    public String registerForEvent(@PathVariable Long eventId, Principal principal) {
        String username = principal.getName(); // Get logged-in user's username
        User user = userService.findByUsername(username);
        eventService.registerUserForEvent(user.getId(), eventId);
        return "redirect:/events";
    }

    @GetMapping("/eventForUsers")
    public String getEventsForUsers(Model model) {
        List<Event> events = eventService.getEventsForUsers();
        model.addAttribute("events", events);
        return "events/eventForUsers";  // Return a Thymeleaf view, not JSON
    }

    @GetMapping("/view/{id}")
    public String viewEvent(@PathVariable Long id, Model model) {
        Event event = eventService.findEventById(id);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        model.addAttribute("event", event); // Add event details to the model
        return "events/view-event"; // Return the name of the Thymeleaf template
    }

}
