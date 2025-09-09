package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public sealed abstract class MotorVehicle extends Vehicle permits Car, Caravan {
    private FuelType fuelType;
    private int numberOfSeats;
    private double pricePerWeek;
    private double pricePerDay;
    private double pricePerHour;

    public MotorVehicle(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);
        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerWeek = pricePerWeek;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }
    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }
    public double getPricePerWeek() {
        return this.pricePerWeek;
    }
    public double getPricePerDay() {
        return this.pricePerDay;
    }
    public double getPricePerHour() {
        return this.pricePerHour;
    }
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    public void setPricePerWeek(double pricePerWeek) {
        this.pricePerWeek = pricePerWeek;
    }
    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if (endOfRent.isBefore(startOfRent)) {
            throw new InvalidRentingPeriodException("Invalid period, cannot calculate price.");
        }

        long hours = Duration.between(startOfRent, endOfRent).toHours();
        long days = Duration.between(startOfRent, endOfRent).toDays();

        double basePrice;

        if (hours < 1) {
            basePrice = this.getPricePerHour();
        } else if (hours < 24) {
            basePrice = this.getPricePerHour() * hours;
        } else if (days < 7) {
            long fullDays = (hours % 24 == 0) ? days : days + 1;
            basePrice = this.getPricePerDay() * fullDays;
        } else {
            long fullWeeks = (days % 7 == 0) ? days / 7 : (days / 7) + 1;
            basePrice = this.getPricePerWeek() * fullWeeks;
        }

        double seatFee = this.getNumberOfSeats() * 5;
        double fuelTax = getFuelType().getTax() * days;

        return basePrice + seatFee + fuelTax;
    }
}
