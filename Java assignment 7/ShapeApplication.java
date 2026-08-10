
abstract class Shape {
    String name;

    Shape(String name) {
        this.name = name;
    }

    // abstract method - each subclass must define its own area calculation
    abstract double calculateArea();

    void display() {
        System.out.println(name + " area = " + calculateArea());
    }
}

class Circle extends Shape {
    private double radius;

    Circle(double radius) {
        super("Circle");
        this.radius = radius;
    }

    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private final double length;
    private final double breadth;

    Rectangle(double length, double breadth) {
        super("Rectangle");
        this.length = length;
        this.breadth = breadth;
    }

    @Override
    double calculateArea() {
        return length * breadth;
    }
}

public class ShapeApplication {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(4, 6);

        circle.display();
        rectangle.display();
    }
}
