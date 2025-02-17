package com.test.contactregistry2;



import com.test.contactregistry2.Dao.ContactDAO;
import com.test.contactregistry2.model.Contact;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebService
public class ContactService {
    private ContactDAO contactDAO = new ContactDAO();

    @WebMethod
    public List<Contact> getContactsByOrganization(String organization) {
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
