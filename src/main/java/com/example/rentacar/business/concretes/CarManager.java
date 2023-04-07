package com.example.rentacar.business.concretes;

import com.example.rentacar.business.abstracts.CarService;
import com.example.rentacar.business.dto.requests.create.CreateCarRequest;
import com.example.rentacar.business.dto.requests.update.UpdateCarRequest;
import com.example.rentacar.business.dto.responses.create.CreateCarResponse;
import com.example.rentacar.business.dto.responses.get.GetAllCarsResponse;
import com.example.rentacar.business.dto.responses.get.GetCarResponse;
import com.example.rentacar.business.dto.responses.update.UpdateCarResponse;
import com.example.rentacar.entities.Car;
import com.example.rentacar.entities.State;
import com.example.rentacar.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.Streams.stream;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
    private final CarRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<GetAllCarsResponse> getAll() {
        List<Car> cars = repository.findAll();
        List<GetAllCarsResponse> response = cars
                .stream()
                .map(car-> mapper.map(car, GetAllCarsResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetCarResponse getById(int id) {
        Car car = repository.findById(id).orElseThrow();
        GetCarResponse response = mapper.map(car,GetCarResponse.class);
        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        Car car = mapper.map(request, Car.class);
        car.setId(0);
        car.setState(State.AVAILABLE);
        repository.save(car);
        CreateCarResponse response = mapper.map(car,CreateCarResponse.class);
        return response;
    }

    @Override
    public UpdateCarResponse update(int id, UpdateCarRequest request) {
        Car car = mapper.map(request, Car.class);
        car.setId(id);

        repository.save(car);
        UpdateCarResponse response = mapper.map(car, UpdateCarResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);

    }
}
