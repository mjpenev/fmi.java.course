package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.Duration;
import java.time.LocalDateTime;

public sealed abstract class Vehicle permits Bicycle, MotorVehicle {
    private final String id;
    private final String model;
    private Driver currentDriver;
    private boolean rented;
    private LocalDateTime startRentTime;

    public Vehicle(String id, String model) {
        this.id = id;
        this.model = model;
    }

    public String getId() {
        return this.id;
    }
    public String getModel() {
        return this.model;
    }
    public Driver getCurrentDriver() {
        return this.currentDriver;
    }
    public boolean isRented() {
        return this.rented;
    }
    public LocalDateTime getStartRentTime() {
        return this.startRentTime;
    }

    public void rent(Driver driver, LocalDateTime startRentTime) throws VehicleAlreadyRentedException {
        if (driver == null || startRentTime == null) {
            throw new IllegalArgumentException("Invalid driver or starting rent time.");
        }
        if (this.rented) {
            throw new VehicleAlreadyRentedException("This action is not allowed, since the vehicle is already rented!");
        }

        this.rented = true;
        this.startRentTime = startRentTime;
        this.currentDriver = driver;
    }

    public void returnBack(LocalDateTime rentalEnd) throws InvalidRentingPeriodException, VehicleNotRentedException {
        if (rentalEnd == null) {
            throw new IllegalArgumentException("Illegal date for rental end.");
        }
        if (!this.rented) {
            throw new VehicleNotRentedException("This vehicle hasn't been rented");
        }
        if (rentalEnd.isBefore(this.startRentTime)) {
            throw new InvalidRentingPeriodException("The passed renting period is not valid");
        }

        this.rented = false;
    }

    public abstract double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException;

}
