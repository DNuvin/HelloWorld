package com.typeb.assignment.adapter.in.web.controller;

import com.typeb.assignment.adapter.in.web.dto.request.HelloRequestDto;
import com.typeb.assignment.adapter.in.web.dto.request.RequestEntityValidator;
import com.typeb.assignment.adapter.in.web.dto.response.ApiResponse;
import com.typeb.assignment.adapter.in.web.dto.response.ResponseTransformer;
import com.typeb.assignment.adapter.in.web.dto.response.transformer.HelloMessageTransformer;
import com.typeb.assignment.application.service.HelloWorldService;
import com.typeb.assignment.domain.model.HelloMessage;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {

    private final HelloWorldService service;
    private final RequestEntityValidator validator;


    public HelloWorldController(HelloWorldService service, RequestEntityValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String,Object>>> sayHello(@RequestParam(required = false) String name){
        HelloRequestDto dto = new HelloRequestDto(name);
        validator.validate(dto); // manually validates all annotations
        HelloMessage msg = service.sayHello(dto.getName());
        return ResponseTransformer.transform(msg, new HelloMessageTransformer());
    }
}
