package com.myblog.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentDTO {
    private Long id;

    @NotEmpty(message = "name should not be empty")
    private  String name;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "this email is invalid")
    private  String email;

    @NotEmpty(message = "body should not be empty")
    @Size(min = 10, message = "the body must be 10 characters or more")
    private  String body;

    public CommentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
