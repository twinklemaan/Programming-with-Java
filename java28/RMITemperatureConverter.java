

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

// 1. Remote Interface for Temperature Conversion
interface TemperatureConverterService extends Remote {
    double celsiusToFahrenheit(double celsius) throws RemoteException;
    double fahrenheitToCelsius(double fahrenheit) throws RemoteException;
}

// 2. Remote Object Implementation
class TemperatureConverterServiceImpl extends UnicastRemoteObject implements TemperatureConverterService {
    public TemperatureConverterServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public double celsiusToFahrenheit(double celsius) throws RemoteException {
        System.out.printf("[RMI Server] Converting Celsius %.2f°C to Fahrenheit%n", celsius);
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    @Override
    public double fahrenheitToCelsius(double fahrenheit) throws RemoteException {
        System.out.printf("[RMI Server] Converting Fahrenheit %.2f°F to Celsius%n", fahrenheit);
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }
}

// 3. Application Main (Demonstrating Server Binding and Client Remote Invocation)
public class RMITemperatureConverter {
    private static final int RMI_PORT = 1099;
    private static final String SERVICE_NAME = "TemperatureConverterService";

    public static void main(String[] args) {
        System.out.println("=== Assignment 28: Java RMI Temperature Conversion Service ===");

        TemperatureConverterServiceImpl service = null;
        try {
            // Step 1: Access or create RMI registry
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(RMI_PORT);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(RMI_PORT);
            }

            // Step 2: Bind the remote converter service
            service = new TemperatureConverterServiceImpl();
            registry.rebind(SERVICE_NAME, service);
            System.out.println("Server: '" + SERVICE_NAME + "' registered successfully in RMI registry.\n");

            // Step 3: Client retrieves stub from registry
            System.out.println("Client: Looking up '" + SERVICE_NAME + "'...");
            TemperatureConverterService stub = (TemperatureConverterService) registry.lookup(SERVICE_NAME);
            System.out.println("Client: Connected to Remote Service!\n");

            // Step 4: Client invokes temperature conversions
            double[] testCelsius = { 0.0, 25.0, 37.0, 100.0 };
            double[] testFahrenheit = { 32.0, 77.0, 98.6, 212.0 };

            System.out.println("--- Celsius to Fahrenheit Conversions ---");
            for (double c : testCelsius) {
                double f = stub.celsiusToFahrenheit(c);
                System.out.printf("Client: %.2f °C  --->  %.2f °F%n", c, f);
            }

            System.out.println("\n--- Fahrenheit to Celsius Conversions ---");
            for (double f : testFahrenheit) {
                double c = stub.fahrenheitToCelsius(f);
                System.out.printf("Client: %.2f °F  --->  %.2f °C%n", f, c);
            }

            System.out.println("\n=== Temperature Conversion Completed Successfully ===");
        } catch (Exception e) {
            System.err.println("RMI Temperature Converter Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (service != null) {
                try {
                    UnicastRemoteObject.unexportObject(service, true);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
