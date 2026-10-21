
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class StudentInfoJSP {

    private static final int PORT = 8081;

    public static final String JSP_SOURCE_CODE = """
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <!DOCTYPE html>
        <html>
        <head>
            <title>Student Information JSP Page</title>
        </head>
        <body>
            <h2>Student Information Form</h2>
            <form method="POST" action="student_info.jsp">
                Roll Number: <input type="text" name="rollNo" required><br><br>
                Student Name: <input type="text" name="name" required><br><br>
                Course: <input type="text" name="course" required><br><br>
                Semester: <input type="text" name="semester" required><br><br>
                Marks: <input type="text" name="marks" required><br><br>
                <input type="submit" value="Display Information">
            </form>
            <%
                String rollNo = request.getParameter("rollNo");
                String name = request.getParameter("name");
                String course = request.getParameter("course");
                String semester = request.getParameter("semester");
                String marks = request.getParameter("marks");

                if (rollNo != null && name != null) {
            %>
            <h3>Submitted Student Details</h3>
            <table border="1">
                <tr><th>Field</th><th>Value</th></tr>
                <tr><td>Roll Number</td><td><%= rollNo %></td></tr>
                <tr><td>Name</td><td><%= name %></td></tr>
                <tr><td>Course</td><td><%= course %></td></tr>
                <tr><td>Semester</td><td><%= semester %></td></tr>
                <tr><td>Marks</td><td><%= marks %></td></tr>
            </table>
            <%
                }
            %>
        </body>
        </html>
        """;

    private static Map<String, String> parseFormData(String data) {
        Map<String, String> map = new HashMap<>();

        if (data == null || data.isEmpty()) {
            return map;
        }

        for (String pair : data.split("&")) {
            String[] parts = pair.split("=", 2);

            String key = URLDecoder.decode(
                parts[0], StandardCharsets.UTF_8
            );

            String value = parts.length > 1
                ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8)
                : "";

            map.put(key, value);
        }

        return map;
    }

    private static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }

    static class StudentHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> params = new HashMap<>();

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                            exchange.getRequestBody(),
                            StandardCharsets.UTF_8))) {

                    StringBuilder body = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        body.append(line);
                    }

                    params = parseFormData(body.toString());
                }
            }

            StringBuilder html = new StringBuilder();

            html.append("""
                <!DOCTYPE html>
                <html>
                <head>
                <meta charset="UTF-8">
                <title>Student Information</title>
                <style>
                body {
                    font-family: Arial, sans-serif;
                    background: #eef2f7;
                    padding: 30px;
                }
                .box {
                    background: white;
                    padding: 25px;
                    border-radius: 8px;
                    max-width: 500px;
                    margin: auto;
                    box-shadow: 0 4px 12px #cccccc;
                }
                h2 { color: #2c3e50; }
                label {
                    display: block;
                    margin-top: 12px;
                    font-weight: bold;
                }
                input[type=text] {
                    width: 100%;
                    padding: 9px;
                    margin-top: 5px;
                    box-sizing: border-box;
                }
                input[type=submit] {
                    margin-top: 18px;
                    padding: 10px;
                    width: 100%;
                    background: #2980b9;
                    color: white;
                    border: none;
                    cursor: pointer;
                }
                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 20px;
                }
                th, td {
                    border: 1px solid #cccccc;
                    padding: 9px;
                    text-align: left;
                }
                th { background: #34495e; color: white; }
                </style>
                </head>
                <body>
                <div class="box">
                <h2>Student Information Form</h2>
                <form method="POST" action="/">
                """);

            String[] fields = {
                "rollNo", "name", "course", "semester", "marks"
            };

            String[] labels = {
                "Roll Number", "Student Name", "Course",
                "Semester", "Marks (%)"
            };

            for (int i = 0; i < fields.length; i++) {
                String value = escapeHtml(params.get(fields[i]));

                html.append("<label>")
                    .append(labels[i])
                    .append(":</label><input type=\"text\" name=\"")
                    .append(fields[i])
                    .append("\" value=\"")
                    .append(value)
                    .append("\" required>");
            }

            html.append("""
                <input type="submit" value="Submit and Display">
                </form>
                """);

            if (params.containsKey("rollNo")
                    && !params.get("rollNo").isEmpty()) {

                html.append("<h3>Submitted Student Details</h3>");
                html.append("<table>");
                html.append("<tr><th>Field</th><th>Value</th></tr>");

                for (int i = 0; i < fields.length; i++) {
                    html.append("<tr><td>")
                        .append(labels[i])
                        .append("</td><td>")
                        .append(escapeHtml(params.get(fields[i])))
                        .append("</td></tr>");
                }

                html.append("</table>");
            }

            html.append("</div></body></html>");

            byte[] response = html.toString()
                                  .getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8"
            );

            exchange.sendResponseHeaders(200, response.length);

            try (OutputStream output = exchange.getResponseBody()) {
                output.write(response);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Assignment 29 - Student Information JSP Application");

        try {
            HttpServer server = HttpServer.create(
                new InetSocketAddress(PORT), 0
            );

            server.createContext("/", new StudentHandler());
            server.setExecutor(null);
            server.start();

            System.out.println("Server started successfully!");
            System.out.println(
                "Open in browser: http://localhost:" + PORT + "/"
            );
            System.out.println("Keep this terminal open.");
            System.out.println("Press Ctrl+C to stop the server.");

            new CountDownLatch(1).await();

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Server interrupted.");
        }
    }
}
