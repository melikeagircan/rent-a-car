package com.example.rentacar.repository;

import com.example.rentacar.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {
    Maintenance findByCarIdAndIsCompletedIsFalse(int carId);
    boolean existsByCarIdAndIsCompletedFalse(int carId);

    Maintenance findMaintenanceByCarIdAndIsCompletedFalse(int carId);
}
