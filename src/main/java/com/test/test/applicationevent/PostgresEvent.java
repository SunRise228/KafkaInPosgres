package com.test.test.applicationevent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class PostgresEvent extends ApplicationEvent {
    private String message;

    public PostgresEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
