package org.metcamp.web.model.response;

import lombok.*;
import org.metcamp.web.model.entities.Event;

import java.util.ArrayList;
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventListResponse {
    private ArrayList<Event> events;
}
