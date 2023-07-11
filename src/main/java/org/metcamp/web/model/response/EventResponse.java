package org.metcamp.web.model.response;

import lombok.*;
import org.metcamp.web.model.entities.Event;
@Getter@Setter
public class EventResponse extends Response{
    private Event event;
    public EventResponse(int code, String message, Event event) {
        super(code, message);
        this.event = event;
    }
}
