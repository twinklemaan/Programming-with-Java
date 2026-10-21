
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoSystem {
    private static final int PORT = 6500;

    // Echo Server Implementation
    public static void runServer() {
        System.out.println("[Echo Server] Starting Echo Server on port " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Echo Server] Listening for incoming client connection...");
            Socket socket = serverSocket.accept();
            System.out.println("[Echo Server] Connection accepted from: " + socket.getRemoteSocketAddress());

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[Echo Server] Received from client: \"" + line + "\"");
                if ("EXIT".equalsIgnoreCase(line.trim())) {
                    System.out.println("[Echo Server] Client requested termination.");
                    writer.println("Connection terminating. Goodbye!");
                    break;
                }
                // Echo the message back to client
                String echoResponse = "Server Echo: " + line;
                writer.println(echoResponse);
                System.out.println("[Echo Server] Echoed back: \"" + echoResponse + "\"");
            }

            socket.close();
            System.out.println("[Echo Server] Closed.");
        } catch (IOException e) {
            System.out.println("[Echo Server Exception]: " + e.getMessage());
        }
    }

    // Echo Client Implementation
    public static void runClient() {
        try {
            Thread.sleep(600); // give server time to initialize
            System.out.println("[Echo Client] Connecting to Echo Server at localhost:" + PORT + "...");
            Socket socket = new Socket("localhost", PORT);
            System.out.println("[Echo Client] Successfully connected to Echo Server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String[] testMessages = {
                "Hello, Server!",
                "Testing Java Socket Echo System",
                "Assignment 27 Exercise 2",
                "Ping 123",
                "EXIT"
            };

            for (String msg : testMessages) {
                System.out.println("\n[Echo Client] Sending: \"" + msg + "\"");
                out.println(msg);

                String response = in.readLine();
                System.out.println("[Echo Client] Received: \"" + response + "\"");
                Thread.sleep(500);
            }

            socket.close();
            System.out.println("[Echo Client] Socket closed.");
        } catch (Exception e) {
            System.out.println("[Echo Client Exception]: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Assignment 27: Socket Programming Echo System ===");

        if (args.length > 0 && args[0].equalsIgnoreCase("server")) {
            runServer();
        } else if (args.length > 0 && args[0].equalsIgnoreCase("client")) {
            runClient();
        } else {
            // Standalone multi-threaded simulation
            System.out.println("Starting concurrent Echo Server and Echo Client demonstration...\n");

            Thread serverThread = new Thread(EchoSystem::runServer, "EchoServer-Thread");
            Thread clientThread = new Thread(EchoSystem::runClient, "EchoClient-Thread");

            serverThread.start();
            clientThread.start();

            try {
                serverThread.join();
                clientThread.join();
            } catch (InterruptedException e) {
                System.out.println("Execution interrupted: " + e.getMessage());
            }

            System.out.println("\n=== Echo System Demonstration Completed Successfully ===");
        }
    }
}
