package com.shoppingms.clientservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@Document(value = "client")
public class Client {
    @Id
    private String id;
    private Instant createdDate;
    private String code;
    private String firstname;
    private String lastname;
    private String email;
    private String photo;
    private String phoneNumber;
    private Adress adress;
}
