package com.ann.truckApp.events.listenersRequest;

import com.ann.truckApp.domain.model.Users;
import lombok.*;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventRegister extends ApplicationEvent {
    private final String otp;
    private Users users;
    public EventRegister(Users users,String otp) {
        super(users);
        this.users=users;
        this.otp=otp;
    }

}
