/*
 * ASSIGNMENT No: 27 - Exercise 1
 * TITLE: Develop a Java application demonstrating socket programming for communication between a client and a server.
 * EXERCISE 1: Create a simple client-server chat application using sockets.
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientServerChat {
    private static final int PORT = 5000;

    // --- Server Logic ---
    public static void runServer() {
        System.out.println("[Server] Starting Chat Server on port " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server] Server is waiting for a client to connect...");
            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 Scanner scanner = new Scanner(System.in)) {

                System.out.println("[Server] Client connected: " + socket.getRemoteSocketAddress());
                System.out.println("[Server] Type messages to send to the client (Type 'bye' to exit):\n");

                String clientMsg;
                while ((clientMsg = in.readLine()) != null) {
                    System.out.println("[Client]: " + clientMsg);
                    if (clientMsg.equalsIgnoreCase("bye")) {
                        out.println("Goodbye! Closing chat.");
                        System.out.println("[Server] Client closed the chat.");
                        break;
                    }

                    System.out.print("[You (Server)]: ");
                    String serverMsg = scanner.nextLine();
                    out.println(serverMsg);
                    if (serverMsg.equalsIgnoreCase("bye")) {
                        System.out.println("[Server] Server closed the chat.");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[Server Error]: " + e.getMessage());
        }
    }

    // --- Client Logic ---
    public static void runClient() {
        System.out.println("[Client] Connecting to Chat Server at localhost:" + PORT + "...");
        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("[Client] Connected to server successfully!");
            System.out.println("[Client] Type messages to send to the server (Type 'bye' to exit):\n");

            while (true) {
                System.out.print("[You (Client)]: ");
                String clientMsg = scanner.nextLine();
                out.println(clientMsg);

                if (clientMsg.equalsIgnoreCase("bye")) {
                    System.out.println("[Client] Exiting chat...");
                    break;
                }

                String serverResponse = in.readLine();
                if (serverResponse == null) {
                    System.out.println("[Client] Server closed the connection.");
                    break;
                }
                System.out.println("[Server]: " + serverResponse);
                if (serverResponse.equalsIgnoreCase("bye")) {
                    System.out.println("[Client] Server ended the chat session.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("[Client Error]: Could not connect to server. Ensure server is running first! Details: " + e.getMessage());
        }
    }

    // --- Automated Demo (runs both server and client threads simultaneously) ---
    public static void runDemo() {
        System.out.println("Running automated concurrent Client-Server Chat Demo...\n");

        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("[Server] Accepted connection from: " + socket.getRemoteSocketAddress());
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("  [Server Received from Client]: \"" + msg + "\"");
                    if (msg.equalsIgnoreCase("bye")) {
                        out.println("Server says: Goodbye!");
                        break;
                    }
                    Thread.sleep(300);
                    out.println("Server reply: Received your message -> [" + msg + "]");
                }
            } catch (Exception e) {
                System.err.println("[Demo Server]: " + e.getMessage());
            }
        });

        Thread clientThread = new Thread(() -> {
            try {
                Thread.sleep(500); // Wait for server to bind
                try (Socket socket = new Socket("localhost", PORT);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String[] chatScript = {
                        "Hello Server, this is the client!",
                        "How is socket programming going?",
                        "Everything looks great!",
                        "bye"
                    };

                    for (String text : chatScript) {
                        System.out.println("[Client Sending to Server]: \"" + text + "\"");
                        out.println(text);
                        String reply = in.readLine();
                        System.out.println("  [Client Received from Server]: \"" + reply + "\"\n");
                        Thread.sleep(400);
                    }
                }
            } catch (Exception e) {
                System.err.println("[Demo Client]: " + e.getMessage());
            }
        });

        serverThread.start();
        clientThread.start();

        try {
            serverThread.join();
            clientThread.join();
        } catch (InterruptedException e) {
            System.err.println("Demo interrupted: " + e.getMessage());
        }

        System.out.println("Demo finished successfully.");
    }

    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println(" Assignment 27 - Exercise 1: Socket Client-Server Chat");
        System.out.println("=========================================================");
        System.out.println("Choose Mode:");
        System.out.println("  1. Run as Chat Server (wait for client in another terminal)");
        System.out.println("  2. Run as Chat Client (connect to running server)");
        System.out.println("  3. Run Automated Demo (Server + Client in parallel)");
        System.out.print("Enter choice (1/2/3) [Default 3]: ");

        Scanner sc = new Scanner(System.in);
        String choice = "3";
        if (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                choice = input;
            }
        }

        switch (choice) {
            case "1":
                runServer();
                break;
            case "2":
                runClient();
                break;
            case "3":
            default:
                runDemo();
                break;
        }
    }
}
