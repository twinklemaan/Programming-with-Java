
// ---- Interfaces: contracts a product may fulfil ----
interface Discountable {
    double applyDiscount(double percent);
}

interface Shippable {
    double calculateShippingCost();
}

// ---- BASE CLASS: common properties for all products ----
abstract class Product {
    protected String name;
    protected double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    void showBasicInfo() {
        System.out.println("Product: " + name + " | Price: Rs. " + price);
    }
}

// ---- Electronic products: discountable and shippable ----
class ElectronicProduct extends Product implements Discountable, Shippable {
    private double weightKg;

    ElectronicProduct(String name, double price, double weightKg) {
        super(name, price);
        this.weightKg = weightKg;
    }

    @Override
    public double applyDiscount(double percent) {
        return price - (price * percent / 100);
    }

    @Override
    public double calculateShippingCost() {
        return weightKg * 50; // Rs. 50 per kg
    }
}

// ---- Clothing products: discountable, not shippable by weight (flat rate) ----
class ClothingProduct extends Product implements Discountable, Shippable {
    private String size;

    ClothingProduct(String name, double price, String size) {
        super(name, price);
        this.size = size;
    }

    @Override
    public double applyDiscount(double percent) {
        return price - (price * percent / 100);
    }

    @Override
    public double calculateShippingCost() {
        return 40; // flat shipping rate
    }

    void showSize() {
        System.out.println("Size: " + size);
    }
}

// ---- Grocery products: shippable only, no discount policy ----
class GroceryProduct extends Product implements Shippable {
    private String expiryDate;

    GroceryProduct(String name, double price, String expiryDate) {
        super(name, price);
        this.expiryDate = expiryDate;
    }

    @Override
    public double calculateShippingCost() {
        return 20; // flat low-cost shipping for groceries
    }

    void showExpiry() {
        System.out.println("Expiry: " + expiryDate);
    }
}

public class ECommerceProductSystem {
    public static void main(String[] args) {

        ElectronicProduct laptop = new ElectronicProduct("DigiCam", 55000, 2.5);
        ClothingProduct shirt = new ClothingProduct("Shirt", 1200, "L");
        GroceryProduct rice = new GroceryProduct("Rice paper", 650, "12-2026");

        laptop.showBasicInfo();
        System.out.println("After 10% discount: Rs. " + laptop.applyDiscount(10));
        System.out.println("Shipping cost: Rs. " + laptop.calculateShippingCost());
        System.out.println();

        shirt.showBasicInfo();
        shirt.showSize();
        System.out.println("After 20% discount: Rs. " + shirt.applyDiscount(20));
        System.out.println("Shipping cost: Rs. " + shirt.calculateShippingCost());
        System.out.println();

        rice.showBasicInfo();
        rice.showExpiry();
        System.out.println("Shipping cost: Rs. " + rice.calculateShippingCost());
    }
}