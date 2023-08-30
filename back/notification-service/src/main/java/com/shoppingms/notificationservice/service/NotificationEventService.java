package com.shoppingms.notificationservice.service;

import com.shoppingms.notificationservice.entities.NotificationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class NotificationEventService {

    @Bean
    public Consumer<NotificationEvent> notificationEventConsumer(){
        return (input)-> {
            System.out.println("*********************");
            System.out.println(input.toString());
        };
    }
}
