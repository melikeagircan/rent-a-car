package com.example.rentacar.business.dto.responses.create;

import com.example.rentacar.entities.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class CreateCarResponse {
    private int modelId;
    private int modelYear ;
    private String plate;
    private State state;
    private double dailyPrice;
}
