package com.test.contactregistry2.servlets;

import com.google.gson.Gson;
import com.test.contactregistry2.Dao.ContactDAO;
import com.test.contactregistry2.model.Contact;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/contacts/search")
public class ContactSearchServlet extends HttpServlet {
    
    private ContactDAO contactDAO = new ContactDAO();
    private Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Contact> contacts = new ArrayList<Contact>();
            resp.setContentType("application/json");

            // Retrieve the phoneHash parameter from the query string
            String phoneHash = req.getParameter("phoneHash");
            if (phoneHash == null || phoneHash.isEmpty()) {

                String maskName = req.getParameter("maskedName");
                String maskPhone = req.getParameter("maskedPhone");

                if ((maskName == null || maskName.isEmpty())
                        && (maskPhone == null || maskPhone.isEmpty())
                ){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'maskName' parameter.");
                    return;
                }
                else{
                   contacts = contactDAO.searchContactsByMaskedNameAndPhone(maskPhone,maskName);
                }
            }
            else {
                // Search for contacts by phone hash
                contacts = contactDAO.searchContactsByPhoneHash(phoneHash);
            }
            String jsonResponse = gson.toJson(contacts);
            resp.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }
}
