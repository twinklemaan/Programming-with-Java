class MobilePhone {
    String brand;
    String model;
    double price;
    int quantity;

    // Default constructor
    MobilePhone() {
        brand = "Unbranded";
        model = "N/A";
        price = 0.0;
        quantity = 0;
    }

    // Parameterized constructor
    MobilePhone(String brand, String model, double price, int quantity) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    // Copy constructor
    MobilePhone(MobilePhone m) {
        this.brand = m.brand;
        this.model = m.model;
        this.price = m.price;
        this.quantity = m.quantity;
    }

    void display() {
        System.out.println("Brand: " + brand + ", Model: " + model +
                ", Price: Rs." + price + ", Quantity: " + quantity);
    }
}

class MobileInventory {
    public static void main(String[] args) {
        // Using default constructor
        MobilePhone m1 = new MobilePhone();
        System.out.println("Phone 1 (Default Constructor):");
        m1.display();

        // Using parameterized constructor
        MobilePhone m2 = new MobilePhone("Samsung", "Galaxy S24", 74999.00, 15);
        System.out.println("\nPhone 2 (Parameterized Constructor):");
        m2.display();

        // Using copy constructor to duplicate a record
        MobilePhone m3 = new MobilePhone(m2);
        System.out.println("\nPhone 3 (Copy of Phone 2 using Copy Constructor):");
        m3.display();

        // Modifying the copied object to show it's an independent record
        m3.quantity = 5;
        System.out.println("\nAfter updating Phone 3's quantity:");
        System.out.println("Phone 2:");
        m2.display();
        System.out.println("Phone 3:");
        m3.display();
    }
}