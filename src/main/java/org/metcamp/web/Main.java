package org.metcamp.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.metcamp.web.model.response.EventListResponse;
import org.metcamp.web.model.response.EventResponse;
import org.metcamp.web.model.response.Response;
import org.metcamp.web.repository.EventRepository;
import org.metcamp.web.service.EventService;
import org.metcamp.web.utils.MapperUtils;
import java.util.Scanner;


public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final MapperUtils mapperUtils = new MapperUtils();
    private static final EventRepository repository = new EventRepository(mapperUtils);
    private static final EventService eventService = new EventService(mapperUtils, repository);
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
            Response response;
            switch (option) {
                case 1:
                    System.out.println("------> Ingrese los datos del evento a crear");
                    if (response.getCode() == 201){
                        System.out.println(response);
                        EventResponse eventResponse = (EventResponse) response;
                        System.out.println(mapperUtils.mapToJson(eventResponse.getEvent()));
                    } else {
                        System.out.println(response);
                    }
                    break;
                case 2:
                    System.out.println("------> Obteniendo todos los eventos");
                    response = eventService.getAllEvents();
                    if (response.getCode() == 200){
                        System.out.println(response);
                        EventListResponse eventListResponse = (EventListResponse) response;
                        System.out.println(mapperUtils.mapToJson(eventListResponse.getEvents()));
                    } else {
                        System.out.println(response);
                    }
                    break;
                case 3:
                    System.out.println("------> Ingrese el ID del evento que desea buscar");
                    response = eventService.getEventById(scannerNextInt());
                    if (response.getCode() == 200){
                        System.out.println(response);
                        EventResponse eventResponse = (EventResponse) response;
                        System.out.println(mapperUtils.mapToJson(eventResponse.getEvent()));
                    } else {
                        System.out.println(response);
                    }
                    break;
                case 4:
                    System.out.println("------> Ingrese el ID del evento a modificar");
                    int id = scannerNextInt();
                    System.out.println("------> Ingrese los datos a modificar");
                    String json = SCANNER.nextLine();
                    response = eventService.updateEvent(id,json);
                    if (response.getCode() == 200){
                        System.out.println(response);
                        EventResponse eventResponse = (EventResponse) response;
                        System.out.println(mapperUtils.mapToJson(eventResponse.getEvent()));
                    } else {
                        System.out.println(response);
                    }
                    break;
                case 5:
                    System.out.println("------> Ingrese el ID del evento a borrar");
                    response = eventService.deleteEvent(scannerNextInt());
                    System.out.println(response);
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
        try {
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e){
            return -1;
        }

    }

}