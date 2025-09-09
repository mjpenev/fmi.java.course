package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Bicycle extends Vehicle {
    private double pricePerDay;
    private double pricePerHour;

    public Bicycle(String id, String model, double pricePerDay, double pricePerHour){
        super(id, model);
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    @Override
    public void returnBack(LocalDateTime rentalEnd) throws InvalidRentingPeriodException, VehicleNotRentedException {
        if (rentalEnd.isAfter(getStartRentTime().plusDays(7))) {
            throw new InvalidRentingPeriodException("Bike has been rented for longer than a week.");
        }
        super.returnBack(rentalEnd);
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if (endOfRent.isBefore(startOfRent) || endOfRent.isAfter(startOfRent.plusDays(7))) {
            throw new InvalidRentingPeriodException("Bike cannot be rented for longer than a week");
        }
        if (Duration.between(startOfRent, endOfRent).toHours() < 1) {
            return this.pricePerHour;
        }
        else if (Duration.between(startOfRent, endOfRent).toHours() < 24) {
            return this.pricePerHour * Duration.between(startOfRent, endOfRent).toHours();
        }
        else if (Duration.between(startOfRent, endOfRent).toHours() == 24) {
            return this.pricePerDay * (Duration.between(startOfRent, endOfRent).toDays());
        }
        else {
            return this.pricePerDay * (Duration.between(startOfRent, endOfRent).toDays() + 1);
        }
    }
}
