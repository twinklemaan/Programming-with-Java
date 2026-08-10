
public class Vehicle {

    private final String brand;
    private final String model;
    private final int year;

    Vehicle(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    // ---- INNER CLASS: displays vehicle details ----
    class VehicleDetails {
        void show() {
            System.out.println("Brand : " + brand);
            System.out.println("Model : " + model);
            System.out.println("Year  : " + year);
        }
    }

    // ---- Interface for the action, implemented anonymously ----
    interface VehicleAction {
        void performAction();
    }

    public static void main(String[] args) {
        Vehicle car = new Vehicle("Toyota", "Fortuner", 2024);

        // Using inner class to display details
        Vehicle.VehicleDetails details = car.new VehicleDetails();
        details.show();

        System.out.println();

        // Using anonymous class to perform an action (e.g., starting the vehicle)
        VehicleAction start = new VehicleAction() {
            @Override
            public void performAction() {
                System.out.println(car.brand + " " + car.model + " has started the engine.");
            }
        };
        start.performAction();
    }
}
