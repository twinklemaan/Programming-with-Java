

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class Employee {
    String id;
    String name;
    String department;
    String designation;
    double salary;

    public Employee(String id, String name, String department, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
    }
}

public class EmployeeCRUDJSP {
    private static final int PORT = 8083;
    private static final Map<String, Employee> database = new ConcurrentHashMap<>();

    static {
        // Sample initial records
        database.put("EMP101", new Employee("EMP101", "Alice Smith", "Engineering", "Software Engineer", 85000));
        database.put("EMP102", new Employee("EMP102", "Bob Johnson", "Marketing", "Brand Manager", 72000));
    }

    public static final String STANDALONE_JSP_CODE = """
        <%@ page import="java.sql.*" %>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <!DOCTYPE html>
        <html>
        <head>
            <title>Employee Management CRUD (JSP)</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 30px; }
                table { border-collapse: collapse; width: 100%; margin-top: 20px; }
                th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                th { background-color: #0288d1; color: white; }
                .btn { padding: 6px 12px; margin: 2px; border: none; cursor: pointer; color: white; border-radius: 4px; }
                .btn-add { background: #4caf50; }
                .btn-del { background: #f44336; }
            </style>
        </head>
        <body>
            <h2>Employee Management System (JSP CRUD)</h2>
            <!-- Create Form -->
            <form method="POST" action="employee_crud.jsp">
                <input type="hidden" name="action" value="create" />
                <input type="text" name="id" placeholder="Employee ID" required />
                <input type="text" name="name" placeholder="Full Name" required />
                <input type="text" name="dept" placeholder="Department" required />
                <input type="text" name="role" placeholder="Designation" required />
                <input type="number" step="0.01" name="salary" placeholder="Salary" required />
                <button type="submit" class="btn btn-add">Add Employee</button>
            </form>

            <%
                String action = request.getParameter("action");
                // In production JSP, JDBC Connection (DriverManager.getConnection(...)) executes:
                // if ("create".equals(action)) INSERT INTO employees VALUES (?,?,?,?,?)
                // if ("delete".equals(action)) DELETE FROM employees WHERE id = ?
                // if ("update".equals(action)) UPDATE employees SET ... WHERE id = ?
                // SELECT * FROM employees displays the records dynamically.
            %>
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

    static class CrudHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            Map<String, String> params = new HashMap<>();

            if ("POST".equalsIgnoreCase(method)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                params = parseFormData(sb.toString());

                String action = params.get("action");
                if ("create".equalsIgnoreCase(action)) {
                    String id = params.get("id");
                    String name = params.get("name");
                    String dept = params.get("dept");
                    String role = params.get("role");
                    double sal = Double.parseDouble(params.getOrDefault("salary", "0"));
                    if (id != null && !id.trim().isEmpty()) {
                        database.put(id, new Employee(id, name, dept, role, sal));
                    }
                } else if ("delete".equalsIgnoreCase(action)) {
                    String id = params.get("id");
                    if (id != null) database.remove(id);
                } else if ("update".equalsIgnoreCase(action)) {
                    String id = params.get("id");
                    if (database.containsKey(id)) {
                        String name = params.get("name");
                        String dept = params.get("dept");
                        String role = params.get("role");
                        double sal = Double.parseDouble(params.getOrDefault("salary", "0"));
                        database.put(id, new Employee(id, name, dept, role, sal));
                    }
                }
            }

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><title>Employee CRUD JSP</title>");
            html.append("<style>");
            html.append("body { font-family: Arial, sans-serif; background: #f8f9fa; margin: 30px; }");
            html.append(".container { max-width: 900px; margin: auto; background: white; padding: 25px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }");
            html.append("h2 { color: #1e3a8a; }");
            html.append(".form-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 10px; margin-bottom: 20px; }");
            html.append("input[type=text], input[type=number] { padding: 8px; border: 1px solid #ccc; border-radius: 4px; width: 100%; box-sizing: border-box; }");
            html.append("button { padding: 9px 15px; border: none; border-radius: 4px; cursor: pointer; color: white; font-weight: bold; }");
            html.append(".btn-save { background: #10b981; }");
            html.append(".btn-del { background: #ef4444; }");
            html.append("table { width: 100%; border-collapse: collapse; margin-top: 15px; }");
            html.append("th, td { border: 1px solid #e5e7eb; padding: 10px; text-align: left; }");
            html.append("th { background-color: #1e3a8a; color: white; }");
            html.append("</style></head><body><div class='container'>");

            html.append("<h2>Employee Management (JSP CRUD Operations)</h2>");
            html.append("<form method='POST'>");
            html.append("<input type='hidden' name='action' value='create' />");
            html.append("<div class='form-grid'>");
            html.append("<input type='text' name='id' placeholder='Employee ID' required />");
            html.append("<input type='text' name='name' placeholder='Full Name' required />");
            html.append("<input type='text' name='dept' placeholder='Department' required />");
            html.append("<input type='text' name='role' placeholder='Designation' required />");
            html.append("<input type='number' step='0.01' name='salary' placeholder='Salary' required />");
            html.append("</div>");
            html.append("<button type='submit' class='btn-save'>+ Add New Employee (Create)</button>");
            html.append("</form>");

            html.append("<h3>Current Employee Records (Read & Delete)</h3>");
            html.append("<table>");
            html.append("<tr><th>ID</th><th>Name</th><th>Department</th><th>Designation</th><th>Salary</th><th>Action</th></tr>");
            for (Employee emp : database.values()) {
                html.append("<tr>");
                html.append("<td>").append(emp.id).append("</td>");
                html.append("<td>").append(emp.name).append("</td>");
                html.append("<td>").append(emp.department).append("</td>");
                html.append("<td>").append(emp.designation).append("</td>");
                html.append("<td>$").append(String.format("%.2f", emp.salary)).append("</td>");
                html.append("<td>");
                html.append("<form method='POST' style='display:inline;'>");
                html.append("<input type='hidden' name='action' value='delete' />");
                html.append("<input type='hidden' name='id' value='").append(emp.id).append("' />");
                html.append("<button type='submit' class='btn-del'>Delete</button>");
                html.append("</form>");
                html.append("</td></tr>");
            }
            html.append("</table></div></body></html>");

            byte[] bytes = html.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Assignment 30 - Exercise 1: Employee CRUD JSP Application ===");
        System.out.println("\nStandalone JSP & Scriptlet CRUD Code Outline:");
        System.out.println("-----------------------------------------------------------------");
        System.out.println(STANDALONE_JSP_CODE);
        System.out.println("-----------------------------------------------------------------");

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new CrudHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("\nLive Embedded Employee CRUD Server running at: http://localhost:" + PORT + "/");

            System.out.println("Server started successfully!");
            System.out.println("Open in browser: http://localhost:" + PORT + "/");
            System.out.println("Keep this terminal open. Press Ctrl+C to stop the server.");

            new java.util.concurrent.CountDownLatch(1).await();
        } catch (Exception e) {
            System.out.println("Server notice: " + e.getMessage());
        }
    }
}
