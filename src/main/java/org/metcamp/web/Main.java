package org.metcamp.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.metcamp.web.model.entities.*;
import org.metcamp.web.model.response.EventResponse;
import org.metcamp.web.model.response.Response;
import org.metcamp.web.service.EventService;

import java.util.ArrayList;
import java.util.Scanner;

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

            switch (option) {
                case 1:
                    System.out.println("------> Ingrese los datos del evento a crear");
                    eventService.createEvent(SCANNER.nextLine());
                    break;
                case 2:
                    System.out.println("------> Obteniendo todos los eventos");
                    ArrayList<Event> events = eventService.getAllEvents();
                    for (Event e: events){
                        System.out.println(MAPPER.writeValueAsString(e));
                    }
                    break;
                case 3:
                    System.out.println("------> Ingrese el ID del evento que desea buscar");
                    Response response = eventService.getEventById(scannerNextInt());
                    if (response.getCode()== 200){
                        EventResponse eventResponse = (EventResponse) response;
                        System.out.println(MAPPER.writeValueAsString(eventResponse.getEvent()));
                    } else System.out.println(response);
                    break;
                case 4:
                    System.out.println("------> Ingrese el ID del evento a modificar");
                   int id = scannerNextInt();
                    System.out.println("------> Ingrese los datos a modificar del evento con id "+ id +":");
                    String json = SCANNER.nextLine();
                    eventService.updateEvent(id,json);
                    break;
                case 5:
                    System.out.println("------> Ingrese el ID del evento a borrar");
                    eventService.deletEvent(scannerNextInt());
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