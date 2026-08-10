
abstract class FoodOrder {
    protected String customerName;
    protected double foodCost;

    FoodOrder(String customerName, double foodCost) {
        this.customerName = customerName;
        this.foodCost = foodCost;
    }

    // abstract method — each order type calculates its bill differently
    abstract double calculateBill();

    // concrete method — common to all order types
    void displayBill() {
        System.out.println("Customer   : " + customerName);
        System.out.println("Food Cost  : Rs. " + foodCost);
        System.out.println("Total Bill : Rs. " + calculateBill());
    }
}

class DineInOrder extends FoodOrder {
    private static final double SERVICE_CHARGE_RATE = 0.10; // 10% service charge
    private static final double GST_RATE = 0.05;             // 5% GST

    DineInOrder(String customerName, double foodCost) {
        super(customerName, foodCost);
    }

    @Override
    double calculateBill() {
        double serviceCharge = foodCost * SERVICE_CHARGE_RATE;
        double gst = foodCost * GST_RATE;
        return foodCost + serviceCharge + gst;
    }
}

class TakeAwayOrder extends FoodOrder {
    private static final double PACKAGING_CHARGE = 20.0; // flat packaging fee
    private static final double GST_RATE = 0.05;          // 5% GST

    TakeAwayOrder(String customerName, double foodCost) {
        super(customerName, foodCost);
    }

    @Override
    double calculateBill() {
        double gst = foodCost * GST_RATE;
        return foodCost + PACKAGING_CHARGE + gst;
    }
}

public class FoodOrderProgram {
    public static void main(String[] args) {
        // FoodOrder order = new FoodOrder("Test", 100); // ERROR: cannot instantiate an abstract class

        FoodOrder order1 = new DineInOrder("Ananya Rao", 800.00);
        System.out.println("---- Dine-In Order ----");
        order1.displayBill();

        System.out.println();

        FoodOrder order2 = new TakeAwayOrder("Rohan Mehta", 500.00);
        System.out.println("---- Takeaway Order ----");
        order2.displayBill();
    }
}