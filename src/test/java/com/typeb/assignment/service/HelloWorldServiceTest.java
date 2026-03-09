package com.typeb.assignment.service;

import com.typeb.assignment.application.service.HelloWorldService;
import com.typeb.assignment.domain.model.HelloMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldServiceTest {

    private final HelloWorldService service = new HelloWorldService();

    @Test
    void testSayHello() {
        HelloMessage result = service.sayHello("Alice");
        assertNotNull(result);
        assertEquals("Hello Alice", result.getMessage());
    }

}
