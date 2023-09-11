package com.shoppingms.clientservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Adress {
    private String adress1;
    private String adress2;
    private String city;
    private String country;
}
