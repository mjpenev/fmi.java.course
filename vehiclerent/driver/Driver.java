package bg.sofia.uni.fmi.mjt.vehiclerent.driver;

public record Driver(AgeGroup group) {
    public Driver(AgeGroup group){
        this.group = group;
    }
}
