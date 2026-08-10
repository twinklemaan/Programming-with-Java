interface Switchable {
    void turnOn();
}

class Light implements Switchable {
    private String location;

    Light(String location) {
        this.location = location;
    }

    @Override
    public void turnOn() {
        System.out.println(location + " Light is now ON.");
    }
}

class Fan implements Switchable {
    private String location;
    private int speed;

    Fan(String location, int speed) {
        this.location = location;
        this.speed = speed;
    }

    @Override
    public void turnOn() {
        System.out.println(location + " Fan is now ON, running at speed " + speed + ".");
    }
}

public class SwitchableProgram {
    public static void main(String[] args) {
        Switchable livingRoomLight = new Light("Living Room");
        livingRoomLight.turnOn();

        Switchable bedroomFan = new Fan("Bedroom", 3);
        bedroomFan.turnOn();
    }
}
