package com.example.rentacar.business.dto.responses.update;

import com.example.rentacar.entities.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCarResponse {
    private int modelId;
    private int modelYear ;
    private String plate;
    private State state;
    private double dailyPrice;
}
