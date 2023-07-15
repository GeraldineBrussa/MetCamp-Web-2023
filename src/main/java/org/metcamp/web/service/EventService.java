package org.metcamp.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.metcamp.web.model.entities.Event;
import org.metcamp.web.model.response.EventResponse;
import org.metcamp.web.model.response.Response;

import java.util.ArrayList;
import java.util.Optional;

public class EventService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ArrayList<Event> events;
     public EventService() {
              this.events = new ArrayList<>();
          }

    private Optional<Event> findEventById (int id){
        Optional<Event> result = Optional.empty();
        for (Event e:events){
            if(e.getId()==id){
                result=Optional.of(e);
            }
        }
        return result;
    }
    public void createEvent(String json) throws JsonProcessingException {
        Event event= MAPPER.readValue(json, Event.class);
        events.add(event);
    }
    public ArrayList<Event> getAllEvents(){
        return events;
    }
    public Response getEventById(int id){
        Optional<Event> foundEvent = findEventById(id);
        return foundEvent.isPresent() ? new EventResponse(200, "OK", foundEvent.get()): new Response(404,"Event Not " +
                "Found");
    }
    public void updateEvent (int id, String json) throws JsonProcessingException {
        Event newEventData = MAPPER.readValue(json, Event.class);
        Optional<Event> foundEvent= findEventById(id);
        foundEvent.ifPresent(e -> e.update(newEventData));

    }
    public void deleteEvent(int id){
        Optional<Event> foundEvent= findEventById(id);
        foundEvent.ifPresent(events::remove);
    }
}
