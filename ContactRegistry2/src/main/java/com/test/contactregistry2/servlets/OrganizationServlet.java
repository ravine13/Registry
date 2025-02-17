package com.test.contactregistry2.servlets;

import com.google.gson.Gson;
import com.test.contactregistry2.Dao.ContactDAO;
import com.test.contactregistry2.model.Contact;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/contacts/organization/*")
public class OrganizationServlet extends HttpServlet {
    private ContactDAO contactDAO = new ContactDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");

        // Retrieve the path info; for a URL like "/contacts/delete/1", getPathInfo() returns "/1"
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing organization in URL.");
            return;
        }

        // Remove the leading "/" to get the orgName as a string
        String orgName = pathInfo.substring(1);
        try {
            List<Contact> contacts = getContactsByOrganization(orgName);
            String jsonResponse = gson.toJson(contacts);

            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error occurred");
        }
    }

    private List<Contact> getContactsByOrganization(String organization) {
        try {
            List<Contact> contacts = contactDAO.getAllContacts();
            return contacts.stream()
                    .filter(c -> c.getOrganization().equalsIgnoreCase(organization))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            return null;
        }
    }
}
