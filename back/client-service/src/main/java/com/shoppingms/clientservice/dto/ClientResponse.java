package com.shoppingms.clientservice.dto;

import com.shoppingms.clientservice.model.Adress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ClientResponse {
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
