package org.metcamp.web.model.response;

import lombok.*;
import org.metcamp.web.model.entities.Event;

import java.util.ArrayList;
@Getter@Setter
public class EventListResponse extends Response {
    private ArrayList<Event> events;

    public EventListResponse(int code, String message, ArrayList<Event> events) {
        super(code, message);
        this.events = events;
    }
}
