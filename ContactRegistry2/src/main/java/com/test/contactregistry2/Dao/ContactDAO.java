package com.test.contactregistry2.Dao;

import com.test.contactregistry2.config.DBConnection;
import com.test.contactregistry2.model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    public void addContact(Contact contact) throws SQLException {
        String sql = "INSERT INTO contacts (full_name, phone_number, email, id_number, dob, gender, organization, masked_name, masked_phone, hashed_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contact.getFullName());
            stmt.setString(2, contact.getPhoneNumber());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getIdNumber());
            stmt.setString(5, contact.getDob());
            stmt.setString(6, contact.getGender());
            stmt.setString(7, contact.getOrganization());
            stmt.setString(8, contact.getMaskedName());
            stmt.setString(9, contact.getMaskedPhoneNumber());
            stmt.setString(10, contact.getHashedPhoneNumber());

            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contact> getAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("organization")
                );
                contacts.add(contact);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }

    // Method to update a contact
    public void updateContact(Contact contact) throws SQLException {
        String sql = "UPDATE contacts SET full_name = ?, phone_number = ?, email = ?, id_number = ?, dob = ?, gender = ?, organization = ?, masked_name = ?, masked_phone = ?, hashed_phone = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contact.getFullName());
            stmt.setString(2, contact.getPhoneNumber());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getIdNumber());
            stmt.setString(5, contact.getDob());
            stmt.setString(6, contact.getGender());
            stmt.setString(7, contact.getOrganization());
            stmt.setString(8, contact.getMaskedName());
            stmt.setString(9, contact.getMaskedPhoneNumber());
            stmt.setString(10, contact.getHashedPhoneNumber());
            stmt.setInt(11, contact.getId()); // Update based on the contact's id

            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to delete a contact by id
    public void deleteContact(int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contact> searchContactsByPhoneHash(String phoneHash) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts WHERE hashed_phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneHash);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contact contact = new Contact(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("phone_number"),
                            rs.getString("email"),
                            rs.getString("id_number"),
                            rs.getString("dob"),
                            rs.getString("gender"),
                            rs.getString("organization")
                    );
                    contacts.add(contact);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }

    public List<Contact> searchContactsByMaskedNameAndPhone(String maskPhone,String maskName) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts WHERE masked_name = ? AND masked_phone = ? ";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maskName);
            stmt.setString(2, maskPhone);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contact contact = new Contact(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("phone_number"),
                            rs.getString("email"),
                            rs.getString("id_number"),
                            rs.getString("dob"),
                            rs.getString("gender"),
                            rs.getString("organization")
                    );
                    contacts.add(contact);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }
}
