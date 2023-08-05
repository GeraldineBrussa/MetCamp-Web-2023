package org.metcamp.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.metcamp.web.exceptions.ConvertionException;
import org.metcamp.web.exceptions.RepoException;
import org.metcamp.web.entities.model.Event;
import org.metcamp.web.entities.response.EventListResponse;
import org.metcamp.web.entities.response.EventResponse;
import org.metcamp.web.entities.response.Response;
import org.metcamp.web.repository.EventRepository;
import org.metcamp.web.utils.MapperUtils;

import java.util.ArrayList;
import java.util.Optional;

public class EventService {
    private static final Logger logger = LogManager.getLogger();
    private final MapperUtils mapperUtils;
    private final EventRepository repository;
    private final ValidationService validationService;
    public EventService(MapperUtils mapperUtils, EventRepository repository, ValidationService validationService){
        this.mapperUtils = mapperUtils;
        this.repository = repository;
        this.validationService = validationService;
    }
    public Response createEvent(String json){
        try {
            Event event = mapperUtils.mapToEvent(json);
            validationService.validateCreateEvent(event);
            Optional<Event> foundEvent = repository.find(event.getId());
            if (foundEvent.isPresent()){
                return new Response(400,"Event already exists");
            } else {
                repository.add(event);
                return new EventResponse(201, "Event Created", event);
            }
        } catch (ConvertionException e){
            return new Response(400, "Malformed Event");
        } catch (RepoException e){
            return new Response(500, e.getMessage());
        }
    }
    public Response getAllEvents(){
        ArrayList<Event> temporalList = repository.getEvents();
        if (temporalList.isEmpty()){
            return new Response(204, "Event List Empty");
        } else {
            return new EventListResponse(200, "OK", temporalList);
        }
    }
    public Response getEventById (int id) {
        Optional<Event> foundEvent = repository.find(id);
        return foundEvent.isPresent() ? new EventResponse(200, "OK", foundEvent.get()) : new Response(404, "Event Not" +
                " Found");
    }
    public Response updateEvent (int id, String json) throws JsonProcessingException{
        try {
            Optional<Event> foundEvent = repository.find(id);
            if (foundEvent.isPresent()){
                Event newEventData = mapperUtils.mapToEvent(json);
                validationService.validateUpdateEvent(newEventData);
                repository.update(id, newEventData);
                return new EventResponse(200, "Event Updated", newEventData);
            }else {
                logger.info("The ID entered is {} and the data is {} ", id, json);
                return new Response(404, "Event Not Found");
            }
        } catch (ConvertionException e){
            return new Response(404, "Malformed Event");
        } catch (RepoException e){
            return new Response(500, e.getMessage());
        }
    }
    public Response deleteEvent(int id){
        try {
            Optional<Event> foundEvent = repository.find(id);
            if (foundEvent.isPresent()){
                    repository.delete(foundEvent.get().getId());
                    return new Response(204, "Event Deleted");
                } else {
                    return new Response(404, "Event Not Found");
                }
        } catch (RepoException e){
            return new Response(500, e.getMessage());
        }
    }

}
