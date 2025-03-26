package com.example.vehicle_transport_calculator.init;

import com.example.vehicle_transport_calculator.model.entity.VehicleType;
import com.example.vehicle_transport_calculator.model.enums.VehicleTypeEnum;
import com.example.vehicle_transport_calculator.repo.VehicleTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitVehicleTypes implements CommandLineRunner {

    private final Map<VehicleTypeEnum, String> vehicleTypeDescription = Map.of(
            VehicleTypeEnum.CAR, "Small cars. Max length 5.0 meters with max height 1.5 meters.",
            VehicleTypeEnum.SUV,"Small SUV. Max length 5.0 meters with max height 1.7 meters.",
            VehicleTypeEnum.LARGESUV,"Large SUV. Max length 5.5 meters with max height 2.0 meters.",
            VehicleTypeEnum.PICKUP, "Large pickup trucks. Max length 6.0 meters with max height 2.1 meters.",
            VehicleTypeEnum.MOTORCYCLE, "Any size of motorcycle.",
            VehicleTypeEnum.ATV, "Small ATV, for 1 person.",
            VehicleTypeEnum.UTV, "Larger than ATV, for 2 or more persons."
    );

    private final VehicleTypeRepository vehicleTypeRepository;

    public InitVehicleTypes(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        long count = vehicleTypeRepository.count();

        if (count == 0){
            for (VehicleTypeEnum vehicleTypeEnum:vehicleTypeDescription.keySet()) {
                VehicleType vehicleType = new VehicleType(vehicleTypeEnum, vehicleTypeDescription.get(vehicleTypeEnum));
                vehicleTypeRepository.save(vehicleType);
            }
        }

    }
}
