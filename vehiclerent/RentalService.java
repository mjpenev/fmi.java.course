package bg.sofia.uni.fmi.mjt.vehiclerent;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.vehicle.Vehicle;

import java.time.LocalDateTime;

public class RentalService {

    public void rentVehicle(Driver driver, Vehicle vehicle, LocalDateTime startOfRent)
            throws IllegalArgumentException, VehicleAlreadyRentedException {
        vehicle.rent(driver, startOfRent);
    }

    public double returnVehicle(Vehicle vehicle, LocalDateTime endOfRent)
            throws IllegalArgumentException, InvalidRentingPeriodException, VehicleNotRentedException {
        vehicle.returnBack(endOfRent);
        return vehicle.calculateRentalPrice(vehicle.getStartRentTime(), endOfRent);
    }
}
