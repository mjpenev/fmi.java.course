package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Caravan extends MotorVehicle {
    private int numberOfBeds;

    public Caravan(String id, String model, FuelType fuelType, int numberOfSeats, int numberOfBeds, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model, fuelType, numberOfSeats, pricePerWeek, pricePerDay, pricePerHour);
        this.numberOfBeds = numberOfBeds;
    }

    @Override
    public void returnBack(LocalDateTime rentalEnd) throws InvalidRentingPeriodException, VehicleNotRentedException {
        if (rentalEnd.isBefore(getStartRentTime().plusDays(1))) {
            throw new InvalidRentingPeriodException("Caravan cannot be rented for less than a day.");
        }
        super.returnBack(rentalEnd);
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if (endOfRent.isBefore(startOfRent.plusDays(1))) {
            throw new InvalidRentingPeriodException("Caravan cannot be rented for less than a day.");
        }
        return super.calculateRentalPrice(startOfRent, endOfRent) + this.numberOfBeds * 10;
    }
}
