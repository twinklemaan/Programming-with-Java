abstract class Payment {
    protected double amount;

    Payment(double amount) {
        this.amount = amount;
    }

    // abstract method — must be implemented by every subclass
    abstract void processPayment();

    // concrete method — shared implementation, inherited as-is
    void showAmount() {
        System.out.println("Amount to Pay: Rs. " + amount);
    }
}

class CreditCardPayment extends Payment {
    private String cardNumber;

    CreditCardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    void processPayment() {
        System.out.println("Processing Credit Card payment...");
        System.out.println("Card No: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Payment Successful via Credit Card.");
    }
}

class UpiPayment extends Payment {
    private String upiId;

    UpiPayment(double amount, String upiId) {
        super(amount);
        this.upiId = upiId;
    }

    @Override
    void processPayment() {
        System.out.println("Processing UPI payment...");
        System.out.println("UPI ID: " + upiId);
        System.out.println("Payment Successful via UPI.");
    }
}

public class PaymentSystemProgram {
    public static void main(String[] args) {
        // Payment p = new Payment(500);  // ERROR: cannot instantiate an abstract class

        Payment payment1 = new CreditCardPayment(2500.00, "4521369870123456");
        payment1.showAmount();
        payment1.processPayment();

        System.out.println();

        Payment payment2 = new UpiPayment(750.50, "twinkle@upi");
        payment2.showAmount();
        payment2.processPayment();
    }
}
