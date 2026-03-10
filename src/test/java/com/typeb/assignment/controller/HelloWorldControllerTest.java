package com.typeb.assignment.controller;

import com.typeb.assignment.adapter.in.web.controller.HelloWorldController;
import com.typeb.assignment.adapter.in.web.dto.request.HelloRequestDto;
import com.typeb.assignment.adapter.in.web.dto.request.RequestEntityValidator;
import com.typeb.assignment.adapter.in.web.dto.response.ApiResponse;
import com.typeb.assignment.application.service.HelloWorldService;
import com.typeb.assignment.domain.model.HelloMessage;
import com.typeb.assignment.infrastructure.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HelloWorldControllerTest {

    private HelloWorldService service;
    private RequestEntityValidator validator;
    private HelloWorldController controller;

    @BeforeEach
    void setup() {
        service = mock(HelloWorldService.class);
        validator = mock(RequestEntityValidator.class);
        controller = new HelloWorldController(service, validator);
    }

    @Test
    void testSayHello_Success() {

        String name = "Alice";
        HelloMessage message = new HelloMessage("Hello Alice");

        when(service.sayHello(name)).thenReturn(message);

        ResponseEntity<ApiResponse<Map<String, Object>>> response = controller.sayHello(name);

        verify(validator).validate(any(HelloRequestDto.class));
        verify(service).sayHello(name);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Hello Alice", (response.getBody().getData()).get("message"));
    }

    @Test
    void testSayHello_EmptyName_NotBlankValidation() {

        String name = "";
        doThrow(new ValidationException(Map.of("name", List.of("Name is required"))))
                .when(validator).validate(any(HelloRequestDto.class));

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            controller.sayHello(name);
        });

        assertTrue(ex.getErrors().containsKey("name"));
        assertEquals("Name is required", ex.getErrors().get("name").get(0));
    }

    @Test
    void testSayHello_CustomAnnotationFailure() {

        String name = "zebra";
        doThrow(new ValidationException(Map.of("name", List.of("Invalid Input"))))
                .when(validator).validate(any(HelloRequestDto.class));

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            controller.sayHello(name);
        });

        assertTrue(ex.getErrors().containsKey("name"));
        assertEquals("Invalid Input", ex.getErrors().get("name").get(0));
    }

    @Test
    void testSayHello_NullName() {

        String name = null;
        doThrow(new ValidationException(Map.of("name", List.of("Name is required"))))
                .when(validator).validate(any(HelloRequestDto.class));

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            controller.sayHello(name);
        });

        assertTrue(ex.getErrors().containsKey("name"));
    }

    @Test
    void testSayHello_ServiceThrowsRuntimeException() {

        String name = "Alice";
        when(service.sayHello(name)).thenThrow(new RuntimeException("Something went wrong"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            controller.sayHello(name);
        });

        assertEquals("Something went wrong", ex.getMessage());
    }
}