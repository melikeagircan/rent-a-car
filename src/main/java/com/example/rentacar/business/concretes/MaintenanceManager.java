package com.example.rentacar.business.concretes;

import com.example.rentacar.business.abstracts.CarService;
import com.example.rentacar.business.abstracts.MaintenanceService;
import com.example.rentacar.business.dto.requests.create.CreateMaintenanceRequest;
import com.example.rentacar.business.dto.requests.update.UpdateMaintenanceRequest;
import com.example.rentacar.business.dto.responses.create.CreateMaintenanceResponse;
import com.example.rentacar.business.dto.responses.get.GetAllMaintenanceResponse;
import com.example.rentacar.business.dto.responses.get.GetMaintenanceResponse;
import com.example.rentacar.business.dto.responses.update.UpdateMaintenanceResponse;
import com.example.rentacar.entities.Maintenance;
import com.example.rentacar.entities.State;
import com.example.rentacar.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
public class MaintenanceManager  implements MaintenanceService {
    private final MaintenanceRepository repository;
    private final ModelMapper mapper;
    private final CarService carService;
    @Override
    public List<GetAllMaintenanceResponse> getAll() {
        List<Maintenance> maintenances = repository.findAll();
        List<GetAllMaintenanceResponse> responses = maintenances
                .stream()
                .map(maintenance -> mapper.map(maintenance, GetAllMaintenanceResponse.class))
                .toList();
        return responses;
    }

    @Override
    public GetAllMaintenanceResponse getById(int id) {
        Maintenance maintenance = repository.findById(id).orElseThrow();
        GetAllMaintenanceResponse response = mapper.map(maintenance, GetAllMaintenanceResponse.class);


        return response;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(int carId) {
        checkIfCarIsNotUnderMaintenance(carId);
        Maintenance maintenance = repository.findMaintenanceByCarIdAndIsCompletedFalse(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());
        repository.save(maintenance);
        carService.changeState(carId, State.AVAILABLE);
        GetMaintenanceResponse response = mapper.map(maintenance, GetMaintenanceResponse.class);

        return response;
    }



    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        Maintenance maintenance = mapper.map(request,Maintenance.class);
        maintenance.setId(0);
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setEndDate(null);
        repository.save(maintenance);
        CreateMaintenanceResponse response = mapper.map(maintenance, CreateMaintenanceResponse.class);

        return response;
    }

    @Override
    public UpdateMaintenanceResponse update(int id, UpdateMaintenanceRequest request) {
        Maintenance maintenance = mapper.map(request, Maintenance.class);
        maintenance.setId(id);
        repository.save(maintenance);
        UpdateMaintenanceResponse response = mapper.map(maintenance, UpdateMaintenanceResponse.class);
        return response;
    }
    private void checkIfCarUnderMaintenance(int carId){
        if(repository.existsByCarIdAndIsCompletedFalse(carId)){
            throw new RuntimeException("Araç şuan bakımda!");
        }
    }
    @Override
    public void delete(int id) {

    }
    private void checkIfCarIsNotUnderMaintenance(int carId){
        if (!repository.existsByCarIdAndIsCompletedFalse(carId)){
            throw new RuntimeException("Bakımda böyle bir araç bulunamadı");

        }

    }
    private void checkCarAvailabilityForMaintenance(int carId){
        if (carService.getById(carId).getState().equals(State.RENTED)){
            throw new RuntimeException("Araç kirada olduğu için bakıma alınamaz!");
        }
    }


}
