

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class UserRegistrationJSP {
    private static final int PORT = 8082;

    public static final String REGISTER_JSP = """
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <!DOCTYPE html>
        <html>
        <head>
            <title>User Registration</title>
            <style>
                body { font-family: Arial, sans-serif; background: #eef2f3; margin: 40px; }
                .form-box { background: white; padding: 30px; border-radius: 8px; width: 400px; margin: auto; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
                input[type=text], input[type=email], input[type=password] { width: 100%; padding: 8px; margin: 8px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
                input[type=submit] { background-color: #4CAF50; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; width: 100%; font-size: 16px; }
            </style>
        </head>
        <body>
            <div class="form-box">
                <h2>User Registration</h2>
                <form action="result.jsp" method="POST">
                    <label>Full Name:</label>
                    <input type="text" name="name" required />
                    
                    <label>Email Address:</label>
                    <input type="email" name="email" required />
                    
                    <label>Phone Number:</label>
                    <input type="text" name="phone" required />
                    
                    <label>Password:</label>
                    <input type="password" name="password" required />
                    
                    <input type="submit" value="Register Now" />
                </form>
            </div>
        </body>
        </html>
        """;

    public static final String RESULT_JSP = """
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <!DOCTYPE html>
        <html>
        <head>
            <title>Registration Result</title>
            <style>
                body { font-family: Arial, sans-serif; background: #eef2f3; margin: 40px; }
                .result-box { background: white; padding: 30px; border-radius: 8px; width: 450px; margin: auto; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
                .success { color: #2e7d32; font-weight: bold; }
                table { width: 100%; border-collapse: collapse; margin-top: 15px; }
                th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
                th { background-color: #f2f2f2; }
            </style>
        </head>
        <body>
            <div class="result-box">
                <h2>Registration Confirmation</h2>
                <p class="success">Registration details successfully received!</p>
                <%
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String phone = request.getParameter("phone");
                    String password = request.getParameter("password");
                    String maskedPassword = (password != null) ? "*".repeat(password.length()) : "";
                %>
                <table>
                    <tr><th>Parameter</th><th>Value</th></tr>
                    <tr><td>Name</td><td><%= name %></td></tr>
                    <tr><td>Email</td><td><%= email %></td></tr>
                    <tr><td>Phone Number</td><td><%= phone %></td></tr>
                    <tr><td>Password</td><td><%= maskedPassword %> (Encrypted)</td></tr>
                </table>
                <br />
                <a href="register.jsp">&larr; Back to Registration</a>
            </div>
        </body>
        </html>
        """;

    private static Map<String, String> parseFormData(String formData) {
        Map<String, String> map = new HashMap<>();
        if (formData == null || formData.isEmpty()) return map;
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                String val = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                map.put(key, val);
            }
        }
        return map;
    }

    static class RegistrationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            Map<String, String> params = new HashMap<>();

            if ("POST".equalsIgnoreCase(method)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                params = parseFormData(sb.toString());
            }

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><title>User Registration JSP</title>");
            html.append("<style>");
            html.append("body { font-family: Arial, sans-serif; background: #eef2f3; margin: 40px; display: flex; justify-content: center; }");
            html.append(".box { background: white; padding: 30px; border-radius: 8px; width: 440px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }");
            html.append("h2 { color: #333; margin-top: 0; }");
            html.append("label { font-weight: bold; margin-top: 10px; display: block; }");
            html.append("input[type=text], input[type=email], input[type=password] { width: 100%; padding: 8px; margin: 5px 0 12px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }");
            html.append("input[type=submit] { background-color: #27ae60; color: white; padding: 10px; border: none; border-radius: 4px; cursor: pointer; width: 100%; font-size: 16px; }");
            html.append("table { width: 100%; border-collapse: collapse; margin-top: 15px; }");
            html.append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            html.append("th { background-color: #f2f2f2; }");
            html.append("</style></head><body><div class='box'>");

            if ("POST".equalsIgnoreCase(method) && params.containsKey("name")) {
                // Result JSP view
                html.append("<h2 style='color:#27ae60;'>Registration Confirmation (Result JSP)</h2>");
                html.append("<p>The following information was processed by the JSP application:</p>");
                html.append("<table>");
                html.append("<tr><th>Field</th><th>Submitted Value</th></tr>");
                html.append("<tr><td>Name</td><td>").append(params.get("name")).append("</td></tr>");
                html.append("<tr><td>Email</td><td>").append(params.get("email")).append("</td></tr>");
                html.append("<tr><td>Phone Number</td><td>").append(params.get("phone")).append("</td></tr>");
                String pwd = params.getOrDefault("password", "");
                html.append("<tr><td>Password</td><td>").append("*".repeat(pwd.length())).append(" (Masked)</td></tr>");
                html.append("</table>");
                html.append("<p style='margin-top:20px;'><a href='/'>&larr; Back to Registration Form</a></p>");
            } else {
                // Register JSP view
                html.append("<h2>User Registration (JSP Form)</h2>");
                html.append("<form method='POST'>");
                html.append("<label>Name:</label><input type='text' name='name' required />");
                html.append("<label>Email:</label><input type='email' name='email' required />");
                html.append("<label>Phone Number:</label><input type='text' name='phone' required />");
                html.append("<label>Password:</label><input type='password' name='password' required />");
                html.append("<input type='submit' value='Register User' />");
                html.append("</form>");
            }

            html.append("</div></body></html>");

            byte[] bytes = html.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Assignment 29 - Exercise 2: User Registration JSP Application ===");
        System.out.println("\n[1] Standalone JSP Code: register.jsp");
        System.out.println("-----------------------------------------------------------------");
        System.out.println(REGISTER_JSP);
        System.out.println("\n[2] Standalone JSP Code: result.jsp");
        System.out.println("-----------------------------------------------------------------");
        System.out.println(RESULT_JSP);

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new RegistrationHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("\nLive Embedded JSP Registration Server running at: http://localhost:" + PORT + "/");

            System.out.println("Server started successfully!");
            System.out.println("Open in browser: http://localhost:" + PORT + "/");
            System.out.println("Keep this terminal open. Press Ctrl+C to stop the server.");

            new java.util.concurrent.CountDownLatch(1).await();
        } catch (Exception e) {
            System.out.println("Server note: " + e.getMessage());
        }
    }
}
