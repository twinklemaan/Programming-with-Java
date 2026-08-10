
public class FoodDeliveryApp {

    private final String customerName;
    private final String foodItem;
    private final double price;

    FoodDeliveryApp(String customerName, String foodItem, double price) {
        this.customerName = customerName;
        this.foodItem = foodItem;
        this.price = price;
    }

    // ---- INNER CLASS: handles order details ----
    class OrderDetails {
        void showOrder() {
            System.out.println("Customer : " + customerName);
            System.out.println("Item     : " + foodItem);
            System.out.println("Price    : Rs. " + price);
        }
    }

    // ---- Interface for delivery status updates ----
    interface DeliveryStatus {
        void updateStatus();
    }

    public static void main(String[] args) {
        FoodDeliveryApp order =
                new FoodDeliveryApp("Twinkle", "Crab Sushi", 1299.00);

        // Inner class usage
        FoodDeliveryApp.OrderDetails orderDetails = order.new OrderDetails();
        orderDetails.showOrder();

        System.out.println();

        // Anonymous class #1 - order placed
        DeliveryStatus placed = new DeliveryStatus() {
            @Override
            public void updateStatus() {
                System.out.println("Status: Order placed successfully.");
            }
        };

        // Anonymous class #2 - out for delivery
        DeliveryStatus outForDelivery = new DeliveryStatus() {
            @Override
            public void updateStatus() {
                System.out.println("Status: Order is out for delivery.");
            }
        };

        // Anonymous class #3 - delivered
        DeliveryStatus delivered = new DeliveryStatus() {
            @Override
            public void updateStatus() {
                System.out.println("Status: Order delivered successfully.");
            }
        };

        placed.updateStatus();
        outForDelivery.updateStatus();
        delivered.updateStatus();
    }
}
