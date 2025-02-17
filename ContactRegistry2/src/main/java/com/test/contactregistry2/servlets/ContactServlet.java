package com.test.contactregistry2.servlets;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.contactregistry2.Dao.ContactDAO;
import com.test.contactregistry2.model.Contact;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/contacts/*")
public class ContactServlet extends HttpServlet {
    private ContactDAO contactDAO = new ContactDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("getting data ............");
            List<Contact> contacts = contactDAO.getAllContacts();
            String jsonResponse = gson.toJson(contacts);

            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Set response type to JSON
            resp.setContentType("application/json");

            // Read the JSON data from the request body
            StringBuilder jsonBuilder = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
            }
            String jsonString = jsonBuilder.toString();

            // Parse the JSON string into a JsonObject
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            Contact newContact = gson.fromJson(jsonObject, Contact.class);
            newContact.setMaskedName(maskName(newContact.getFullName()));
            newContact.setMaskedPhoneNumber(maskPhone(newContact.getPhoneNumber()));
            newContact.setHashedPhoneNumber(hashPhone(newContact.getPhoneNumber()));



            contactDAO.addContact(newContact);
            resp.getWriter().write("{\"message\": \"Contact added successfully\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }
        catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Set response type to JSON
            resp.setContentType("application/json");

            // Read the JSON data from the request body
            StringBuilder jsonBuilder = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
            }
            String jsonString = jsonBuilder.toString();

            // Parse the JSON string into a JsonObject
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            Contact newContact = gson.fromJson(jsonObject, Contact.class);
            newContact.setMaskedName(maskName(newContact.getFullName()));
            newContact.setMaskedPhoneNumber(maskPhone(newContact.getPhoneNumber()));
            newContact.setHashedPhoneNumber(hashPhone(newContact.getPhoneNumber()));



            contactDAO.updateContact(newContact);
            resp.getWriter().write("{\"message\": \"Contact updated successfully\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }
        catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");

        // Retrieve the path info; for a URL like "/contacts/delete/1", getPathInfo() returns "/1"
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing contact id in URL.");
            return;
        }

        // Remove the leading "/" to get the id as a string
        String idStr = pathInfo.substring(1);
        try {
            int id = Integer.parseInt(idStr);
            contactDAO.deleteContact(id);
            resp.getWriter().write("{\"message\": \"Contact deleted successfully.\"}");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid contact id: " + idStr);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
    private String maskName(String name) {
        return name.split(" ")[0] + " *";
    }

    private String maskPhone(String phone) {
        return phone.substring(0, 5) + "***" + phone.substring(phone.length() - 3);
    }

    private String hashPhone(String phone) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(phone.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing phone number", e);
        }
    }
}
