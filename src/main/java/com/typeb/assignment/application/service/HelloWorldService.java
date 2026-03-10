package com.typeb.assignment.application.service;

import com.typeb.assignment.domain.model.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldService.class);

    /**
     * Generate a greeting message.
     * Assumes the input is already validated at the controller layer.
     *
     * @param name Validated username
     * @return HelloMessage domain object
     */
    public HelloMessage sayHello(String name) {
        String traceId = MDC.get("traceId");
        log.debug("Generating greeting message for name='{}' [traceId={}]", name, traceId);

        String formatted = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        HelloMessage message = new HelloMessage("Hello " + formatted);

        log.info("Generated greeting message [traceId={}]: {}", traceId, message.getMessage());
        return message;
    }
}