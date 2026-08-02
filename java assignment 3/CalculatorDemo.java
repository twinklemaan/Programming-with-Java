class Calculator {
    // Static variable to count total calculations performed
    static int calculationCount = 0;

    // Overloaded method - addition of two integers
    int add(int a, int b) {
        calculationCount++;
        return a + b;
    }

    // Overloaded method - addition of three integers
    int add(int a, int b, int c) {
        calculationCount++;
        return a + b + c;
    }

    // Overloaded method - addition of two decimals
    double add(double a, double b) {
        calculationCount++;
        return a + b;
    }

    // Overloaded method - addition of three decimals
    double add(double a, double b, double c) {
        calculationCount++;
        return a + b + c;
    }
}

public class CalculatorDemo {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println("Sum of two integers (5, 10): " + calc.add(5, 10));
        System.out.println("Sum of three integers (5, 10, 15): " + calc.add(5, 10, 15));
        System.out.println("Sum of two decimals (5.5, 10.2): " + calc.add(5.5, 10.2));
        System.out.println("Sum of three decimals (5.5, 10.2, 2.3): " + calc.add(5.5, 10.2, 2.3));

        System.out.println("\nTotal calculations performed: " + Calculator.calculationCount);
    }
}