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

class Patient {
    String patientId;
    String name;
    int age;
    String disease;
    String treatmentStatus;

    public Patient(String patientId, String name, int age, String disease, String treatmentStatus) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.treatmentStatus = treatmentStatus;
    }
}

public class PatientCRUDJSP {
    private static final int PORT = 8084;
    private static final Map<String, Patient> patientDb = new ConcurrentHashMap<>();

    static {
        // Sample initial patient records
        patientDb.put("P101", new Patient("P101", "John Doe", 45, "Hypertension", "Under Treatment"));
        patientDb.put("P102", new Patient("P102", "Jane Roe", 32, "Type 2 Diabetes", "Recovered"));
        patientDb.put("P103", new Patient("P103", "David Miller", 58, "Cardiovascular Disease", "Admitted"));
    }

    public static final String STANDALONE_JSP_CODE = """
        <%@ page import="java.sql.*" %>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <!DOCTYPE html>
        <html>
        <head>
            <title>Hospital Patient Management (JSP CRUD)</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 30px; background: #fdfdfd; }
                table { border-collapse: collapse; width: 100%; margin-top: 20px; }
                th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
                th { background-color: #00796b; color: white; }
                .btn { padding: 6px 12px; border: none; border-radius: 4px; color: white; cursor: pointer; }
                .btn-add { background: #00897b; }
                .btn-del { background: #e53935; }
            </style>
        </head>
        <body>
            <h2>Patient Records Management System (JSP CRUD)</h2>
            <!-- Form to Add / Create Patient Record -->
            <form method="POST" action="patient_crud.jsp">
                <input type="hidden" name="action" value="create" />
                <input type="text" name="patientId" placeholder="Patient ID (e.g. P104)" required />
                <input type="text" name="name" placeholder="Patient Name" required />
                <input type="number" name="age" placeholder="Age" required />
                <input type="text" name="disease" placeholder="Disease/Diagnosis" required />
                <select name="treatmentStatus" required>
                    <option value="Under Treatment">Under Treatment</option>
                    <option value="Admitted">Admitted</option>
                    <option value="Recovered">Recovered</option>
                    <option value="Discharged">Discharged</option>
                </select>
                <button type="submit" class="btn btn-add">Save Patient Record</button>
            </form>

            <%
                String action = request.getParameter("action");
                // Backend JSP Controller Logic:
                // String url = "jdbc:mysql://localhost:3306/hospital_db";
                // Connection conn = DriverManager.getConnection(url, "root", "password");
                // if ("create".equals(action)) {
                //     PreparedStatement ps = conn.prepareStatement("INSERT INTO patients VALUES(?,?,?,?,?)");
                //     ps.setString(1, request.getParameter("patientId"));
                //     ps.setString(2, request.getParameter("name"));
                //     ps.setInt(3, Integer.parseInt(request.getParameter("age")));
                //     ps.setString(4, request.getParameter("disease"));
                //     ps.setString(5, request.getParameter("treatmentStatus"));
                //     ps.executeUpdate();
                // }
                // if ("delete".equals(action)) {
                //     PreparedStatement ps = conn.prepareStatement("DELETE FROM patients WHERE patient_id = ?");
                //     ps.setString(1, request.getParameter("patientId"));
                //     ps.executeUpdate();
                // }
                // ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM patients");
                // while(rs.next()) { ... print table rows ... }
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

    static class PatientHandler implements HttpHandler {
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
                    String pid = params.get("patientId");
                    String name = params.get("name");
                    int age = Integer.parseInt(params.getOrDefault("age", "0"));
                    String disease = params.get("disease");
                    String status = params.get("treatmentStatus");
                    if (pid != null && !pid.trim().isEmpty()) {
                        patientDb.put(pid, new Patient(pid, name, age, disease, status));
                    }
                } else if ("delete".equalsIgnoreCase(action)) {
                    String pid = params.get("patientId");
                    if (pid != null) patientDb.remove(pid);
                } else if ("updateStatus".equalsIgnoreCase(action)) {
                    String pid = params.get("patientId");
                    String status = params.get("treatmentStatus");
                    if (pid != null && patientDb.containsKey(pid)) {
                        patientDb.get(pid).treatmentStatus = status;
                    }
                }
            }

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><title>Patient Records CRUD (JSP)</title>");
            html.append("<style>");
            html.append("body { font-family: Arial, sans-serif; background: #e0f2f1; margin: 30px; }");
            html.append(".card { max-width: 950px; margin: auto; background: white; padding: 25px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }");
            html.append("h2 { color: #004d40; }");
            html.append(".form-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(160px, 1fr)); gap: 10px; margin-bottom: 20px; }");
            html.append("input[type=text], input[type=number], select { padding: 8px; border: 1px solid #ccc; border-radius: 4px; width: 100%; box-sizing: border-box; }");
            html.append("button { padding: 9px 15px; border: none; border-radius: 4px; cursor: pointer; color: white; font-weight: bold; }");
            html.append(".btn-submit { background: #00796b; }");
            html.append(".btn-del { background: #d32f2f; }");
            html.append("table { width: 100%; border-collapse: collapse; margin-top: 15px; }");
            html.append("th, td { border: 1px solid #b2dfdb; padding: 10px; text-align: left; }");
            html.append("th { background-color: #004d40; color: white; }");
            html.append(".status-badge { display: inline-block; padding: 3px 8px; border-radius: 12px; font-size: 13px; font-weight: bold; background: #b2dfdb; color: #004d40; }");
            html.append("</style></head><body><div class='card'>");

            html.append("<h2>Patient Records Management (JSP CRUD Operations)</h2>");
            html.append("<form method='POST'>");
            html.append("<input type='hidden' name='action' value='create' />");
            html.append("<div class='form-grid'>");
            html.append("<input type='text' name='patientId' placeholder='Patient ID' required />");
            html.append("<input type='text' name='name' placeholder='Patient Name' required />");
            html.append("<input type='number' name='age' placeholder='Age' required />");
            html.append("<input type='text' name='disease' placeholder='Disease/Condition' required />");
            html.append("<select name='treatmentStatus' required>");
            html.append("<option value='Under Treatment'>Under Treatment</option>");
            html.append("<option value='Admitted'>Admitted</option>");
            html.append("<option value='Recovered'>Recovered</option>");
            html.append("<option value='Discharged'>Discharged</option>");
            html.append("</select>");
            html.append("</div>");
            html.append("<button type='submit' class='btn-submit'>+ Add New Patient (Create)</button>");
            html.append("</form>");

            html.append("<h3>Active Patient List (Read, Update, Delete)</h3>");
            html.append("<table>");
            html.append("<tr><th>Patient ID</th><th>Name</th><th>Age</th><th>Disease</th><th>Treatment Status</th><th>Action</th></tr>");
            for (Patient p : patientDb.values()) {
                html.append("<tr>");
                html.append("<td>").append(p.patientId).append("</td>");
                html.append("<td>").append(p.name).append("</td>");
                html.append("<td>").append(p.age).append("</td>");
                html.append("<td>").append(p.disease).append("</td>");
                html.append("<td><span class='status-badge'>").append(p.treatmentStatus).append("</span></td>");
                html.append("<td>");
                html.append("<form method='POST' style='display:inline;'>");
                html.append("<input type='hidden' name='action' value='delete' />");
                html.append("<input type='hidden' name='patientId' value='").append(p.patientId).append("' />");
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
        System.out.println("=== Assignment 30 - Exercise 2: Patient Records CRUD JSP Application ===");
        System.out.println("\nStandalone JSP Code Outline for 'patient_crud.jsp':");
        System.out.println("-----------------------------------------------------------------");
        System.out.println(STANDALONE_JSP_CODE);
        System.out.println("-----------------------------------------------------------------");

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new PatientHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("\nLive Embedded Patient Records CRUD Server running at: http://localhost:" + PORT + "/");

            System.out.println("Server started successfully!");
            System.out.println("Open in browser: http://localhost:" + PORT + "/");
            System.out.println("Keep this terminal open. Press Ctrl+C to stop the server.");

            new java.util.concurrent.CountDownLatch(1).await();
        } catch (Exception e) {
            System.out.println("Server notice: " + e.getMessage());
        }
    }
}
