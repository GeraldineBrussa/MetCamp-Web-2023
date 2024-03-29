package org.metcamp.web.repository;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.metcamp.web.exceptions.RepoException;
import org.metcamp.web.entities.model.Event;
import org.metcamp.web.utils.MapperUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;


public class EventRepository {
    private static final Logger logger = LogManager.getLogger();
    @Getter
    private final ArrayList<Event> events;
    private final MapperUtils mapperUtils;
    public EventRepository(MapperUtils mapperUtils){
        this.mapperUtils = mapperUtils;
        this.events = loadEvents();
    }

    private ArrayList<Event> loadEvents() {
        try{
            byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/repository/events.json"));
            String input = new String(bytes);
            return this.mapperUtils.mapToEventList(input);
        }catch (IOException io){
            throw new RepoException("Error reading file");
        }
    }
    private void save(){
        try {
            String datos = mapperUtils.mapToJson(events);
            byte [] bytes = datos.getBytes(StandardCharsets.UTF_8);
            Files.write(Paths.get("src/main/resources/repository/events.json"), bytes);
        } catch (IOException io){
            throw new RepoException("Error writing file");
        }
    }
    public Optional<Event> find (int id){
        Optional<Event> result = Optional.empty();
        for (Event e: events){
            if (e.getId() == id){
                result = Optional.of(e);
            }
        }
        return result;
    }
    public void add (Event newEvent){
        events.add(newEvent);
        save();
    }
    public void delete (int id){
        Optional<Event> foundEvent = find(id);
        if (foundEvent.isPresent()){
            events.remove(foundEvent.get());
            save();
        }
    }
    public void update(int id, Event updateInfo){
        Optional<Event> optionalEvent = find(id);
        if (optionalEvent.isPresent()){
            Event foundEvent = optionalEvent.get();
            events.remove(foundEvent);
            foundEvent.update(updateInfo);
            events.add(foundEvent);
            save();
        }
    }
}
