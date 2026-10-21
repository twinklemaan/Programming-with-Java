

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

// 1. Remote Interface
interface CalculatorService extends Remote {
    double add(double a, double b) throws RemoteException;
    double subtract(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
    double divide(double a, double b) throws RemoteException;
}

// 2. Remote Implementation
class CalculatorServiceImpl extends UnicastRemoteObject implements CalculatorService {
    public CalculatorServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        System.out.println("[RMI Server] Executing add(" + a + ", " + b + ")");
        return a + b;
    }

    @Override
    public double subtract(double a, double b) throws RemoteException {
        System.out.println("[RMI Server] Executing subtract(" + a + ", " + b + ")");
        return a - b;
    }

    @Override
    public double multiply(double a, double b) throws RemoteException {
        System.out.println("[RMI Server] Executing multiply(" + a + ", " + b + ")");
        return a * b;
    }

    @Override
    public double divide(double a, double b) throws RemoteException {
        System.out.println("[RMI Server] Executing divide(" + a + ", " + b + ")");
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not permitted.");
        }
        return a / b;
    }
}

// 3. Application Main (Host Registry, Server, and Client Stub Demonstration)
public class RMICalculator {
    private static final int RMI_PORT = 1099;
    private static final String SERVICE_NAME = "CalculatorService";

    public static void main(String[] args) {
        System.out.println("=== Assignment 28: Java RMI Remote Calculator Service ===");

        CalculatorServiceImpl serverObject = null;
        try {
            // Step 1: Create/Get RMI Registry
            System.out.println("Initializing RMI Registry on port " + RMI_PORT + "...");
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(RMI_PORT);
            } catch (RemoteException e) {
                // If registry is already running, get existing registry
                registry = LocateRegistry.getRegistry(RMI_PORT);
            }

            // Step 2: Bind the remote object (Server registration)
            serverObject = new CalculatorServiceImpl();
            registry.rebind(SERVICE_NAME, serverObject);
            System.out.println("Server: '" + SERVICE_NAME + "' bound into RMI registry.\n");

            // Step 3: Client looks up the remote object stub
            System.out.println("Client: Looking up '" + SERVICE_NAME + "' from registry...");
            CalculatorService stub = (CalculatorService) registry.lookup(SERVICE_NAME);
            System.out.println("Client: Remote stub obtained successfully!\n");

            // Step 4: Client invokes methods remotely on the stub
            double num1 = 24.0;
            double num2 = 6.0;

            System.out.println("--- Remote Invocations ---");
            System.out.printf("Client Invoking Remote add(%.1f, %.1f)      -> Result: %.2f%n", num1, num2, stub.add(num1, num2));
            System.out.printf("Client Invoking Remote subtract(%.1f, %.1f) -> Result: %.2f%n", num1, num2, stub.subtract(num1, num2));
            System.out.printf("Client Invoking Remote multiply(%.1f, %.1f) -> Result: %.2f%n", num1, num2, stub.multiply(num1, num2));
            System.out.printf("Client Invoking Remote divide(%.1f, %.1f)   -> Result: %.2f%n", num1, num2, stub.divide(num1, num2));

            System.out.println("\n=== RMI Calculator Demonstration Completed Successfully ===");
        } catch (Exception e) {
            System.err.println("RMI Calculator Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (serverObject != null) {
                try {
                    UnicastRemoteObject.unexportObject(serverObject, true);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
