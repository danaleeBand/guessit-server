package com.danaleeband.guessit.global;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RoomListEvent extends ApplicationEvent {

    private final String message;

    public RoomListEvent(String message) {
        super(message);
        this.message = message;
    }
}
