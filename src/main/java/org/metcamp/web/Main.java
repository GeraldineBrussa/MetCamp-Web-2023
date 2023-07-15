package org.metcamp.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.metcamp.web.model.entities.*;
import org.metcamp.web.model.response.EventResponse;
import org.metcamp.web.model.response.Response;
import org.metcamp.web.service.EventService;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private  static final ObjectMapper MAPPER = new ObjectMapper();
    private static final EventService eventService = new EventService();
    public static final String WELCOME_MSG = "Bienvenidx al sistema de eventos. Qué acción deseas realizar?";
    public static final String OPTIONS = "\nSeleccione una opción" +
            ":\n1 -> Crear un evento" +
            ";\n2 -> Conocer los eventos disponibles" +
            ";\n3 -> Encontrar un evento" +
            ";\n4 -> Modificar un evento" +
            ";\n5 -> Borrar un evento" +
            ";\n0 -> Salir";
    public static final String INVALID_OPTION_MSG = "La opción ingresada no es válida";
    public static final String GOOD_BYE_MSG = "------> Gracias por usar el sistema de eventos";
    public static void main (String[] args) throws JsonProcessingException {

        System.out.println(WELCOME_MSG);
        int option =1;

        while (option!= 0) {
            System.out.println(OPTIONS);
            option = scannerNextInt();
            int id = 0;
            switch (option) {
                case 1:
                    System.out.println("------> Ingrese los datos del evento a crear");
                    eventService.createEvent(SCANNER.nextLine());
                    break;
                case 2:
                    System.out.println("------> Obteniendo todos los eventos");
                    ArrayList<Event> events = eventService.getAllEvents();
                    System.out.println(events.isEmpty() ? "No hay eventos guardados" :
                            events.stream()
                                    .map(event -> {
                                        try {
                                            return MAPPER.writeValueAsString(event);
                                        } catch (JsonProcessingException e) {
                                            e.printStackTrace();
                                            return null; // Opcional: Manejar el error como desees
                                        }
                                    })
                                    .collect(Collectors.joining("\n")));
/*
                    System.out.println(events.isEmpty()? "No hay eventos guardados" : "");
                    for (Event e: events){
                        System.out.println(MAPPER.writeValueAsString(e));
                    }*/
                    break;
                case 3:
                    System.out.println("------> Ingrese el ID del evento que desea buscar");
                    id = scannerNextInt();
                    Response response = eventService.getEventById(id);
                    if (response.getCode()== 200){
                        EventResponse eventResponse = (EventResponse) response;
                        System.out.println(MAPPER.writeValueAsString(eventResponse.getEvent()));
                    } else {System.out.println(response);}
                    break;
                case 4:
                    System.out.println("------> Ingrese el ID del evento a modificar");
                    id = scannerNextInt();
                    Response responseUpdate = eventService.getEventById(id);
                    if (responseUpdate.getCode() == 200){
                       System.out.println("------> Ingrese los datos a modificar del evento con ID "+ id +":");
                       String json = SCANNER.nextLine();
                       eventService.updateEvent(id,json);
                       System.out.println("------> Evento con ID "+ id +" fue correctamente modificado");
                    } else {
                       System.out.println(responseUpdate);
                   }
                    break;
                case 5:
                    System.out.println("------> Ingrese el ID del evento a borrar");
                    id = scannerNextInt();
                    Response responseDelete = eventService.getEventById(id);
                    if (responseDelete.getCode() == 200 ){
                        eventService.deleteEvent(id);
                        System.out.println("------> Evento con ID "+ id +" fue correctamente eliminado");
                    }else {
                        System.out.println(responseDelete);
                    }
                    break;
                case 0:
                    System.out.println(GOOD_BYE_MSG);
                    break;
                default:
                    System.out.println(INVALID_OPTION_MSG);
            }
        }
    }
    public static int scannerNextInt(){
        return Integer.parseInt(SCANNER.nextLine());
    }

}