// Exercise 2 - Vehicle Insurance System
// Child insurance classes access parent Vehicle information using the super keyword.

class Vehicle {
    protected String registrationNumber;
    protected String type;
    protected double vehicleValue;

    Vehicle(String registrationNumber, String type, double vehicleValue) {
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.vehicleValue = vehicleValue;
    }

    void showVehicleInfo() {
        System.out.println("Reg No       : " + registrationNumber);
        System.out.println("Type         : " + type);
        System.out.println("Vehicle Value: Rs. " + vehicleValue);
    }
}

// ---- Child class: Two-Wheeler Insurance ----
class TwoWheelerInsurance extends Vehicle {

    TwoWheelerInsurance(String registrationNumber, double vehicleValue) {
        super(registrationNumber, "Two-Wheeler", vehicleValue);
    }

    double calculatePremium() {
        // accesses parent's vehicleValue via super keyword
        return super.vehicleValue * 0.03; // 3% premium
    }

    void showPolicy() {
        super.showVehicleInfo(); // reuse parent's display logic
        System.out.println("Premium      : Rs. " + calculatePremium());
    }
}

// ---- Child class: Four-Wheeler Insurance ----
class FourWheelerInsurance extends Vehicle {

    FourWheelerInsurance(String registrationNumber, double vehicleValue) {
        super(registrationNumber, "Four-Wheeler", vehicleValue);
    }

    double calculatePremium() {
        return super.vehicleValue * 0.05; // 5% premium
    }

    void showPolicy() {
        super.showVehicleInfo();
        System.out.println("Premium      : Rs. " + calculatePremium());
    }
}

public class VehicleInsuranceSystem {
    public static void main(String[] args) {
        TwoWheelerInsurance bike = new TwoWheelerInsurance("MH12AB1234", 80000);
        FourWheelerInsurance car = new FourWheelerInsurance("MH14CD5678", 700000);

        System.out.println("--- Two-Wheeler Policy ---");
        bike.showPolicy();

        System.out.println("\n--- Four-Wheeler Policy ---");
        car.showPolicy();
    }
}