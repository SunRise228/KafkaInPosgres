package com.test.test.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@ToString
@Table(name = "message")
public class Message {

    @Getter
    @Setter
    @Id
    private UUID id;
    private String country;
    private String name;
    private String tin;

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getTin() {
        return tin;
    }

    public Message(UUID id, String country, String name, String tin) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.tin = tin;
    }
    public Message() {}

}