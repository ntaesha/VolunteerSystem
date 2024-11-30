package com.store.mystore.repositories;

import com.store.mystore.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // Custom query method for searching by name or location
    List<Event> findByNameContainingOrLocationContaining(String name, String location);


}
