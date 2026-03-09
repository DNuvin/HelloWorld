package com.typeb.assignment.adapter.in.web.controller;

import com.typeb.assignment.adapter.in.web.dto.request.HelloRequestDto;
import com.typeb.assignment.adapter.in.web.dto.request.RequestEntityValidator;
import com.typeb.assignment.adapter.in.web.dto.response.ApiResponse;
import com.typeb.assignment.adapter.in.web.dto.response.ResponseTransformer;
import com.typeb.assignment.adapter.in.web.dto.response.transformer.HelloMessageTransformer;
import com.typeb.assignment.application.service.HelloWorldService;
import com.typeb.assignment.domain.model.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    private final HelloWorldService service;
    private final RequestEntityValidator validator;

    public HelloWorldController(HelloWorldService service, RequestEntityValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    /**
     * Generate a greeting message.
     * Assumes the input is already validated at the controller layer.
     *
     * @param name Validated username
     * @return HelloMessage domain object wrapped in ApiResponse
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> sayHello(@RequestParam(required = false) String name){
        String traceId = MDC.get("traceId");

        log.info("📥 Received sayHello request [traceId={}]", traceId);
        log.debug("Request param: name={}", name);

        HelloRequestDto dto = new HelloRequestDto(name);
        validator.validate(dto);

        HelloMessage msg = service.sayHello(dto.getName());
        log.info("📤 Returning sayHello response [traceId={}]: {}", traceId, msg.getMessage());
        log.debug("Transformed response: {}", msg);

        return ResponseTransformer.transform(msg, new HelloMessageTransformer());
    }
}