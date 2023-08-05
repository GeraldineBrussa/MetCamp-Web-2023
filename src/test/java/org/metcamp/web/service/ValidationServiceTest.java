package org.metcamp.web.service;

import org.junit.jupiter.api.*;
import org.metcamp.web.exceptions.ValidationException;

import static org.junit.jupiter.api.Assertions.*;
public class ValidationServiceTest {
    private static final ValidationService service = new ValidationService();
    @Test
    @DisplayName("Probando el método validateId con 0")
    void validateIdTestWithIdZero() {
        //given - dado --> configurar datos
        int id  = 0;
        //when - cuando --> ejecutar el método //then - entonces --> validar el resultado
        ValidationException e = assertThrows(ValidationException.class, () -> service.validateId(id));
        assertEquals("id must not be zero", e.getMessage());
    }

    @Test
    @DisplayName("Probando el método validateId con 1 - Happy path")
    void validateIdTestWithIdNotZero() {
        int id  = 1;
        assertDoesNotThrow(() -> service.validateId(id));
    }

    @Test
    @DisplayName("Probando el método validateId con valor negativo")
    void validateIdTestWithIdNegative() {
        int id  = -1;
        ValidationException e = assertThrows(ValidationException.class, () -> service.validateId(id));
        assertEquals("id must be positive", e.getMessage());
    }

    @Test
    @DisplayName("Probando el método validateAttendees con 1 - Happy path")
    void validateAttendeesTestWithNotZero() {
        //given
        int cantidad  = 1;
        // Then (Expecting no exception to be thrown)
        assertDoesNotThrow(() -> service.validateAttendees(cantidad));
    }

    @Test
    @DisplayName("Probando el método validateAttendees con -1")
    void validateAttendeesTestWithNegative() {
        int cantidad  = -1;
        // Then (Expecting a ValidationException to be thrown)
        assertThrows(ValidationException.class, () -> service.validateAttendees(cantidad));
    }

    @Test
    @DisplayName("Probando el método validateAttendees con 0")
    void validateAttendeesTestWithZero() {
        int cantidad  = 0;
        assertThrows(ValidationException.class, () -> service.validateAttendees(cantidad));
    }
}
