package com.hrms.hrpitch.common.events;

import org.springframework.context.ApplicationEvent;

public class GenericEvent<T> extends ApplicationEvent {

    private T payload;
    private Integer eventId;

    public GenericEvent(Object source, T payload, Integer eventId) {
        super(source);
        this.payload = payload;
        this.eventId = eventId;
    }

    public T getPayload(){
        return payload;
    }

    public Integer getEventId(){
        return eventId;
    }
}
