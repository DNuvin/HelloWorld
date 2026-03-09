package com.typeb.assignment.application.service;

import com.typeb.assignment.domain.model.HelloMessage;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    /**
     * Generate a greeting message.
     * Assumes the input is already validated at the controller layer.
     *
     * @param name Validated username
     * @return HelloMessage domain object
     */
    public HelloMessage sayHello(String name) {
        // Format name: first letter uppercase, rest lowercase
        String formatted = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return new HelloMessage("Hello " + formatted);
    }
}