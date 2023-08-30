package com.shoppingms.notificationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor
public class NotificationEvent {
    private Date date;
    private String code;
    private String productName;
    private boolean status;
}
