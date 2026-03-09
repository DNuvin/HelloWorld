package com.typeb.assignment.adapter.in.web.dto.request;
import com.typeb.assignment.adapter.in.web.validator.FirstHalfAlphabet;
import jakarta.validation.constraints.NotBlank;

public class HelloRequestDto implements RequestEntityInterface{

    @NotBlank(message = "Name is required")
    @FirstHalfAlphabet
    private String name;

    public HelloRequestDto() {}

    public HelloRequestDto(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = (name != null) ? name.trim() : null; }

}
