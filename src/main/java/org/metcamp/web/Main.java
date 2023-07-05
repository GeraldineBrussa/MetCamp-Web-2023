package org.metcamp.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.metcamp.web.entities.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private  static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String WELCOME_MSG = "Bienvenidx al sistema de eventos. Qué acción deseas realizar?";
    public static final String OPTIONS = "\nSeleccione la opción" +
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
                    createEvent(SCANNER.nextLine());
                    break;
                case 2:
                    System.out.println("------> Obteniendo todos los eventos");
                    getAllEvents();
                    break;
                case 3:
                    System.out.println("------> Ingrese el ID del evento que desea buscar");
                    getEventById(scannerNextInt());
                    break;
                case 4:
                    System.out.println("------> Ingrese el ID del evento a modificar");
                    updateEvent(scannerNextInt());
                    break;
                case 5:
                    System.out.println("------> Ingrese el ID del evento a borrar");
                    deleteEvent(scannerNextInt());
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
    private static void createEvent(String json) throws JsonProcessingException {
        Event event = MAPPER.readValue(json, Event.class);
        System.out.println("Creando el evento " + event.printJson());
    }
    private static void updateEvent(int id) {
        if (id == 20){
            System.out.println("Modificando evento " + id);
        } else {
            System.out.println("El evento solicitado no existe");
        }
    }
    private static void deleteEvent(int id) {
        if (id == 30){
            System.out.println("Borrando evento " + id);
        } else {
            System.out.println("El evento solicitado no existe");
        }
    }
    public static void getAllEvents () throws  JsonProcessingException{
        List<Event> events = List.of(
                new Event(1, EventType.ANIVERSARIO, "Aniversario MeT",
                        LocalDateTime.of(2023, 7, 1, 0, 0, 0),
                        LocalDateTime.of(2023, 7, 31, 23, 59, 59),
                        2000, "MeT",null),
                new Event(2, EventType.CLASE_METCAMP, "Clase 1",
                        LocalDateTime.of(2023, 7, 1, 10, 0, 0),
                        LocalDateTime.of(2023, 7, 1, 14, 0, 0),
                        200, "MetCamp Web",List.of(new Price(TicketType.REGULAR_FULL_PASS, Currency.ARS, 2500)))
        );
        for (Event e: events) {
            System.out.println(e.printJson());
        }
    }
    private static void getEventById(int id) throws JsonProcessingException {
        if (id == 10) {
            System.out.println(new Event(1, EventType.ENCUENTRO_METLAB, "Hackeá tus skills",
                    LocalDateTime.of(2023, 3, 18, 10, 0, 0),
                    LocalDateTime.of(2023, 3, 18, 12, 30, 0),
                    200, "MetLab Web",null)
                    .printJson());
        } else {
            System.out.println("El evento solicitado no existe");
        }
    }
}