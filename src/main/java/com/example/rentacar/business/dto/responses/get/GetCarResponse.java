package com.example.rentacar.business.dto.responses.get;

import com.example.rentacar.entities.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCarResponse {
    private int modelId;
    private int modelYear ;
    private String plate;
    private State state;
    private double dailyPrice;
  //   private String modelName;
  //  private String modelBrandName;
}
