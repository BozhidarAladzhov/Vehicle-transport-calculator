package com.example.vehicle_transport_calculator.service.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ExceptionTest {

    @Test
    public void testApiObjectNotFoundException() {
        // Arrange
        String message = "Object not found!";
        Object id = 123;

        // Act
        ApiObjectNotFoundException exception = new ApiObjectNotFoundException(message, id);

        // Assert
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(id, exception.getId());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(404));
    }

    @Test
    public void testObjectNotFoundException() {
        // Arrange
        String message = "Object not found!";
        Object id = 123;

        // Act
        ObjectNotFoundException exception = new ObjectNotFoundException(message, id);

        // Assert
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(id, exception.getId());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(404));
    }

    @Test
    public void testUserNotFoundExceptionWithMessage() {
        // Arrange
        String message = "User not found!";

        // Act
        UserNotFoundException exception = new UserNotFoundException(message);

        // Assert
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void testUserNotFoundExceptionWithMessageAndCause() {
        // Arrange
        String message = "User not found!";
        Throwable cause = new Throwable("Cause of the exception");

        // Act
        UserNotFoundException exception = new UserNotFoundException(message, cause);

        // Assert
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(cause, exception.getCause());
    }

}
