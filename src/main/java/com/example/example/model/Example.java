package com.example.example.model;

public class Example {
    private Long id;
    private String message;

    public Example(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Example() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
