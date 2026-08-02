class RestaurantBill {
    // Static variable to track total number of orders across all bill types
    static int totalOrders = 0;

    // Overloaded method - Dine-in bill (includes service charge)
    double calculateBill(double foodAmount, double serviceChargePercent) {
        totalOrders++;
        double serviceCharge = foodAmount * serviceChargePercent / 100;
        return foodAmount + serviceCharge;
    }

    // Overloaded method - Takeaway bill (no service charge, small packaging fee)
    double calculateBill(double foodAmount) {
        totalOrders++;
        double packagingFee = 20.0;
        return foodAmount + packagingFee;
    }

    // Overloaded method - Delivery bill (includes delivery charge + distance factor)
    double calculateBill(double foodAmount, double deliveryCharge, double distanceInKm) {
        totalOrders++;
        double extraPerKm = 5.0;
        double totalDeliveryCharge = deliveryCharge + (distanceInKm * extraPerKm);
        return foodAmount + totalDeliveryCharge;
    }
}

public class RestaurantBillingDemo {
    public static void main(String[] args) {
        RestaurantBill bill = new RestaurantBill();

        // Dine-in order with 10% service charge
        double dineInTotal = bill.calculateBill(1000.0, 10.0);
        System.out.println("Dine-in Bill (Food: Rs.1000, Service: 10%): Rs." + dineInTotal);

        // Takeaway order
        double takeawayTotal = bill.calculateBill(500.0);
        System.out.println("Takeaway Bill (Food: Rs.500 + Packaging): Rs." + takeawayTotal);

        // Delivery order (base delivery charge Rs.30, distance 6 km)
        double deliveryTotal = bill.calculateBill(750.0, 30.0, 6.0);
        System.out.println("Delivery Bill (Food: Rs.750, Base: Rs.30, Distance: 6km): Rs." + deliveryTotal);

        System.out.println("\nTotal Orders Processed: " + RestaurantBill.totalOrders);
    }
}