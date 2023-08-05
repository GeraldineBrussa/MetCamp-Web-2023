package org.metcamp.web.entities.response;

import lombok.*;
import org.metcamp.web.entities.model.Event;

import java.util.ArrayList;
@Getter@Setter
public class EventListResponse extends Response {
    private ArrayList<Event> events;

    public EventListResponse(int code, String message, ArrayList<Event> events) {
        super(code, message);
        this.events = events;
    }
}
